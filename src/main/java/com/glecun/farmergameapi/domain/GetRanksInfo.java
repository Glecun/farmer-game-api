package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.RankInfo;
import com.glecun.farmergameapi.domain.entities.RanksInfo;
import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.entities.UserInfo;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import com.glecun.farmergameapi.domain.port.UserPort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

@Service
public class GetRanksInfo {
    private final UserPort userPort;
    private final UserInfoPort userInfoPort;

    public GetRanksInfo(UserPort userPort, UserInfoPort userInfoPort) {
        this.userPort = userPort;
        this.userInfoPort = userInfoPort;
    }

    public RanksInfo execute(User user) {
        Map<String, String> userMap = userPort.findAll().stream().collect(Collectors.toMap(User::getEmail, User::getUsername));
        List<RankInfo> allSortedRankInfo = rank(userInfoPort.findAll().stream(), UserInfo::getProfit,reverseOrder())
                .entrySet().stream()
                .map(integerListEntry -> integerListEntry.getValue().stream()
                        .map(userInfo -> new RankInfo(integerListEntry.getKey(), userMap.get(userInfo.email), userInfo.profit))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        RankInfo myRankInfo = allSortedRankInfo.stream().filter(rankInfo -> rankInfo.username.equals(user.getUsername())).findFirst().orElseThrow();
        List<RankInfo> betterRankInfo = allSortedRankInfo.stream().filter(rankInfo -> rankInfo.profit > myRankInfo.profit).collect(Collectors.toList());
        List<RankInfo> betterNearMeRankInfo = betterRankInfo.stream().skip(betterRankInfo.size() >= 2 ? betterRankInfo.size() - 2 : betterRankInfo.size()).collect(Collectors.toList());
        List<RankInfo> worseNearMeRankInfo = allSortedRankInfo.stream().filter(rankInfo -> rankInfo.profit <= myRankInfo.profit && !rankInfo.username.equals(myRankInfo.username)).limit(2).collect(Collectors.toList());
        return new RanksInfo(allSortedRankInfo, myRankInfo, betterNearMeRankInfo, worseNearMeRankInfo, allSortedRankInfo.size());
    }

    static <T, V> SortedMap<Integer, List<T>> rank(Stream<T> stream, Function<T, V> propertyExtractor, Comparator<V> propertyComparator) {
        return stream.sorted(comparing(propertyExtractor, propertyComparator))
                .collect(TreeMap::new,
                        (rank, item) -> {
                            V property = propertyExtractor.apply(item);
                            if (rank.isEmpty()) {
                                rank.put(1, new LinkedList<>());
                            } else {
                                Integer r = rank.lastKey();
                                List<T> items = rank.get(r);
                                if (!property.equals(propertyExtractor.apply(items.get(0)))) {
                                    rank.put(r + items.size(), new LinkedList<>());
                                }
                            }
                            rank.get(rank.lastKey()).add(item);
                        },
                        (rank1, rank2) -> {
                            int lastRanking = rank1.lastKey();
                            int offset = lastRanking + rank1.get(lastRanking).size() - 1;
                            if (propertyExtractor.apply(rank1.get(lastRanking).get(0))
                                    == propertyExtractor.apply(rank2.get(rank2.firstKey()).get(0))) {
                                rank1.get(lastRanking).addAll(rank2.get(rank2.firstKey()));
                                rank2.remove(rank2.firstKey());
                            }
                            rank2.forEach((r, items) -> rank1.put(offset + r, items));
                        }
                );
    }
}

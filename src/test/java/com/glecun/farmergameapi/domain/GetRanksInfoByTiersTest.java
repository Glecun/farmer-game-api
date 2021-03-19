package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import com.glecun.farmergameapi.domain.port.UserPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRanksInfoByTiersTest {
    @Mock
    private UserPort userPort;
    @Mock
    private UserInfoPort userInfoPort;

    @InjectMocks
    private GetRanksInfoByTiers getRanksInfoByTiers;

    @Test
    void should_get_rankings_info() {
        var user = new User("greg1", "greg.lol@mdr.fr", "pass");

        when(userInfoPort.findAll()).thenReturn(List.of(
                new UserInfo("1", "greg1@mdr.fr", 0,new ProfitsByTiers(300,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 300),
                                new ProfitByTier(TierEnum.TIER_2, 0),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("2", "greg2@mdr.fr", 0, new ProfitsByTiers(500,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 500),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("3", "greg3@mdr.fr", 0, new ProfitsByTiers(400,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 400),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("4", "greg4@mdr.fr", 0, new ProfitsByTiers(3000,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 3000),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("5", "greg5@mdr.fr", 0, new ProfitsByTiers(200,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 0),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 200),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("6", "greg6@mdr.fr", 0, new ProfitsByTiers(50,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 0),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 50),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("7", "greg7@mdr.fr", 0, new ProfitsByTiers(5000,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 0),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 5000),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("8", "greg8@mdr.fr", 0, new ProfitsByTiers(2,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 0),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 2),
                                new ProfitByTier(TierEnum.TIER_7, 0)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC)),
                new UserInfo("9", "greg9@mdr.fr", 0, new ProfitsByTiers(200,
                        List.of(
                                new ProfitByTier(TierEnum.TIER_1, 0),
                                new ProfitByTier(TierEnum.TIER_2, 0),
                                new ProfitByTier(TierEnum.TIER_3, 0),
                                new ProfitByTier(TierEnum.TIER_4, 0),
                                new ProfitByTier(TierEnum.TIER_5, 0),
                                new ProfitByTier(TierEnum.TIER_6, 0),
                                new ProfitByTier(TierEnum.TIER_7, 200)
                        )
                ), emptyList(), Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC))
        ));

        when(userPort.findAll()).thenReturn(List.of(
                new User("greg1","greg1@mdr.fr", ""),
                new User("greg2","greg2@mdr.fr", ""),
                new User("greg3","greg3@mdr.fr", ""),
                new User("greg4","greg4@mdr.fr", ""),
                new User("greg5","greg5@mdr.fr", ""),
                new User("greg6","greg6@mdr.fr", ""),
                new User("greg7","greg7@mdr.fr", ""),
                new User("greg8","greg8@mdr.fr", ""),
                new User("greg9","greg9@mdr.fr", "")
        ));

        RanksInfoByTiers ranksInfoByTiers = getRanksInfoByTiers.execute(user);

        assertThat(ranksInfoByTiers.globalRanksInfo).isEqualTo(new RanksInfo(
                List.of(
                        new RankInfo(1, "greg7",5000),
                        new RankInfo(2, "greg4",3000),
                        new RankInfo(3, "greg2",500),
                        new RankInfo(4, "greg3",400),
                        new RankInfo(5, "greg1",300),
                        new RankInfo(6, "greg5",200),
                        new RankInfo(6, "greg9",200),
                        new RankInfo(8, "greg6",50),
                        new RankInfo(9, "greg8",2)
                ),
                new RankInfo(5, "greg1",300),
                List.of(
                        new RankInfo(3, "greg2",500),
                        new RankInfo(4, "greg3",400)
                ),
                List.of(
                        new RankInfo(6, "greg5",200),
                        new RankInfo(6, "greg9",200)
                ),
                9
        ));

        assertThat(
                ranksInfoByTiers.rankInfoByTiers.stream()
                .filter(rankInfoByTier -> rankInfoByTier.tier == TierEnum.TIER_2)
                .findFirst()
        )
                .hasValueSatisfying(rankInfoByTier -> assertThat(rankInfoByTier.ranksInfo).isEqualTo(new RanksInfo(
                        List.of(
                                new RankInfo(1, "greg4",3000),
                                new RankInfo(2, "greg2",500),
                                new RankInfo(3, "greg3",400),
                                new RankInfo(4, "greg1",0),
                                new RankInfo(4, "greg5",0),
                                new RankInfo(4, "greg6",0),
                                new RankInfo(4, "greg7",0),
                                new RankInfo(4, "greg8",0),
                                new RankInfo(4, "greg9",0)
                        ),
                        new RankInfo(4, "greg1",0),
                        List.of(
                                new RankInfo(2, "greg2",500),
                                new RankInfo(3, "greg3",400)
                        ),
                        List.of(
                                new RankInfo(4, "greg5",0),
                                new RankInfo(4, "greg6",0)
                        ),
                        3
                )));

    }
}

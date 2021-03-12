package com.glecun.farmergameapi.domain;

import java.util.*;
import java.util.function.Function;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

@Service
public class ApplicationDomain {

    public static final int NB_OF_FAKE_USERS = 10;

    private final SignUp signUp;
    private final GetCurrentMarketInfo getCurrentMarketInfo;
    private final GenerateMarketInfos generateMarketInfos;
    private final ResolveSales resolveSales;
    private final GetRanksInfo getRanksInfo;

    private final UserInfoPort userInfoPort;

    @Autowired
    public ApplicationDomain(SignUp signUp, GetCurrentMarketInfo getCurrentMarketInfo, GenerateMarketInfos generateMarketInfos, ResolveSales resolveSales, GetRanksInfo getRanksInfo, UserInfoPort userInfoPort) {
        this.signUp = signUp;
        this.getCurrentMarketInfo = getCurrentMarketInfo;
        this.generateMarketInfos = generateMarketInfos;
        this.resolveSales = resolveSales;
        this.getRanksInfo = getRanksInfo;
        this.userInfoPort = userInfoPort;
    }

    public void signUpUser(User user) {
        signUp.execute(user);
    }

    public Optional<MarketInfo> getCurrentMarketInfo() {
        return getCurrentMarketInfo.execute();
    }

    public void generateMarketInfos(GrowthTime growthTime) {
        generateMarketInfos.execute(growthTime);
    }

    public UserInfo getUserInfo(String email) {
        return userInfoPort.findByEmail(email).orElseGet(() -> userInfoPort.save(UserInfo.createUserInfo(email)));
    }

    public UserInfo plantInAZone(HarvestableZone harvestableZone, User user) {
        return userInfoPort.findByEmail(user.getEmail())
                .or( () -> {throw new RuntimeException("Cannot plant a seed for a UserInfo that doesn't exists");})
                .map(userInfo -> userInfo.replaceInHarvestableZones(harvestableZone.nullifyInfoSale()))
                .map(userInfo -> userInfo.modifyMoney(
                        - harvestableZone.harvestableZoneType.nbOfZone *
                        harvestableZone.getHarvestablePlanted()
                                .map(harvestablePlanted -> harvestablePlanted.seedsPlanted)
                                .map(onSaleSeed -> onSaleSeed.buyPrice)
                                .orElseThrow(() -> {throw new RuntimeException("Try to plant in a zone without specify onSaleSeed");})
                ))
                .map(userInfoPort::save)
                .orElseThrow( () -> {throw new RuntimeException("Cannot return UserInfo while plantInAZone");});
    }

    public void resolveSales(GrowthTime growthTime) {
        resolveSales.execute(growthTime);
    }

    public UserInfo acknowledgeInfoSales(HarvestableZone harvestableZone, User user) {
        var harvestable = harvestableZone
              .getHarvestablePlanted().map(HarvestablePlanted::getInfoSale)
              .flatMap(Function.identity()).orElseThrow();

        return userInfoPort.findByEmail(user.getEmail())
              .map(userInfo -> userInfo.modifyMoney(harvestable.revenue))
              .map(userInfo -> userInfo.addProfit(harvestable.profit))
              .map(userInfo -> userInfo.replaceInHarvestableZones(harvestableZone.unplant()))
              .map(userInfoPort::save)
              .orElseThrow();
    }

    public RanksInfo getRanksInfo(User user) {
        return getRanksInfo.execute(user);
    }

    public UserInfo unlockHarvestableZone(User user, HarvestableZoneType harvestableZoneType) {
        return userInfoPort.findByEmail(user.getEmail())
                    .map(userInfo -> userInfo.modifyMoney(-harvestableZoneType.price))
                    .map(userInfo -> userInfo.unlockHarvestableZone(harvestableZoneType))
                    .map(userInfoPort::save)
                    .orElseThrow();
    }
}

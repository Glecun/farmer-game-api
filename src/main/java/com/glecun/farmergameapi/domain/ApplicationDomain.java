package com.glecun.farmergameapi.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Supplier;

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
    private final GetRanksInfoByTiers getRanksInfoByTiers;

    private final UserInfoPort userInfoPort;

    public Supplier<LocalDateTime> now = () -> LocalDateTime.now(ZoneOffset.UTC);

    @Autowired
    public ApplicationDomain(SignUp signUp, GetCurrentMarketInfo getCurrentMarketInfo, GenerateMarketInfos generateMarketInfos, ResolveSales resolveSales, GetRanksInfoByTiers getRanksInfoByTiers, UserInfoPort userInfoPort) {
        this.signUp = signUp;
        this.getCurrentMarketInfo = getCurrentMarketInfo;
        this.generateMarketInfos = generateMarketInfos;
        this.resolveSales = resolveSales;
        this.getRanksInfoByTiers = getRanksInfoByTiers;
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
        return userInfoPort.findByEmail(email).orElseGet(() -> userInfoPort.save(UserInfo.createUserInfo(email, now)));
    }

    public UserInfo plantInAZone(HarvestableZoneType harvestableZoneType, SeedEnum seedEnum , User user) {
        OnSaleSeed onSaleSeed = getCurrentMarketInfo.execute().map(marketInfo -> marketInfo.onSaleSeeds).stream().flatMap(Collection::stream)
                .filter(aOnSaleSeed -> aOnSaleSeed.seedEnum == seedEnum)
                .findFirst().orElseThrow();

        return userInfoPort.findByEmail(user.getEmail())
                .or( () -> {throw new RuntimeException("Cannot plant a seed for a UserInfo that doesn't exists");})
                .map(userInfo ->  userInfo.plant(harvestableZoneType, onSaleSeed, now ))
                .map(userInfo -> userInfo.modifyMoney(- userInfo.getHarvestableZone(harvestableZoneType).harvestableZoneType.nbOfZone * onSaleSeed.buyPrice))
                .map(userInfoPort::save)
                .orElseThrow( () -> {throw new RuntimeException("Cannot return UserInfo while plantInAZone");});
    }

    public void resolveSales(GrowthTime growthTime) {
        resolveSales.execute(growthTime);
    }

    public UserInfo acknowledgeInfoSales(HarvestableZoneType harvestableZoneType, User user) {
        return userInfoPort.findByEmail(user.getEmail()).map(userInfo -> {
            HarvestableZone harvestableZone = userInfo.getHarvestableZone(harvestableZoneType);
            InfoSale infoSale = harvestableZone.getInfoSale();
            TierEnum tierOfZone = TierEnum.getTierOfZone(harvestableZone.harvestableZoneType);
            return userInfo.modifyMoney(infoSale.revenue)
                    .addProfit(infoSale.profit, tierOfZone)
                    .replaceInHarvestableZones(harvestableZone.unplant());
        })
                .map(userInfoPort::save)
                .orElseThrow();
    }

    public RanksInfoByTiers getRanksInfoByTiers(User user) {
        return getRanksInfoByTiers.execute(user);
    }

    public UserInfo unlockHarvestableZone(User user, HarvestableZoneType harvestableZoneType) {
        return userInfoPort.findByEmail(user.getEmail())
                    .map(userInfo -> userInfo.unlockHarvestableZone(harvestableZoneType))
                    .map(userInfo -> userInfo.modifyMoney(-harvestableZoneType.price))
                    .map(userInfoPort::save)
                    .orElseThrow();
    }

    public UserInfo unlockTier(User user, TierEnum tierEnum) {
        return userInfoPort.findByEmail(user.getEmail())
                .map(userInfo -> userInfo.unlockTier(tierEnum))
                .map(userInfo -> userInfo.modifyMoney(-tierEnum.priceToUnlock))
                .map(userInfoPort::save)
                .orElseThrow();
    }
}

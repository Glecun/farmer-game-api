package com.glecun.farmergameapi.domain;

import java.util.Optional;
import com.glecun.farmergameapi.domain.entities.GrowthTime;
import com.glecun.farmergameapi.domain.entities.MarketInfo;
import com.glecun.farmergameapi.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDomain {

    private final SignUp signUp;
    private final GetCurrentMarketInfo getCurrentMarketInfo;
    private final GenerateMarketInfos generateMarketInfos;

    @Autowired
    public ApplicationDomain(SignUp signUp, GetCurrentMarketInfo getCurrentMarketInfo, GenerateMarketInfos generateMarketInfos) {
        this.signUp = signUp;
        this.getCurrentMarketInfo = getCurrentMarketInfo;
        this.generateMarketInfos = generateMarketInfos;
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
}

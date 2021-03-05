package com.glecun.farmergameapi.domain;

import java.util.Optional;
import com.glecun.farmergameapi.domain.entities.GrowthTime;
import com.glecun.farmergameapi.domain.entities.MarketInfo;
import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.entities.UserInfo;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDomain {

    private final SignUp signUp;
    private final GetCurrentMarketInfo getCurrentMarketInfo;
    private final GenerateMarketInfos generateMarketInfos;
    private final UserInfoPort userInfoPort;

    @Autowired
    public ApplicationDomain(SignUp signUp, GetCurrentMarketInfo getCurrentMarketInfo, GenerateMarketInfos generateMarketInfos, UserInfoPort userInfoPort) {
        this.signUp = signUp;
        this.getCurrentMarketInfo = getCurrentMarketInfo;
        this.generateMarketInfos = generateMarketInfos;
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
        return userInfoPort.findByEmail(email)
              .orElseGet(() -> userInfoPort.save(UserInfo.createUserInfo(email)));
    }
}

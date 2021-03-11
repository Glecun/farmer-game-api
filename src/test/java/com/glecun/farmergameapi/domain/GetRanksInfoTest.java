package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.RankInfo;
import com.glecun.farmergameapi.domain.entities.RanksInfo;
import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.entities.UserInfo;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import com.glecun.farmergameapi.domain.port.UserPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRanksInfoTest {
    @Mock
    private UserPort userPort;
    @Mock
    private UserInfoPort userInfoPort;

    @InjectMocks
    private GetRanksInfo getRanksInfo;

    @Test
    void should_get_rankings_info() {
        var user = new User("greg1", "greg.lol@mdr.fr", "pass");

        when(userInfoPort.findAll()).thenReturn(List.of(
                new UserInfo("1", "greg1@mdr.fr", 0, 300, emptyList()),
                new UserInfo("2", "greg2@mdr.fr", 0, 500, emptyList()),
                new UserInfo("3", "greg3@mdr.fr", 0, 400, emptyList()),
                new UserInfo("4", "greg4@mdr.fr", 0, 3000, emptyList()),
                new UserInfo("5", "greg5@mdr.fr", 0, 200, emptyList()),
                new UserInfo("6", "greg6@mdr.fr", 0, 50, emptyList()),
                new UserInfo("7", "greg7@mdr.fr", 0, 5000, emptyList()),
                new UserInfo("8", "greg8@mdr.fr", 0, 2, emptyList()),
                new UserInfo("9", "greg9@mdr.fr", 0, 200, emptyList())
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

        RanksInfo ranksInfo = getRanksInfo.execute(user);

        assertThat(ranksInfo).isEqualTo(new RanksInfo(
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

    }
}
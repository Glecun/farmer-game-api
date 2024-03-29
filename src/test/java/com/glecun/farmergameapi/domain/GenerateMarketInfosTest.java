package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.MarketInfoPort;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateMarketInfosTest {

    @Mock
    private MarketInfoPort marketInfoPort;
    @Mock
    private GetCurrentMarketInfo getCurrentMarketInfo;
    @Mock
    private UserInfoPort userInfoPort;

    @InjectMocks
    private GenerateMarketInfos generateMarketInfos;


    @Captor
    ArgumentCaptor<MarketInfo> marketInfoCaptor;

    @Test
    void should_generate_market_info() {
        OnSaleSeed greenBean = OnSaleSeed.builder()
                .seedEnum(SeedEnum.GREEN_BEAN)
                .demand(new Demand(DemandType.HighDemand, 12))
                .buyPrice(2000)
                .sellPrice(4000)
                .onSaleDate(LocalDateTime.now(ZoneOffset.UTC))
                .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(1))
                .build();
        OnSaleSeed beet = OnSaleSeed.builder()
                .seedEnum(SeedEnum.PEA)
                .demand(new Demand(DemandType.SmallDemand, 2))
                .buyPrice(20)
                .sellPrice(30)
                .onSaleDate(LocalDateTime.now(ZoneOffset.UTC))
                .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(1))
                .build();
        MarketInfo marketInfo = new MarketInfo("1", List.of(greenBean, beet), LocalDateTime.now(ZoneOffset.UTC));
        when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(marketInfo));

        generateMarketInfos.execute(GrowthTime.GROWTH_TIME_1);

        verify(marketInfoPort).save(marketInfoCaptor.capture());
        assertThat(marketInfoCaptor.getValue()).isNotEqualTo(marketInfo);
        assertThat(marketInfoCaptor.getValue().onSaleSeeds).doesNotContain(greenBean);
        assertThat(marketInfoCaptor.getValue().onSaleSeeds).contains(beet);
    }

    @Test
    void should_generate_market_info_when_no_market_info() {
        when(getCurrentMarketInfo.execute()).thenReturn(Optional.empty());

        generateMarketInfos.execute(GrowthTime.GROWTH_TIME_1);

        verify(marketInfoPort).save(marketInfoCaptor.capture());
        assertThat(marketInfoCaptor.getValue().onSaleSeeds).isNotEmpty();
    }
}

package com.glecun.farmergameapi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.glecun.farmergameapi.domain.entities.MarketInfo;
import com.glecun.farmergameapi.domain.port.MarketInfoPort;

@ExtendWith(MockitoExtension.class)
class GetCurrentMarketInfoTest {

   @Mock
   MarketInfoPort marketInfoPort;

   @InjectMocks
   GetCurrentMarketInfo getCurrentMarketInfo;

   @Test
   void should_get_current_market_info() {
      var marketInfoFirst = new MarketInfo("1", Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC));
      var marketInfoSecond = new MarketInfo("2", Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC));
      var marketInfoThird = new MarketInfo("3", Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC));

      when(marketInfoPort.getMarketInfos()).thenReturn(List.of(
            marketInfoSecond, marketInfoThird, marketInfoFirst
      ));

      var maybeMarketInfo = getCurrentMarketInfo.execute();

      assertThat(maybeMarketInfo).hasValueSatisfying(marketInfo -> assertThat(marketInfo).isEqualTo(marketInfoThird));
   }

   @Test
   void should_remove_old_market_info() {
      var marketInfoOld1 = new MarketInfo("1", Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC).minusSeconds(1));
      var marketInfoOld2 = new MarketInfo("2", Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC).minusSeconds(1));
      var marketInfoCurrent = new MarketInfo("3", Collections.emptyList(), LocalDateTime.now(ZoneOffset.UTC));

      when(marketInfoPort.getMarketInfos()).thenReturn(List.of(
            marketInfoCurrent, marketInfoOld1, marketInfoOld2
      ));

      getCurrentMarketInfo.execute();

      verify(marketInfoPort).deleteAll(List.of(marketInfoOld1, marketInfoOld2));
   }
}

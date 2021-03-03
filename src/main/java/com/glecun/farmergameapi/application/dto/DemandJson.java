package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.Demand;
import com.glecun.farmergameapi.domain.entities.DemandType;

public class DemandJson {

   public final DemandType demandType;
   public final int nbDemand;

   public DemandJson(DemandType demandType, int nbDemand) {
      this.demandType = demandType;
      this.nbDemand = nbDemand;
   }

   public static DemandJson from(Demand demand) {
      return new DemandJson(demand.demandType, demand.nbDemand);
   }
}

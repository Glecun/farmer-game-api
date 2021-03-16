package com.glecun.farmergameapi.domain.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.glecun.farmergameapi.domain.ApplicationDomain.NB_OF_FAKE_USERS;

public class FakeUser {

    public static Integer getMaxCapacity() {
        return 88;
    }

    private static List<Integer> getNbZonesSumList() {
        return List.of(12, 24, 36, 48, 88);
    }

    public static int nbOfZoneFakeUsersTake(long nbOfFakeUserInvolved) {
        List<Integer> nbOfZoneList = getNbZonesSumList();
        return IntStream.range(0, (int)nbOfFakeUserInvolved)
                .map(operand -> nbOfZoneList.get(new Random().nextInt(nbOfZoneList.size())))
                .reduce(0, Integer::sum);
    }

    public static int randomizeNbFakePlayersAccordingToDemand(Demand demand) {
        var minNbFakeUser = 0;
        var maxNbFakeUser = NB_OF_FAKE_USERS;

        if(demand.demandType.equals(DemandType.VerySmallDemand)) {
            maxNbFakeUser = 4;
        }
        if(demand.demandType.equals(DemandType.SmallDemand)) {
            minNbFakeUser = 2;
            maxNbFakeUser = 6;
        }
        if(demand.demandType.equals(DemandType.MediumDemand)) {
            minNbFakeUser = 3;
            maxNbFakeUser = 7;
        }
        if(demand.demandType.equals(DemandType.HighDemand)) {
            minNbFakeUser = 4 ;
            maxNbFakeUser = 8;
        }
        if(demand.demandType.equals(DemandType.VeryHighDemand)) {
            minNbFakeUser = 6;
        }

        Random r = new Random();
        return r.ints(minNbFakeUser, (maxNbFakeUser + 1)).findFirst().orElse(minNbFakeUser);
    }
}

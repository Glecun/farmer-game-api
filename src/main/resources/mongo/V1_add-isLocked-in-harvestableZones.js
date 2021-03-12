db.getCollection('userInfoMongo').find().forEach(function (userInfoMongo) {
   userInfoMongo.harvestableZones.forEach(function (harvestableZone) {
      if (harvestableZone.harvestableZoneType === "ZONE_1" || harvestableZone.harvestableZoneType === "ZONE_2" ){
         harvestableZone.isLocked = false
      } else {
         harvestableZone.isLocked = true
      }
   });
   db.getCollection('userInfoMongo').save(userInfoMongo);
});

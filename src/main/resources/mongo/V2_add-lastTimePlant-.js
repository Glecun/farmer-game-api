db.getCollection('userInfoMongo').find().forEach(function (userInfoMongo) {
   userInfoMongo.lastTimePlant = ISODate("2021-03-19T15:18:34.283Z")
   db.getCollection('userInfoMongo').save(userInfoMongo);
});

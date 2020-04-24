#!/usr/bin/mongo --quiet

db.zwierzeta.drop()
db.sport.drop()
db.osoby.drop()
db.createCollection("zwierzeta")
db.createCollection("sport")
db.createCollection("osoby")
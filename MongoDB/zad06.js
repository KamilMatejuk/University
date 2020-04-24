#!/usr/bin/mongo --quiet

db.zwierzeta.find({"gatunek":"Cat"}).forEach( z => {
	print("Animal:")
	printjson(z)
	print("\tOwner:")
	db.osoby.find({"_id":z.ownerID}).forEach( o => printjson(o) )
})
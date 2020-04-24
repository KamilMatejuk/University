#!/usr/bin/mongo --quiet

//wszystkie
print("Wszystkie kolekcje: ")
print(db.getCollectionNames())

//niepuste
print("\nTylko niepuste kolekcje: ")
db.getCollectionNames().forEach(c => {
	if(db[c].count()>0){
		print(c)
	}
})
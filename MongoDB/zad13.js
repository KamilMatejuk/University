#!/usr/bin/mongo --quiet

db.osoby.find().sort({wiek:1}).forEach( o => {
	db.zwierzeta.distinct('ownerID',{gatunek: 'Dog', ownerID: o._id}).forEach( z => {
		printjson({imie:o.imie, nazwisko:o.nazwisko, narodowosc:o.narodowosc})
	})
})
#!/usr/bin/mongo --quiet

db.osoby.find(
	{nazwisko:"Smith"})
.forEach( o => {
	let temp = {}
	temp.imie = o.imie
	temp.nazwisko = o.nazwisko
	temp.narodowosc = o.narodowosc

	for(let i=0; i<o.zainteresowania.length; i++){
		db.sport.find({nazwa:o.zainteresowania[i]}).forEach( s => {
			temp["nazwa hobby"] = s.nazwa
			temp["miejsce wykonywania hobby"] = s.miejsce_wykonywania
		})
	}

	printjson(temp)
})
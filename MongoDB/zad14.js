#!/usr/bin/mongo --quiet

db.osoby.find(
	{$or: [{ 	imie: { $nin: [ /v/i, /x/i, /q/i, /ł/i, /ą/i ] }, 
				nazwisko: { $nin: [ /v/i, /x/i, /q/i, /ł/i, /ą/i ] }},
	{narodowosc: "Poland" }]})
.forEach( o => printjson(o))
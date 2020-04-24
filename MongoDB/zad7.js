#!/usr/bin/mongo --quiet

db.osoby.find(
	{$or: [
		{imie:"James", narodowosc:"Poland"},
		{'narodowosc.1':{$exists: true}}] 
	}, {_id:0})
.forEach( o => printjson(o))
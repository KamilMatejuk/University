#!/usr/bin/mongo --quiet

db.osoby.updateMany(
	{'narodowosc.nazwa': 'Russia'},
	{$set: {narodowosc: 'Russia'}})
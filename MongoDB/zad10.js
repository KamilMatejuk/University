#!/usr/bin/mongo --quiet

db.osoby.remove({$and: [
	{zainteresowania: {$in: ['Karate']}},
	{zainteresowania: {$in: ['Rugby']}}
	]})
#!/usr/bin/mongo --quiet

db.osoby.updateMany(
	{$and: [
	{zainteresowania: {$in: ['Boxing']}},
	{zainteresowania: {$in: ['Badminton']}}
	]},
	{$set: {zainteresowania: ['Biathlon']}})
#!/usr/bin/mongo --quiet

let counts = db.osoby.aggregate(
	[{$unwind: "$zainteresowania"},
	{$group: {
		_id: "$zainteresowania",
		count: {$sum: 1}
	}}],
	{allowDiskUse: true})

counts.forEach( z => {
	if(z.count >= 5) print(z._id)
})

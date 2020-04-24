#!/usr/bin/mongo --quiet

const sportName = [
    'Archery',
    'Athletics',
    'Badminton',
    'Baseball',
    'Basketball',
    'Boxing',
    'Canoe',
    'Cycling',
    'Equestrian',
    'Fencing',
    'Football',
    'Gymnastics',
    'Judo',
    'Karate',
    'Rugby']
const sportTyp = ['indywidualny', 'zespolowy']
const sportMiejsce = ['hala', 'na zewnatrz', 'basen', 'silownia']
const sportPopularnosc = ['bardzo wysoka', 'wysoka', 'średnia', 'niska', 'bardzo niska']

function randomSport(){
	var sp = {}
	//nazwa
	sp["nazwa"] = sportName[Math.floor(Math.random()*sportName.length)]
	//typ
	sp["typ"] = sportTyp[Math.floor(Math.random()*sportTyp.length)]
	//miejsce wykonywania
	let miejsce = []
	for(let i = 0; i < Math.floor(Math.random()*sportMiejsce.length); i++){
		miejsce.push(sportMiejsce[i])
	}
	if(miejsce.length>0){
		sp["miejsce_wykonywania"] = miejsce
	}
	//rok debiutu
	if(Math.random() > 0.5){
		sp["debiut_na_olimpiadzie"] = 1896 + Math.floor(Math.random()*124)
	}
	//popularnosc
	if(Math.random() > 0.6){
		sp["popularnosc"] = sportPopularnosc[Math.floor(Math.random()*sportPopularnosc.length)]
	}
	//wstawienie do bazy
	let duplicate = false
	db.sport.find({nazwa: sp["nazwa"]}).forEach( t => {duplicate = true})
	if(duplicate == false){
		db.sport.insert(sp)
	}
	
}

for(let i=0; i<10; i++){
	randomSport()
}
print(db.sport.count())
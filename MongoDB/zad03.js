#!/usr/bin/mongo --quiet

const animalColour = [
    'White',
    'Yellow',
    'Blue',
    'Red',
    'Green',
    'Black',
    'Brown',
    'Azure',
    'Ivory',
    'Teal',
    'Silver',
    'Purple',
    'Navy blue',
    'Pea green',
    'Gray',
    'Orange']
const animalGatunek = [
	'Cat',
    'Dog',
    'Goldfish',
    'Hamster',
    'Kitten',
    'Mouse',
    'Parrot',
    'Puppy',
    'Rabbit',
    'Turtle']
const animalRasa = ['huge', 'stripped', 'american']

function randomAnimal(){
	var an = {}
	//gatunek
	an["gatunek"] = animalGatunek[Math.floor(Math.random()*animalGatunek.length)]
	//waga minimalna
	let min = Math.floor(1 + Math.random()*20)
	an["waga_min"] = min + ' kg'
	//waga maksymalna
	let max = Math.floor(min + Math.random()*30)
	an["waga_max"] = max + ' kg'
	//długość życia
	an["dl_życia"] = Math.floor(1 + Math.random() * 15) + ' lat'
	//gama kolorystyczna
	let colours = []
	for(let i = 0; i < Math.floor(1 + Math.random()*3); i++){
		colours.push(animalColour[Math.floor(Math.random()*animalColour.length)])
	}
	an["gama_kolorystyczna"] = colours
	//rasy
	if(Math.random() > 0.5){
		an["rasa"] = animalRasa[Math.floor(Math.random()*animalRasa.length)]
	}
	//wlasciciel
	let n = db.osoby.count()
	let rand = Math.floor(Math.random()*n);
	an["ownerID"] = [];
	db.osoby.find().limit(1).skip(rand).forEach( o => an["ownerID"].push(o._id))
	an["ownerID"] = an.ownerID[0]
	// WAZNE: NAJPIERW UZUPELNIĆ KOLEKCJE OSÓB (zad4.js)

	//wstawienie do bazy
	db.zwierzeta.insert(an)
}

for(let i=0; i<10; i++){
	randomAnimal()
}
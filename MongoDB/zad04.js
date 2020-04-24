#!/usr/bin/mongo --quiet

const personName = [
    'James',
    'Logan',
    'Benjamin',
    'Mason',
    'Elijah',
    'Oliver',
    'Jacob',
    'Lucas',
    'Michael',
    'Alexander',
    'Ethan',
    'Daniel',
    'Matthew',
    'Aiden',
    'Henry',
    'Joseph',
    'Jackson',
    'Samuel',
    'Sebastian',
    'Emma',
    'Olivia',
    'Ava',
    'Isabella',
    'Sophia',
    'Charlotte',
    'Mia',
    'Amelia',
    'Harper',
    'Evelyn',
    'Abigail',
    'Emily',
    'Elizabeth',
    'Mila',
    'Ella',
    'Avery']
const personSurname = [
	'Smith',
	'Johnson',
	'Williams',
	'Brown',
	'Jones',
	'Miller',
	'Davis',
	'Garcia',
	'Rodriguez',
	'Wilson',
	'Martinez',
	'Anderson',
	'Taylor',
	'Thomas',	
	'Hernandez',
	'Moore',
	'Martin',
	'Jackson',
	'Thompson']
const personNation = [
	'Afghanistan',
    'Albania',
    'Argentina',
    'Australia',
    'Belgium',
    'Canada',
    'Chile',
    'China',
    'Czech Republic',
    'Denmark',
    'Egypt',
    'El Salvador',
    'Fiji',
    'Finland',
    'France',
    'Hungary',
    'Iceland',
    'India',
    'Italy',
    'Jamaica',
    'Japan',
    'Libya',
    'Liechtenstein',
    'Lithuania',
    'Malta',
    'Mexico',
    'Nepal',
    'Netherlands',
    'New Zealand',
    'Panama',
    'Papua New Guinea',
    'Paraguay',
    'Peru',
    'Philippines',
    'Poland',
    'Portugal',
    'Rwanda',
    'Slovakia',
    'Slovenia',
    'Spain',
    'Sweden',
    'Turkey',
    'Ukraine',
    'United Kingdom',
    'Vatican City',]
const personFederalNation = [
	{'nazwa': 'Austria',
	'ilosc': 9,
	'kraje_zwiazkowe': ['Burgenland','Carinthia','Lower Austria','...']},
	{'nazwa': 'Brazil',
	'ilosc': 26,
	'kraje_zwiazkowe': ['Acre','Alagoas','Amapa','...']},
	{'nazwa': 'Etiopia',
	'ilosc': 11,
	'kraje_zwiazkowe': ['Afar','Amhara','Dire Dawa','...']},
	{'nazwa': 'Germany',
	'ilosc': 16,
	'kraje_zwiazkowe': ['Berlin','Bayern','Niedersashen','...']},
	{'nazwa': 'Nigeria',
	'ilosc': 36,
	'kraje_zwiazkowe': ['Abia','Adamawa','Benue','...']},
	{'nazwa': 'Russia',
	'ilosc': 9,
	'kraje_zwiazkowe': ['Altai','Kamchatka','Perm','...']},
	{'nazwa': 'Switzerland',
	'ilosc': 26,
	'kraje_zwiazkowe': ['AArgau','Basel-Stadt','Bern','...']},
	{'nazwa': 'United States',
	'ilosc': 50,
	'kraje_zwiazkowe': ['Alabama','Alaska','Arizona','...']}]
	

function randomPerson(){
	var p = {}
	//imie
	p["imie"] = personName[Math.floor(Math.random()*personName.length)]
	//nazwisko
	p["nazwisko"] = personSurname[Math.floor(Math.random()*personSurname.length)]
	//wiek
	if(Math.random()>0.15){
		p["wiek"] = Math.floor(16 + Math.random()*80)
	}
	//wzorst
	if(Math.random()>0.15){
		p["wzrost"] = Math.floor(140 + Math.random()*65) + ' cm'
	}
	//zainteresowania
	if(Math.random()>0.15){
		let n = db.sport.count()
		let rand1 = Math.floor(Math.random()*n);
		let rand2 = Math.floor(Math.random()*n);
		while(rand1 == rand2){
			rand2 = Math.floor(Math.random()*n);
		}
		p["zainteresowania"] = []
		db.sport.find().limit(1).skip(rand1).forEach( z => p["zainteresowania"].push(z.nazwa))
		db.sport.find().limit(1).skip(rand2).forEach( z => p["zainteresowania"].push(z.nazwa))
	}
	//narodowosc
	if(Math.random()>0.5){
		p["narodowosc"] = personNation[Math.floor(Math.random()*personNation.length)]
	} else if(Math.random()>0.5){
		p["narodowosc"] = [
			personNation[Math.floor(Math.random()*personNation.length)],
			personNation[Math.floor(Math.random()*personNation.length)]
		]
	} else if(Math.random()>0.5){
		//państwa federalne
		p["narodowosc"] = personFederalNation[Math.floor(Math.random()*personFederalNation.length)]
	}

	//wstawienie do bazy
	db.osoby.insert(p)
}

for(let i=0; i<50; i++){
	randomPerson()
}
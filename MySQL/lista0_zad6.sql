SELECT DISTINCT owner FROM pet INNER JOIN event ON pet.name = event.name 
WHERE event.type = 'litter' AND pet.species = 'cat';
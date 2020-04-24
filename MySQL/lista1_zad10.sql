SELECT owner,pet.name FROM pet 
INNER JOIN event ON pet.name = event.name
WHERE event.type != 'birthday' ORDER BY pet.name DESC;
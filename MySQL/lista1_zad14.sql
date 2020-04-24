SELECT DISTINCT owner FROM pet JOIN event ON pet.name = event.name
WHERE date > (
    SELECT date from event WHERE name = 'Slim'
);
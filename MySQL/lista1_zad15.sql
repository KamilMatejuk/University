SELECT owner FROM pet WHERE name NOT IN (
    SELECT name FROM event WHERE type = 'birthday'
) ORDER BY name ASC;
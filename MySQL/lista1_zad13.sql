SELECT * FROM pet WHERE birth = (
    SELECT MIN(birth) FROM pet
);
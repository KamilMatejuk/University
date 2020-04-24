#!/bin/sh

# Kamil Matejuk

# Napisz skrypt pobierający zdjęcie kotka z podanego API, następnie wyświetlający je w konsoli jako tekst ANSI. 
# Pniżej wyświetl losowy cytat Chucka Norrisa

url=$(curl -s 'https://api.thecatapi.com/v1/images/search' | jq -r '.[0].url')
wget -O "image" -q $url
imgPath=$(readlink -f image)
img2txt -f utf8 $imgPath

quote=$(curl -s 'http://api.icndb.com/jokes/random' | jq -r '.value.joke')
echo \"$quote\"
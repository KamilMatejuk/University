#!/bin/sh

# Kamil Matejuk

# linia ponizej dzieli pliki po enterze, a nie spacji, wiec widzi nazwy plikow ze spacja jako calosc a nie dwa pliki
IFS=$'
'
for file in $(ls | grep [A-Z])
do
	# sprawdzenie czy to folder
	if [ -f $file ]; then
		# stworzenie nowej nazwy
		after=$(echo "$file" | tr 'A-Z' 'a-z');
		# podmiana nazwy, dzieki zapisowi ./ mozemy uzyc nazw z myslnikami
		mv ./$file ./after;
		echo "changing name of \"$file\" to \"$after\"";
	fi
done

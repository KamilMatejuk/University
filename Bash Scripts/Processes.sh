#!/bin/sh

# Kamil Matejuk

# Napisz skrypt wyświetlający dane na temat każdego z aktualnie działających procesów
#(http://man7.org/linux/man-pages/man5/proc.5.html)

printf "%6s | %6s | %30s | %15s | %5s \n\n" "PID" "PPID" "Name" "State" "opened files"

for file in $(ls /proc/ | grep [0-9])
do
	if [ -f "/proc/$file/status" ]; then
		name=$( cat /proc/$file/status| head -1 | tail -1 )
		nameCut=$( echo $name | awk -F " " '{ print $2 }' )
		
		pid=$( cat /proc/$file/status | head -6 | tail -1 )
		pidCut=$( echo $pid | awk -F " " '{ print $2 }' )
		
		ppid=$( cat /proc/$file/status | head -7 | tail -1 )
		ppidCut=$( echo $ppid | awk -F " " '{ print $2 }' )
		
		state=$( cat /proc/$file/status | head -3 | tail -1 )
		stateCut=$( echo $state | awk -F " " '{ print $3 }' )
		
		count=$( lsof -p $file | wc -l )

		printf "%6s | %6s | %30s | %15s | %5s \n" ${pidCut} ${ppidCut} ${nameCut} ${stateCut} ${count}
	fi

	
done

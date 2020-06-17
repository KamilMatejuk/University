#!/bin/bash

# Kamil Matejuk

#aktulna predkosc przsylania danych
_loadSpeed() {
	bytes_in_new=$(awk "/^ *wlp3s0:/"' { print $2 }' /proc/net/dev)
	bytes_out_new=$(awk "/^ *wlp3s0:/"' { print $10 }' /proc/net/dev)
	bytes_in=$((bytes_in_new - bytes_in_old))
	bytes_out=$((bytes_out_new - bytes_out_old))
    bytes_in_old=${bytes_in_new}
    bytes_out_old=${bytes_out_new}

	if [ ${bytes_in} -gt 1048576 ]; then
    	echo "Upload: $(( bytes_in/1048576 )) Mbps"
    else if [ ${bytes_in} -gt 1024 ]; then
    	echo "Upload: $(( bytes_in/1024 )) Kbps"	
	else
		echo "Upload: $(( bytes_in )) bps"
	fi
	fi

	if [ ${bytes_out} -gt 1048576 ]; then
    	echo "Download:	$(( bytes_out/1048576 )) Mbps"
    else if [ ${bytes_out} -gt 1024 ]; then
    	echo "Download: $(( bytes_out/1024 )) Kbps"
	else
		echo "Download: $(( bytes_out )) bps"
	fi
	fi
}

#czas uruchomienia systemu
#  /proc/uptime -> This file contains two numbers: the uptime of the system (seconds), and the amount of time spent in idle process (seconds).
_upTime() {
	time=$(awk '{print $1 }' /proc/uptime)
	time=${time%.*}
	timeDays=$(( time/86400 ))
	time=$(( time - timeDays*86400 ))
	timeHours=$(( time/3600 ))
	time=$(( time - timeHours*3600 ))
	timeMins=$(( time/60 ))
	time=$(( time - timeMins*60 ))
	echo "System time up: $timeDays days $timeHours h $timeMins min $time s"
}

#poziom baterii
_battery() {
	. /sys/class/power_supply/BAT0/uevent #ustawienie żródła source
    echo "Battery level: ${POWER_SUPPLY_CAPACITY}%"
}

#obciązenie systemu
#	The first three columns measure CPU and I/O utilization of the last 1, 5, and 15 min periods.
#   The fourth column shows the number of currently running processes and the total number of processes.
#   The last column displays the last process ID used.
_system() {
	avg1=$(awk '{ print $1 }' /proc/loadavg)
    avg5=$(awk '{ print $2 }' /proc/loadavg)
    avg15=$(awk '{ print $3 }' /proc/loadavg)
    echo "System usage over 1 min: ${avg1}, 5min: ${avg5}, 15min: ${avg15}"
}

#narysowanie wykresu
_graph() {
	for a in {0..18}; do
		values[$a]=${values[$a+1]}
	done
	values[19]=${bytes_in}

	_scale
	printf "Graph over time: \n"
	for n in {15..0}; do #dla kazdej linii  tekstu, jezeli n jest nizej niz scaled[x] to rusyj
		printf "  "
		for x in ${scaled[@]}; do
			if [ $n -gt $x ]; then
				printf "___ "
			else
				printf "### "
			fi
		done
		printf "\n"
	done
}
_scale() {
	max=0
	for i in ${values[@]}; do
		if [[ $i -gt $max ]]; then
			max=$i
		fi
	done
	
	if [ $max -gt 0 ];  then
		j=0
		for i in ${values[@]}; do
			scaled[$j]=$(( 15 * $i / $max))
			j=$j+1
		done
	fi
}


for a in {0..19}; do
	values[$a]=0
done
bytes_in_old=$(awk "/^ *wlp3s0:/"' { print $2 }' /proc/net/dev)
bytes_out_old=$(awk "/^ *wlp3s0:/"' { print $10 }' /proc/net/dev)
while sleep 1; do
	#wyczyszczenie ekranu
	clear
    _loadSpeed
    _upTime
    _battery
    _system
    _graph
done
#!/bin/bash
# author: Kamil Matejuk

# $1 - plik z IP

avgttl=0
maxttl=0
avgtime=0
counter=0
while IFS= read -r line; do
  	ttlstr=$(ping -c1 -w1 -s1472 $line | grep -o 'ttl=[0-9][0-9]* time=[0-9][0-9]*') || {
        continue;
    }
    stringarray=($ttlstr)
    ttl=${stringarray[0]}
    time=${stringarray[1]}
    ttl="${ttl#*=*}"
    time="${time#*=*}"

    if [[ $ttl -lt 64 ]]; then
		ttl1=$((64 - $ttl))
	elif [[ $ttl -lt 128 ]]; then
		ttl1=$((128 - $ttl))
	elif [[ $ttl -lt 256 ]]; then
		ttl1=$((256 - $ttl))
	fi

	if [[ $ttl1 -gt $maxttl ]]; then
    	maxttl=$ttl1
    fi

	avgttl=$(($avgttl + $ttl1))
	avgtime=$(($avgtime + $time))
	counter=$(($counter + 1))
    printf "(%d) %s,\t ttl=%d,\t time=%dms\n" "$counter" "$line" "$ttl1" "$time"
done < "$1"

avgttl=$((counter>0 ? $avgttl / $counter : 0))
avgtime=$((counter>0 ? $avgtime / $counter : 0))
echo "Average ttl: $(( ($avgttl)))"
echo "Average time: $(( ($avgtime)))"
echo "Maximal ttl: $(( ($maxttl)))"

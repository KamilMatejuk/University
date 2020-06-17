#!/bin/bash

# Kamil Matejuk

countdown() {
	local count[0]=" XXX "
	local count[1]="X   X"
	local count[2]="   X "
	local count[3]="X   X"
	local count[4]=" XXX "

	local count[5]=" XXX "
	local count[6]="X   X"
	local count[7]="   X "
	local count[8]=" X   "
	local count[9]="XXXXX"

	local count[10]="  XX "
	local count[11]=" X X "
	local count[12]="   X "
	local count[13]="   X "
	local count[14]="   X "

	for i in {0..2}; do
		local centerX=$(( $TERMINAL_WIDTH/2 - 3 ))
		local centerY=$(( $TERMINAL_HEIGHT/2 - 3 ))
		clear
		for j in {0..4}; do
			tput cup $centerY $centerX
			local index=$(( i * 5 + j))
			echo "${count[$index]}"
			centerY=$(( $centerY+1 ))
		done
		showInstructions
		sleep 1
	done
	clear
}

showInstructions() {
	local MARGIN_SIDE=5
	local MARGIN_TOP=2
	tput cup $(( $TERMINAL_HEIGHT/2 + 3 + $MARGIN_TOP )) $MARGIN_SIDE
	echo "PLAYER  A"
	tput cup $(( $TERMINAL_HEIGHT/2 + 3 + $MARGIN_TOP + 2 )) $MARGIN_SIDE
	echo " \"q\" - up"
	tput cup $(( $TERMINAL_HEIGHT/2 + 3 + $MARGIN_TOP + 3 )) $MARGIN_SIDE
	echo " \"a\" - down"
	tput cup $(( $TERMINAL_HEIGHT/2 + 3 + $MARGIN_TOP )) $(( $TERMINAL_WIDTH - 9 - $MARGIN_SIDE ))
	echo "PLAYER  B"
	tput cup $(( $TERMINAL_HEIGHT/2 + 3 + $MARGIN_TOP + 2 )) $(( $TERMINAL_WIDTH - 9 - $MARGIN_SIDE ))
	echo " \"p\" - up"
	tput cup $(( $TERMINAL_HEIGHT/2 + 3 + $MARGIN_TOP + 3 )) $(( $TERMINAL_WIDTH - 9 - $MARGIN_SIDE ))
	echo " \"l\" - down"
}

gameOver() {
	local over[0]=" XXX     X    X   X  XXXXX"
	local over[1]="X   X   X X   XX XX  X    "
	local over[2]="X  __  X   X  X X X  XXXXX"
	local over[3]="X   X  XXXXX  X   X  X    "
	local over[4]=" XXX   X   X  X   X  XXXXX"
	local over[5]=" "
	local over[6]=" XXX   X   X  XXXXX  XXXX "
	local over[7]="X   X  X   X  X      X   X"
	local over[8]="X   X  X   X  XXXXX  XXXX "
	local over[9]="X   X   X X   X      X  X "
   local over[10]=" XXX     X    XXXXX  X   X"

	local centerX=$(( $TERMINAL_WIDTH/2 - 13 ))
	local centerY=$(( $TERMINAL_HEIGHT/2 - 6 ))
	clear
	for j in {0..10}; do
		tput cup $centerY $centerX
		echo "${over[$j]}"
		centerY=$(( $centerY+1 ))
	done
}

game() {
	while [[ $PLAY = true ]]; do

		# wczytanie klawiatury
		read -n 1 -t $TIMING user_input
		case "$user_input" in
			q) USER_A_LOCATION=$(( $USER_A_LOCATION-1 ));;
			a) USER_A_LOCATION=$(( $USER_A_LOCATION+1 ));;
			p) USER_B_LOCATION=$(( $USER_B_LOCATION-1 ));;
			l) USER_B_LOCATION=$(( $USER_B_LOCATION+1 ));;
		esac

		#sprawdzenie czy sie miesci w planszy
		if [ $USER_A_LOCATION -lt 1 ]; then
			USER_A_LOCATION=1
		elif [ $USER_A_LOCATION -gt $(( $TERMINAL_HEIGHT - $PAD_HEIGHT + 1 )) ]; then
			USER_A_LOCATION=$(( $TERMINAL_HEIGHT - $PAD_HEIGHT + 1 ))
		fi

		if [ $USER_B_LOCATION -lt 1 ]; then
			USER_B_LOCATION=1
		elif [ $USER_B_LOCATION -gt $(( $TERMINAL_HEIGHT - $PAD_HEIGHT + 1 )) ]; then
			USER_B_LOCATION=$(( $TERMINAL_HEIGHT - $PAD_HEIGHT + 1 ))
		fi

		#odbijanie kulki góra-dół
		if [ ${BALL_LOCATION_Y} -eq 0 ]; then
			BALL_DIRECTION_Y=1
		elif [ ${BALL_LOCATION_Y} -eq $(( $TERMINAL_HEIGHT - $BALL_HEIGHT - 1 )) ]; then
			BALL_DIRECTION_Y=-1
		fi

		#odbicie po lewej
		if [ $BALL_LOCATION_X -eq $(( $PAD_WIDTH )) ]; then

			if [ $BALL_LOCATION_Y -ge $(( $USER_A_LOCATION - $BALL_HEIGHT + 1 )) ] &&
				[ $BALL_LOCATION_Y -le $(( $USER_A_LOCATION + $PAD_HEIGHT - 1 )) ]; then
				BALL_DIRECTION_X=1;
			else
				PLAY=false
			fi;
		#odbicie po prawej
		elif [ $BALL_LOCATION_X -eq $(( $TERMINAL_WIDTH - $BALL_WIDTH - 2 )) ]; then

			if [ $BALL_LOCATION_Y -ge $(( $USER_B_LOCATION - $BALL_HEIGHT + 1 )) ] &&
				[ $BALL_LOCATION_Y -le $(( $USER_B_LOCATION + $PAD_HEIGHT - 1 )) ]; then
				BALL_DIRECTION_X=-1;
			else
				PLAY=false
			fi;
		fi;

		#przemieszczenie piłeczki
		clear
		BALL_LOCATION_X=$(( $BALL_LOCATION_X + $BALL_DIRECTION_X ))
		BALL_LOCATION_Y=$(( $BALL_LOCATION_Y + $BALL_DIRECTION_Y ))
		
		#narysowanie wszytskiego
		drawBall
		drawPadA
		drawPadB
	done

}

drawBall(){
	local X=$BALL_LOCATION_X
	local Y=$BALL_LOCATION_Y
	tput cup $Y $X
	echo "/XX\ "
	tput cup $(($Y+1)) $X
	echo "XXXX"
	tput cup $(($Y+2)) $X
	echo "\XX/"
}

drawPadA(){
	local Y=$USER_A_LOCATION
	for i in {0..6}; do
		tput cup $(( $Y + $i )) 0
		echo "X"
	done
}
drawPadB(){
    local Y=$USER_B_LOCATION
	local X=$(( $TERMINAL_WIDTH - 1 ))
	for i in {0..6}; do
		tput cup $(( $Y + $i )) $X
		echo "X"
	done
}

TIMING=0.15 #seconds between refresh
BALL_WIDTH=4
BALL_HEIGHT=3
PAD_WIDTH=1
PAD_HEIGHT=7
TERMINAL_WIDTH=$( tput cols )
TERMINAL_HEIGHT=$( tput lines )
USER_A_LOCATION=$(( $TERMINAL_HEIGHT/2 - $PAD_HEIGHT/2 ))
USER_B_LOCATION=$(( $TERMINAL_HEIGHT/2 - $PAD_HEIGHT/2 ))
BALL_LOCATION_X=$(( $TERMINAL_WIDTH/2 - $BALL_WIDTH/2 ))
BALL_LOCATION_Y=$(( $TERMINAL_HEIGHT/2 - $BALL_HEIGHT/2 ))
BALL_DIRECTION_X=-1
BALL_DIRECTION_Y=1
PLAY=true

tput civis #invisible cursor
countdown
game
gameOver
tput cnorm #visible cursor
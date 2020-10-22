#!/bin/bash
# Kamil Matejuk

function option1 {
    # list all words from all files
    all_words=$(cat $(find $1 -type f) | tr ' ' '\n')
    # list all unique words
    words=$(echo $all_words | tr ' ' '\n' | sort -u -f)
    for w in $words ; do
        # count occurences of word $w
        n=$(echo $all_words | tr ' ' '\n' | grep -cw $w)
        printf "%7s $w \n" $n
    done
}

function option2 {
    # list all regular files in path $1
    files=$(find $1 -type f)
    for file in $files; do
        # get all unique words in $file
        cat $file | tr -cs '[[:alpha:]]' '\n' | sort -u
    done | sort | uniq -c # sort and count occurances
}

function option3 {
    # list all regular files in path $1
    files=$(find $1 -type f)
    # list all unique words in files
    words=$(cat $files | tr ' ' '\n' | sort -u -f)
    for w in $words ; do
        # for each file find words matching $w, and list with filename and line number
        find $1 -type f -exec grep -Hnw $w {} \; | sed -e "s/^/word $w, \tfile $f/" -e 's/:/, \tline /' -e 's/:/\t"/' -e 's/$/"/'
    done
}

function option4 {
    # set new line character to '\n'
    IFS=$'\n'
    # list all regular files in path $1
    files=$(find $1 -type f)
    for f in $files ; do
        for l in $(cat $f) ; do
            # get list of all words in line
            all=$(echo $l | tr -s ' ' '\n')
            # get list of all unique words in line
            all_unique=$(echo $l | tr -s ' ' '\n' | sort -u)
            if [[ $all != $all_unique ]] ; then
                printf "In file %-15s some words are repeated in line \"%s\"\n" $f "$l"
            fi
        done
    done
}


# check arguments
if [ \( ! $# -eq 2 \) -o \( ! -d $1 \) ] ; then
    echo "Give path to folder as first argument and option as second argument"
    echo "OPTIONS:"
    echo "1 - count word occurences in all files in folder"
    echo "2 - count number of files in which word occures, for all words"
    echo "3 - list all lines in which word exists in files, for all words"
    echo "4 - list all lines with word duplications in them"
    exit
fi
# main program
folder=$1
option=$2
if [ $option == 1 ] ; then
    option1 $folder
elif [ $option == 2 ] ; then
    option2 $folder
elif [ $option == 3 ] ; then
    option3 $folder
elif [ $option == 4 ] ; then
    option4 $folder
fi

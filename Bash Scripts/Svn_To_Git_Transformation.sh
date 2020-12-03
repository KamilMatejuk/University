#!/bin/bash
# Kamil Matejuk, 250135


function getMessage {
    x="$(svn log --limit 1)"
    message=$(echo "$x" | sed -n 4p)
}

function addCommit {
    link=$2
    rev=$1
    svn co $link -r $rev newGitRepo > /dev/null 2>&1
    cd newGitRepo
    git init > /dev/null 2>&1
    echo ".svn" > .gitignore
    git add .gitignore > /dev/null 2>&1
    git add * > /dev/null 2>&1
    getMessage
    git commit -m "$message" > /dev/null 2>&1
    cd ..
}

# check if arguments exists
if [ $# -ne 3 ] ; then
    echo "Give revision numbers as 2 first arguments and url as third" && exit
fi
rm -rf newGitRepo/
# check each revision
for rev in $(seq $1 $2) ; do
    addCommit $rev $3
done

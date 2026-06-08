#!/bin/bash
rm -rf git_repository
mkdir git_repository
cd git_repository
git init

git config user.name "Egor"
git config user.email "my@gmail.com"

user_commit=(3 1 3 2 1 3 2 3 1 1 3 2 3 2 3 2 2 2 2 1 3 1 1 2 2 2 2 2 2 1 3 3 1 1 3 3 3 3 2 3 2 1 3 3 1 2 2 2 1 3 3 2 2 2 2 3 3 1 3 3 2 2 1 2 3 2 3 2 1 2 1 2 3)

cp -rf /home/studs/s467530/OPI/lab2/commits/commit0/* .
git add .
git commit -m "commit0"

git checkout -b feature2
cp -rf /home/studs/s467530/OPI/lab2/commits/commit1/* .
git add .
git commit -m "commit1"

git checkout -b feature1
cp -rf /home/studs/s467530/OPI/lab2/commits/commit2/* .
git add .
git commit -m "commit2"

for i in $(seq 0 72); do
    NUM=$((i+3))
    if [ "${user_commit[$i]}" == "1" ]; then
        git checkout master
    elif [ "${user_commit[$i]}" == "2" ]; then
        git checkout feature1
    elif [ "${user_commit[$i]}" == "3" ]; then
        git checkout feature2
    fi

    find . -maxdepth 1 ! -name '.git' ! -name '.' -exec rm -rf {} +
    cp -rf /home/studs/s467530/OPI/lab2/commits/commit${NUM}/* .
    git add -A
    git commit -m "commit${NUM}" --allow-empty
done

git checkout feature1
git merge master --no-edit
cp -rf /home/studs/s467530/OPI/lab2/commits/commit76/* .
git add -A
git commit -m "commit76: merge master into feature"

git checkout master
git merge feature1 --no-edit
cp -rf /home/studs/s467530/OPI/lab2/commits/commit77/* .
git add -A
git commit -m "commit77: merge feature into master"

#!/bin/bash
rm -rf svn_repository user1 user2 trunk_wc

user_commit=(3 1 3 2 1 3 2 3 1 1 3 2 3 2 3 2 2 2 2 1 3 1 1 2 2 2 2 2 2 1 3 3 1 1 3 3 3 3 2 3 2 1 3 3 1 2 2 2 1 3 3 2 2 2 2 3 3 1 3 3 2 2 1 2 3 2 3 2 1 2 1 2 3)
BASE_URL="file:///home/studs/s467530/OPI/lab2/svn_repository"

svnadmin create /home/studs/s467530/OPI/lab2/svn_repository

svn mkdir "${BASE_URL}/trunk" -m "create trunk"
svn mkdir "${BASE_URL}/branches" -m "create branches"

svn co "${BASE_URL}/trunk" trunk_wc
cp -rf /home/studs/s467530/OPI/lab2/commits/commit0/* trunk_wc/
cd trunk_wc
svn add --force *
svn commit -m "commit0" --username "user1"
cd ..

svn copy "${BASE_URL}/trunk" "${BASE_URL}/branches/user1" -m "create branches/user1" --username "user1"
svn co "${BASE_URL}/branches/user1" user1
cp -rf /home/studs/s467530/OPI/lab2/commits/commit1/* user1/
cd user1
svn add --force *
svn commit -m "commit1" --username "user1"
cd ..

svn copy "${BASE_URL}/branches/user1" "${BASE_URL}/branches/user2" -m "create branches/user2" --username "user2"
svn co "${BASE_URL}/branches/user2" user2
cp -rf /home/studs/s467530/OPI/lab2/commits/commit2/* user2/
cd user2
svn add --force *
svn commit -m "commit2" --username "user2"
cd ..

for i in $(seq 0 72); do
    NUM=$((i+3))

    if [ "${user_commit[$i]}" == "1" ]; then
        cd trunk_wc
        USER="user1"
    elif [ "${user_commit[$i]}" == "2" ]; then
        cd user2
        USER="user2"
    elif [ "${user_commit[$i]}" == "3" ]; then
        cd user1
        USER="user1"
    fi

    find . -maxdepth 1 ! -name '.svn' ! -name '.' -exec rm -rf {} +
    cp -rf /home/studs/s467530/OPI/lab2/commits/commit${NUM}/* .
    svn add --force *
    svn status | grep '^!' | awk '{print $2}' | xargs -r svn delete
    svn commit -m "commit${NUM}" --username "$USER"
    cd ..
done

cd user2
svn update
svn merge --accept=theirs-full "${BASE_URL}/trunk"
cp -rf /home/studs/s467530/OPI/lab2/commits/commit76/* .
svn resolve --accept=working --depth=infinity .
svn add --force *
svn status | grep '^!' | awk '{print $2}' | xargs -r svn delete
svn commit -m "commit76: merge trunk into branch" --username "user2"
cd ..

cd trunk_wc
svn update
svn merge --accept=theirs-full "${BASE_URL}/branches/user2"
cp -rf /home/studs/s467530/OPI/lab2/commits/commit77/* .
svn resolve --accept=working --depth=infinity .
svn add --force *
svn status | grep '^!' | awk '{print $2}' | xargs -r svn delete
svn commit -m "commit77: merge branch into trunk" --username "user1"
cd ..

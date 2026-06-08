REPO=/home/studs/s467530/OPI/lab2

rm -rf $REPO/svn_repository $REPO/user1 $REPO/user2

user_commit=(1 1 1 2 1 1 2 1 1 1 1 2 1 2 1 2 2 2 2 1 1 1 1 2 2 2 2 2 2 1 1 1 1 1 1 1 1 1 2 1 2 1 1 1 1 2 2 2 1 1 1 2 2 2 2 1 1 1 1 1 2 2 1 2 1 2 1 2 1 2 1 2 1)

svnadmin create $REPO/svn_repository
svn mkdir file://$REPO/svn_repository/trunk    -m "create trunk" -q
svn mkdir file://$REPO/svn_repository/branches -m "create branches" -q
svn co file://$REPO/svn_repository/trunk $REPO/user1 -q

do_commit() {
    local COMMIT=$1 WORKDIR=$2 MSG=$3 USER_ID=$4
    local FILES=$(ls $REPO/commits/commit$COMMIT/ 2>/dev/null | wc -l)
    
    if [ "$FILES" -eq 0 ]; then
        echo "commit$COMMIT | empty"
        return
    fi

    rm -f $WORKDIR/*.java $WORKDIR/*.* $WORKDIR/_ 2>/dev/null || true
    cp $REPO/commits/commit$COMMIT/* $WORKDIR/ 2>/dev/null || true
    
    cd $WORKDIR
    svn add --force * -q 2>/dev/null || true
    svn status | grep '^!' | awk '{print $2}' | xargs -r svn delete -q
    
    ADDED=$(svn status | grep '^A' | awk '{print $2}' | tr '\n' ',' | sed 's/,$//')
    MODIFIED=$(svn status | grep '^M' | awk '{print $2}' | tr '\n' ',' | sed 's/,$//')
    DELETED=$(svn status | grep '^D' | awk '{print $2}' | tr '\n' ',' | sed 's/,$//')
    
    INFO=""
    [ -n "$ADDED" ]    && INFO="${INFO} +[$ADDED]"
    [ -n "$MODIFIED" ] && INFO="${INFO} ~[$MODIFIED]"
    [ -n "$DELETED" ]  && INFO="${INFO} -[$DELETED]"
    [ -z "$INFO" ]     && INFO=" no changes"
    
    REV=$(svn commit -m "$MSG" 2>/dev/null | grep 'revision' | grep -o '[0-9]*')
    echo "commit$COMMIT | user${USER_ID} | svn r$REV |$INFO"
}

do_commit 0 $REPO/user1 "commit0" 1
do_commit 1 $REPO/user1 "commit1" 1

svn copy file://$REPO/svn_repository/trunk \
         file://$REPO/svn_repository/branches/user2 \
         -m "create branch for user2" -q
svn co file://$REPO/svn_repository/branches/user2 $REPO/user2 -q

for i in $(seq 0 72); do
    COMMIT=$((i+2))
    USER=${user_commit[$i]}
    if [ "$USER" == "1" ]; then
        WORKDIR=$REPO/user1
    else
        WORKDIR=$REPO/user2
    fi
    do_commit $COMMIT $WORKDIR "commit$COMMIT" $USER
done

do_commit 75 $REPO/user1 "commit75" 1

cd $REPO/user2
svn update -q
svn merge --accept=theirs-full file://$REPO/svn_repository/trunk -q
cp $REPO/commits/commit76/* $REPO/user2/ 2>/dev/null || true
svn resolve --accept=working --depth=infinity . -q 2>/dev/null || true
svn add --force * -q 2>/dev/null || true
svn status | grep '^!' | awk '{print $2}' | xargs -r svn delete -q
REV=$(svn commit -m "commit76: merge trunk into branch" 2>/dev/null | grep 'revision' | grep -o '[0-9]*')
echo "commit76 | user2 | svn r$REV | MERGE trunk->branch"

cd $REPO/user1
svn update -q
svn merge --accept=theirs-full file://$REPO/svn_repository/branches/user2 -q
cp $REPO/commits/commit77/* $REPO/user1/ 2>/dev/null || true
svn resolve --accept=working --depth=infinity . -q 2>/dev/null || true
svn add --force * -q 2>/dev/null || true
svn status | grep '^!' | awk '{print $2}' | xargs -r svn delete -q
REV=$(svn commit -m "commit77: merge branch into trunk" 2>/dev/null | grep 'revision' | grep -o '[0-9]*')
echo "commit77 | user1 | svn r$REV | MERGE branch->trunk"

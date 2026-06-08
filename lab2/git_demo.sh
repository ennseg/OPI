REPO=/home/studs/s467530/OPI/lab2

rm -rf $REPO/git_repository

user_commit=(1 1 1 2 1 1 2 1 1 1 1 2 1 2 1 2 2 2 2 1 1 1 1 2 2 2 2 2 2 1 1 1 1 1 1 1 1 1 2 1 2 1 1 1 1 2 2 2 1 1 1 2 2 2 2 1 1 1 1 1 2 2 1 2 1 2 1 2 1 2 1 2 1)

mkdir $REPO/git_repository
cd $REPO/git_repository
git init

cp $REPO/commits/commit0/* $REPO/git_repository/ 2>/dev/null || true
git add -A
git commit -m "commit0"

cp $REPO/commits/commit1/* $REPO/git_repository/ 2>/dev/null || true
git add -A
git commit -m "commit1"

git checkout -b feature

for i in $(seq 0 72); do
    COMMIT=$((i+2))
    USER=${user_commit[$i]}

    FILES=$(ls $REPO/commits/commit$COMMIT/ 2>/dev/null | wc -l)
    if [ "$FILES" -eq 0 ]; then
        echo "commit$COMMIT | пусто"
        continue
    fi

    if [ "$USER" == "1" ]; then
        git checkout master
    else
        git checkout feature
    fi

    rm -f $REPO/git_repository/*.java $REPO/git_repository/*.* $REPO/git_repository/_ 2>/dev/null || true
    cp $REPO/commits/commit$COMMIT/* $REPO/git_repository/ 2>/dev/null || true

    git add -A
    ADDED=$(git diff --cached --name-only --diff-filter=A | tr '\n' ',' | sed 's/,$//')
    MODIFIED=$(git diff --cached --name-only --diff-filter=M | tr '\n' ',' | sed 's/,$//')
    DELETED=$(git diff --cached --name-only --diff-filter=D | tr '\n' ',' | sed 's/,$//')
    INFO=""
    [ -n "$ADDED" ]    && INFO="${INFO} +[$ADDED]"
    [ -n "$MODIFIED" ] && INFO="${INFO} ~[$MODIFIED]"
    [ -n "$DELETED" ]  && INFO="${INFO} -[$DELETED]"
    [ -z "$INFO" ]     && INFO=" нет изменений"

    git commit -m "commit$COMMIT" -q
    echo "commit$COMMIT | user$USER |$INFO"
done

# commit75 — user1
git checkout master
FILES=$(ls $REPO/commits/commit75/ 2>/dev/null | wc -l)
if [ "$FILES" -eq 0 ]; then
    echo "commit75 | пусто"
else
    rm -f $REPO/git_repository/*.java $REPO/git_repository/*.* $REPO/git_repository/_ 2>/dev/null || true
    cp $REPO/commits/commit75/* $REPO/git_repository/ 2>/dev/null || true
    git add -A
    git commit -m "commit75" -q
    echo "commit75 | user1"
fi

# commit76 — user2 merge из master
git checkout feature
git merge master --no-edit
cp $REPO/commits/commit76/* $REPO/git_repository/ 2>/dev/null || true
git add -A
git commit -m "commit76: merge master into feature" --allow-empty -q
echo "commit76 | user2 | MERGE master->feature"

# commit77 — user1 финальный merge
git checkout master
git merge feature --no-edit
cp $REPO/commits/commit77/* $REPO/git_repository/ 2>/dev/null || true
git add -A
git commit -m "commit77: merge feature into master" --allow-empty -q
echo "commit77 | user1 | MERGE feature->master"
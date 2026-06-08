#!/bin/bash
HISTORY_DIR=$1
SRC_DIR=$2

mkdir -p $HISTORY_DIR

REVISIONS=$(git log --format='%H' 2>/dev/null)
if [ -z "$REVISIONS" ]; then
  echo "Git история недоступна"
  exit 0
fi

LAST_GOOD=""
PREV_REV=""

for REV in $REVISIONS; do
  echo "Проверяем ревизию: ${REV:0:7}"
  WORK_DIR=$HISTORY_DIR/rev_$REV
  mkdir -p $WORK_DIR
  git archive $REV | tar -x -C $WORK_DIR 2>/dev/null
  if [ -d $WORK_DIR/$SRC_DIR ]; then
    cd $WORK_DIR
    mvn compile -q 2>/dev/null
    RESULT=$?
    cd -
    if [ $RESULT -eq 0 ]; then
      echo "Найдена рабочая ревизия: ${REV:0:7}"
      LAST_GOOD=$REV
      break
    else
      PREV_REV=$REV
    fi
  fi
done

if [ -n "$LAST_GOOD" ] && [ -n "$PREV_REV" ]; then
  git diff $LAST_GOOD $PREV_REV -- '*.java' > $HISTORY_DIR/changes.diff 2>/dev/null
  echo "Последняя рабочая ревизия: $LAST_GOOD" > $HISTORY_DIR/result.txt
  echo "Следующая сломанная ревизия: $PREV_REV" >> $HISTORY_DIR/result.txt
  echo "Diff сохранён в: $HISTORY_DIR/changes.diff" >> $HISTORY_DIR/result.txt
  cat $HISTORY_DIR/result.txt
elif [ -n "$LAST_GOOD" ]; then
  echo "Текущая версия работает, история чистая" > $HISTORY_DIR/result.txt
  cat $HISTORY_DIR/result.txt
else
  echo "Рабочая ревизия не найдена" > $HISTORY_DIR/result.txt
fi

echo "HISTORY: Завершено"
#!/bin/bash
TEAM_DIR=$1
JAR_NAME=$2
WAR_NAME=$3
REVISIONS_COUNT=$4

mkdir -p $TEAM_DIR

REVISIONS=$(git log --format='%H' -$REVISIONS_COUNT 2>/dev/null)
if [ -z "$REVISIONS" ]; then
  echo "Нет ревизий в git"
  exit 0
fi

COUNT=1
for REV in $REVISIONS; do
  echo "Обрабатываем ревизию $COUNT: ${REV:0:7}"
  WORK_DIR=$TEAM_DIR/rev_$REV
  mkdir -p $WORK_DIR
  git archive $REV | tar -x -C $WORK_DIR 2>/dev/null || echo "Не удалось извлечь ревизию $REV"
  if [ -d $WORK_DIR/src ]; then
    cd $WORK_DIR
    mvn compile package -q -DskipTests 2>/dev/null && echo "Ревизия ${REV:0:7} собрана" || echo "Ревизия ${REV:0:7} не собрана"
    cd -
    if [ -f $WORK_DIR/target/$WAR_NAME.war ]; then
      cp $WORK_DIR/target/$WAR_NAME.war $TEAM_DIR/$JAR_NAME-rev${COUNT}-${REV:0:7}.jar
    fi
  fi
  COUNT=$((COUNT+1))
done

echo "Упаковываем результаты в ZIP"
cd $TEAM_DIR
zip -r $JAR_NAME-team-revisions.zip *.jar 2>/dev/null || echo "Нет jar-файлов для упаковки"
cd -
echo "TEAM: Готово — $TEAM_DIR/$JAR_NAME-team-revisions.zip"
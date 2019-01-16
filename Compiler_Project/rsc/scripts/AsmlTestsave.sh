#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"


i=0
echo "================> ASML Tests"
for test_case in ../tests/asml/*.ml
do
    echo $test_case
    file=$i'_tmp.asml'
    java $CPARG AsmlTest $test_case $file
    ./../tests/asml/asml $file
    i=`expr $i + 1`
done

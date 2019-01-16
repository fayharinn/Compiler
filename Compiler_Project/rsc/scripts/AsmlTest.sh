#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ASML Tests"
for test_case in ../tests/asml/*.ml
do
    echo $test_case
    java $CPARG AsmlTest $test_case _tmp.asml
    ./../tests/asml/asml _tmp.asml > _tmpres0
    ocaml $test_case > _tmpres1

    DIFF=`diff _tmpres0 _tmpres1`

    if [ "$DIFF" = "" ]
    then
        echo "OK"
    else
        echo "KO"
    fi
done

rm _tmp.asml _tmpres0 _tmpres1

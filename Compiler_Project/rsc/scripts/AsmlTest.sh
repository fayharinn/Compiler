#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ASML Tests"
for test_case in ../tests/asml/*.ml
do
    echo $test_case
    java $CPARG AsmlTest $test_case _tmp.asml
    ./../tests/asml/asml _tmp.asml > _tmpres0 2> /dev/null
    ocaml $test_case > _tmpres1 2> /dev/null

    DIFF=`diff _tmpres0 _tmpres1`

    if [ -f _tmp.asml ]
    then
        if [ -f _tmpres0 ]
        then
            if [ -f _tmpres1 ]
            then
                if [ "$DIFF" = "" ]
                then
                    echo "OK"
                else
                    echo "KO"
                fi
            else
                echo "KO"
            fi
        else
            echo "KO"
        fi
    else
        echo "KO"
    fi
    rm _tmp.asml _tmpres0 _tmpres1
done

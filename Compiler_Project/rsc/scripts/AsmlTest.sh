#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ASML Tests"
for test_case in ../tests/asml/*.ml
do
    echo $test_case
    fileName=${test_case%.*}
    fileName=${fileName##*/}
    pathName='../tests/asml/generated_asml/'$fileName'.asml'
    java $CPARG AsmlTest $test_case $pathName 2> /dev/null
    if [ -f $pathName ]
    then
        ./../tests/asml/asml $pathName > _tmpres0 2> /dev/null
        if [ -f _tmpres0 ]
        then
            ocaml $test_case > _tmpres1 2> /dev/null
            if [ -f _tmpres1 ]
            then
                DIFF=`diff _tmpres0 _tmpres1`
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
    rm _tmpres0 _tmpres1 #$pathName
done

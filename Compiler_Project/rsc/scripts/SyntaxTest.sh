#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo ""
echo ""
echo "================> Syntax Tests"
echo "=====> Valid"
for test_case in ../tests/syntax/valid/*.ml
do
    echo $test_case
    java $CPARG SyntaxTest $test_case 2> /dev/null 1> /dev/null
    STATUS="${?}"
    # Suivant la valeur de retour l'affichage diffère
    if [ $STATUS = 0 ]
    then
        echo "OK"
    else
        if [ $STATUS = 1 ]
        then
            echo "KO"
        else
            if [ $STATUS = 2 ]
            then
                echo "ERROR"
            fi
        fi
    fi
done

echo "=====> Invalid"
for test_case in ../tests/syntax/invalid/*.ml
do
    echo $test_case
    java $CPARG SyntaxTest $test_case 2> /dev/null 1> /dev/null
    STATUS="${?}"
    if [ $STATUS = 0 ]
    then
        echo "OK"
    else
        if [ $STATUS = 1 ]
        then
            echo "KO"
        else
            if [ $STATUS = 2 ]
            then
                echo "ERROR"
            fi
        fi
    fi
done

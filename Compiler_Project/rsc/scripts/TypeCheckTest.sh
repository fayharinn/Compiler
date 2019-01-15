#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out/production/Compiler_Project"

echo ""
echo ""
echo "================> TypeCheck Tests"
echo "=====> Valid"
for test_case in ../tests/typecheck/valid/*.ml
do
    echo $test_case
    java $CPARG TypeCheckTest $test_case 2> /dev/null 1> /dev/null
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
for test_case in ../tests/typecheck/invalid/*.ml
do
    echo $test_case
    java $CPARG TypeCheckTest $test_case 2> /dev/null 1> /dev/null
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

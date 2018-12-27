#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out/production/Compiler_Project"

HELP=0
VERSION=0
SYNTAXTEST=0
TYPECHECKTEST=0
ASMLTEST=0
ARMTEST=0

for param in "$@"
do
    case $param in
        "-h")
        HELP=1
        ;;
        "-v")
        VERSION=1
        ;;
        "-p")
        SYNTAXTEST=1
        ;;
        "-t")
        TYPECHECKTEST=1
        ;;
        "-asml")
        ASMLTEST=1
        ;;
        "-o")
        ARMTEST=1
        ;;
    esac
done

# S'il y a 0 option de spécifié on affiche un message et l'aide
TOTAL=`expr $HELP + $VERSION + $SYNTAXTEST + $TYPECHECKTEST + $ASMLTEST + $ARMTEST`
if [ $TOTAL = 0 ]
then
    echo "You have failed to specify what to do correctly."
    HELP=1
fi

# ==============================================================================================
# ==============================================================================================
if [ $HELP = 1 ]
then
    echo "TODO HELP"
fi
# ==============================================================================================
# ==============================================================================================
if [ $VERSION = 1 ]
then
    echo "TODO VERSION"
fi
# ==============================================================================================
# ==============================================================================================
if [ $SYNTAXTEST = 1 ]
then

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

fi
# ==============================================================================================
# ==============================================================================================
if [ $TYPECHECKTEST = 1 ]
then
    echo "================> TypeCheck Tests"
    echo "Not implemented yet."
    #Idem que SYNTAXTEST, à faire quand TypeCheck sera merge
fi
# ==============================================================================================
# ==============================================================================================
if [ $ASMLTEST = 1 ]
then

    echo "================> ASML Tests"
    echo "Not implemented yet."
    #for test_case in ../tests/asml/*.ml
    #do
        #echo $test_case
        # On génère le code dans un fichier
        #java $CPARG AsmlTest $test_case > _tmp0.asml
        #TODO génération asml > _tmp1.asml
        #TODO comparer les 2 fichiers
        #TODO affichier OK ou KO suivant la comparaison
        #rm _tmp0.asml
        #rm _tmp1.asml
    #done

fi
# ==============================================================================================
# ==============================================================================================
if [ $ARMTEST = 1 ]
then

    echo "================> ARM Tests"
    echo "Not implemented yet."
    #for test_case in ../tests/arm/*.ml
    #do
        #echo $test_case
        # On génère le code dans un fichier
        #java $CPARG ArmTest $test_case > _tmp0.asml
        #TODO génération asml > _tmp1.asml
        #TODO comparer les 2 fichiers
        #TODO affichier OK ou KO suivant la comparaison
        #rm _tmp0.asml
        #rm _tmp1.asml
    #done

fi
# ==============================================================================================
# ==============================================================================================

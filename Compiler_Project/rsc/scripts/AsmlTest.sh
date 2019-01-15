#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ASML Tests"
echo "Not implemented yet."
#for test_case in ../tests/asml/*.asml
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

test_case="../tests/asml/boolean_if.asml"
echo $test_case
# On génère le code dans un fichier
java $CPARG AsmlTest $test_case _tmp0.asml
#TODO génération asml > _tmp1.asml
#TODO comparer les 2 fichiers
#TODO affichier OK ou KO suivant la comparaison
#rm _tmp0.asml
#rm _tmp1.asml

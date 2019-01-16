#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ASML Tests"
for test_case in ../tests/asml/*.ml
do
    echo $test_case
    java $CPARG AsmlTest $test_case $test_case+"_tmp.asml"
    ./../tests/asml/asml $test_case+"_tmp.asml"
done

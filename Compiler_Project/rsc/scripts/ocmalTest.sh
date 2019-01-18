#!/bin/sh
echo "================> ocaml Tests"
for test_case in ../tests/ARM/*.ml
do

    echo " ----------------> "
    echo $test_case
    ocaml $test_case
    echo ""
done

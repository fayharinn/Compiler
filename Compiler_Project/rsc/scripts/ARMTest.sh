#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ARM Tests"
echo "========> Generating .s"

for test_case in ../tests/ARM/*.ml
do
    echo $test_case
    fileName=${test_case%.*}
    fileName=${fileName##*/}
    pathName='../tests/ARM/arm/'$fileName'.s'
    java $CPARG ArmTest $test_case $pathName
    arm-eabi-as -o $fileName'.o' $fileName'.s' libmincaml.S 2> /dev/null
    arm-eabi-ld -o $fileName'.arm' $fileName'.o' 2> /dev/null

    qemu-arm $fileName'.arm' > _tmpres0 2> /dev/null
    ocaml $test_case > _tmpres1 2> /dev/null
done

#!/bin/sh
CPARG="-cp ../../java-cup-11b-runtime.jar:../../java-cup-11b.jar:../../out"

echo "================> ARM Tests"
echo "========> Generating .s"

for test_case in ../tests/ARM/*.ml
do
    echo $test_case
    fileName=${test_case%.*}
    fileName=${fileName##*/}
    path='../tests/ARM/generated_ARM/'
    pathName=$path''$fileName
    java $CPARG ArmTest $test_case $pathName'.s'
    if [ -f $pathName'.s' ]
    then
        /opt/gnu/arm/bin/arm-eabi-as -o $pathName'.o' $pathName'.s' $path'libmincaml.S'
        if [ -f $pathName'.o' ]
        then
            /opt/gnu/arm/bin/arm-eabi-ld -o $pathName'.arm' $pathName'.o'
            if [ -f $pathName'.arm' ]
            then
                qemu-arm $pathName'.arm' > _tmpres0
                if [ -f _tmpres0 ]
                then
                    ocaml $test_case > _tmpres1
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
        else
            echo "KO"
        fi
    else
        echo "KO"
    fi
    rm _tmpres0 _tmpres1 $pathName'.o' $pathName'.arm' #$pathName'.s'

done

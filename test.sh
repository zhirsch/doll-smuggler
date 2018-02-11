#!/bin/bash

cd $(dirname $0)

lein test || exit 1

PASS=y

echo
echo "testing examples"
for x in examples/*; do
    [[ ! -d $x ]] && continue
    echo -n "$(basename $x) ... "
    if ! diff -q $x/output.txt <(lein run $x/input.txt) >/dev/null; then
        echo "FAILED"
        PASS=n
    else
        echo "OK"
    fi
done
echo

[[ $PASS = y ]] || exit 1

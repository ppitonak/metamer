#!/bin/bash
echo "####################### NOT TRACKED ISSUES ###########################"
find -name "stacktrace.txt" | sed -r 's#stacktrace.txt$#issues.txt#' | xargs ls 2>&1 | grep "cannot access" | sed -r 's#ls: cannot access \.\/target\/failures\/##' | sed -r 's#\/[^\/]+\/issues\.txt: No such file or directory$##'
echo "######################################################################"

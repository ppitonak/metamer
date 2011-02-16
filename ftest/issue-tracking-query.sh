#!/bin/bash
issues=`find -name "issues.txt" -exec cat {} \; -exec echo '' \; | sed -r 's# #\n#' | sort | uniq | egrep 'RF(PL)?-' | sed -r 's#.*/(RF(PL)?-[0-9]+)#\1,#'`
issues=`echo $issues | sed -r 's#,[^A-Z]*$##'`

echo "####################################################################"
echo "############### ALL TRACKED ISSUES #################################"
echo "####################################################################"
echo "project IN (RF, RFPL) AND key in ($issues) ORDER BY status DESC, key DESC"
echo "####################################################################"
echo "############### ISSUES WHICH ARE NOT LABELED IN JIRA ###############"
echo "####################################################################"
echo "project IN (RF, RFPL) AND key in ($issues) AND labels != "metamer-ftest-tracked" ORDER BY status DESC, key DESC"
echo "####################################################################"
echo "############### ISSUES LABEL IN JIRA BUT PASSING ###################"
echo "####################################################################"
echo "project IN (RF, RFPL) AND key NOT IN ($issues) AND labels = metamer-ftest-tracked ORDER BY status DESC, key DESC"
echo "####################################################################"

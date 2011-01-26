#!/bin/bash
issues=`find -name "issues.txt" -exec cat {} \; -exec echo '' \; | sed -r 's# #\n#' | sort | uniq | grep RF- | sed -r 's#.*/(RF-[0-9]+)#\1,#'`
issues=`echo $issues | sed -r 's#,[^A-Z]*$##'`

echo "===================================================================="
echo "=============== ALL TRACKED ISSUES ================================="
echo "===================================================================="
echo "project = RF AND key in ($issues) AND status != Closed ORDER BY status DESC, key DESC"
echo "===================================================================="
echo "=============== ISSUES WHICH ARE NOT LABELED IN JIRA ==============="
echo "===================================================================="
echo "project = RF AND key in ($issues) AND status != Closed AND labels != "metamer-ftest-tracked" ORDER BY status DESC, key DESC"
echo "===================================================================="

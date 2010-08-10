#!/bin/bash
RICHFACES_VERSION=`grep '<version.richfaces>' pom.xml | sed -r 's#.*>([^<]+)<.*#\1#'`

echo "Project defines following version of RichFaces ${RICHFACES_VERSION}"
echo $RICHFACES_VERSION | egrep -q '\-SNAPSHOT$' && { echo "The project cannot depend on SNAPSHOT version, correct the version first and try again."; exit 2; }
echo "Is this version correct?"
read -p 'Press ENTER to continue or Ctrl+C for exit...'
read -p 'Enter release version: ' RELEASE
read -p 'Enter new development version: ' DEVELOPMENT
CONF="--batch-mode -Dtag=metamer-${RELEASE} -DreleaseVersion=${RELEASE} -DdevelopmentVersion=${DEVELOPMENT}"
echo "Configuration: ${CONF}"
read -p 'Press ENTER to clean...'
mvn release:clean clean
read -p 'Press ENTER to dry run...'
mvn release:prepare -DdryRun=true ${CONF} || exit 1
read -p 'Press ENTER to clean...'
mvn release:clean
read -p 'Press ENTER to prepare...'
mvn clean release:prepare ${CONF} || exit 1
read -p 'Press ENTER to perform...'
mvn release:perform ${CONF} || exit 1

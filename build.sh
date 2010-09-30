#!/bin/bash
mvn clean install -Pmyfaces,war-myfaces -Dselenium.server.skip=true -Dselenium.test.skip=true $*

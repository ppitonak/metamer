#!/bin/bash
mvn clean install -Pmyfaces -Dselenium.server.skip=true -Dselenium.test.skip=true $*

#!/bin/bash
mvn clean install -Dselenium.server.skip=true -Dselenium.test.skip=true $*

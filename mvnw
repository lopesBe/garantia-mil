#!/usr/bin/env sh
# ----------------------------------------------------------------------------
# Maven Wrapper
# ----------------------------------------------------------------------------
# Copyright 2011-2017 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ----------------------------------------------------------------------------

basedir="$(dirname "$0")"

if [ -z "$MAVEN_PROJECTBASEDIR" ]; then
  MAVEN_PROJECTBASEDIR="$basedir"
fi

MAVEN_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
  echo "Maven Wrapper jar not found: $MAVEN_WRAPPER_JAR"
  exit 1
fi

if [ -z "$MAVEN_HOME" ]; then
  exec java -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" -cp "$MAVEN_WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
else
  exec "$MAVEN_HOME/bin/mvn" -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" "$@"
fi

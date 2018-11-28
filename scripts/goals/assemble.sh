#!/bin/bash

source "./scripts/utils/check_exit_code.sh"

./gradlew assemble -x test --console=plain --no-daemon --stacktrace --rerun-tasks ; CHECK_EXIT_CODE
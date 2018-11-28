#!/bin/bash

function CHECK_EXIT_CODE() {
    exit_code=$?
    if [ ${exit_code} -ne "0" ]; then
        echo
        echo "ERROR: ${0} returned not zero exit code ${exit_code}"
        osascript -e 'tell app "System Events" to display dialog "Script returned not zero exit code. Please check terminal!" with title "ERROR"'
        exit ${exit_code}
    fi
}

export -f CHECK_EXIT_CODE
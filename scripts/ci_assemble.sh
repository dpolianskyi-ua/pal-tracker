#!/bin/bash

source "./scripts/utils/check_exit_code.sh"

./scripts/helpers/create_mysql_scheme.sh

./scripts/migrate-database.sh

./scripts/goals/assemble.sh

CHECK_EXIT_CODE
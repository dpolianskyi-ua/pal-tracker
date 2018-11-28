#!/bin/bash

source "./scripts/utils/check_exit_code.sh"

mysql -uroot < databases/tracker/create_databases.sql
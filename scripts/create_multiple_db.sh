#!/bin/bash
set -e
set -u

function create_user() {
  local user
  user=$1
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    DO
    \$do$
    BEGIN
      CREATE ROLE $user LOGIN PASSWORD '$user';
      EXCEPTION WHEN duplicate_object THEN
      RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
    END
    \$do$
EOSQL
  echo "$user created"
}

function create_database() {
  local database
  database=$1
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
      SELECT 'CREATE DATABASE $database'
      WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$database')\gexec
EOSQL
}

function grant_privileges() {
  local database
  local user
  database=$1
  user=$2
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
      GRANT ALL PRIVILEGES ON DATABASE $database TO $POSTGRES_USER;
      GRANT ALL PRIVILEGES ON DATABASE $database TO $user;
EOSQL
}
if [ -n "$POSTGRES_ADDITIONAL_DATABASES" ]; then
  echo "Multiple database creation requested: $POSTGRES_ADDITIONAL_DATABASES"
  for db in $(echo "$POSTGRES_ADDITIONAL_DATABASES" | tr ',' ' '); do
    database=$(echo $db | tr ':' ' ' | awk '{print $1}')
    owner=$(echo $db | tr ':' ' ' | awk '{print $2}')
    create_user $owner
    create_database $database
    grant_privileges $database $owner
  done
  echo "Multiple databases created"
fi

#!/usr/bin/env bash
set -e

url=$1
shift
cmd="$@"

until curl -sf "$url" > /dev/null; do
  >&2 echo " $url is unavailable - waiting"
  sleep 5
done

>&2 echo " $url is up - executing command"
exec $cmd
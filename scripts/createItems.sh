#!/usr/bin/env bash
for i in {1..20} ; do
  curl -H "Content-Type: application/json" -d '{"name": "testItem-'"$i"'", "amount": "500g"}' "localhost:9000/item"
done

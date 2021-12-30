#!/usr/bin/env bash
for i in {1..2} ; do
  curl -H "Content-Type: application/json" -d '{"name": "testItem-'"$i"'", "amount": "500g"}' "localhost:8080/item1"
done

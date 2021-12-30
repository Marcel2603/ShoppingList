#!/usr/bin/env bash
for i in {5..405} ; do
  curl -H "Content-Type: application/json" -d '{"name": "testItem-'"$i"'", "amount": '"$i"'}' "localhost:9000/item"
done

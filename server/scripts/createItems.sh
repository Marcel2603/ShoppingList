#!/usr/bin/env bash
for i in {1..200} ; do
  curl "localhost:8080/item/test-$i"&
done
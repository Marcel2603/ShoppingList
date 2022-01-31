# The @txn tag enables a Transaction open-rollback around each Scenario,
# Preventing persisted data from leaking between Scenarios.
# Try removing the @txn tag and see what happens.
@txn
Feature: Testing ShoppingListApi

  Scenario: Inserting Item
    Given an Item "Mehl" "500g"
    When the item gets created
    Then the server returns "200"

  Scenario: Get Hash for existing List
    Given an Item in the database
    When getting hash
    Then the server returns "200"
    And the hash is equal to the item as list

  Scenario: Get Hash for empty List
    Given no Item is in the database
    When getting hash
    Then the server returns "200"
    And the hash is equal to "1"
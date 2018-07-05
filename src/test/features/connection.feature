Feature: Friend connection

  Scenario: Add connection
    When andy add john
    Then success is true

  Scenario: Add duplicate connection
    Given andy add john
    When andy add john
    Then success is false

  Scenario: Can not add connection when block
    Given john block andy
    When andy add john
    Then success is false
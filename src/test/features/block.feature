Feature: Block update

  Scenario: Block
    When lisa block john
    Then success is true

  Scenario: Already block
    Given lisa block john
    When lisa block john
    Then success is false
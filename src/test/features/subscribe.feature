Feature: Subscribe update

  Scenario: Subscribe
    When lisa subscribe john
    Then success is true

  Scenario: Already subscribe
    Given lisa subscribe john
    When lisa subscribe john
    Then success is false




Feature: Common Friend

  Scenario: No common friend
    When get common for andy and john
    Then success is true
    And no common friend

  Scenario: Has common friend
    Given andy add common
    And john add common
    When get common for andy and john
    Then success is true
    And friend is common
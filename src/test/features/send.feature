Feature: Send text

  Scenario: Has a friend connection
    Given john add lisa
    When john send 'Hello World!'
    Then success is true
    And recipient is 'lisa'

  Scenario: Has a friend connection with block
    Given john add lisa
    And lisa block john
    When john send 'Hello World!'
    Then success is true
    And recipient is empty

  Scenario: Has subscribed
    Given lisa subscribe john
    When john send 'Hello World!'
    Then success is true
    And recipient is 'lisa'

  Scenario: Has subscribed with block
    Given lisa subscribe john
    And john block lisa
    When john send 'Hello World!'
    Then success is true
    And recipient is empty

  Scenario: Has been mentioned
    When john send 'Hello World! kate@example.com'
    Then success is true
    And recipient is 'kate'

  Scenario: Has been mentioned with block
    Given kate block john
    When john send 'Hello World! kate@example.com'
    Then success is true
    And recipient is empty
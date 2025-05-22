Feature: SIM Card Activation

  Scenario: Successful SIM card activation
    Given the SIM card with ICCID "1255789453849037777" and email "success@test.com" is submitted for activation
    When I query the status of the SIM card with ID 1
    Then the activation should be successful

  Scenario: Failed SIM card activation
    Given the SIM card with ICCID "8944500102198304826" and email "fail@test.com" is submitted for activation
    When I query the status of the SIM card with ID 2
    Then the activation should be unsuccessful

@ui @functional @end_to_end
Feature: Complete Purchase Flow

  Scenario: Successful end-to-end user journey
    Given the SauceDemo login page is displayed
    When the username "standard_user" and password "secret_sauce" are entered
    And the login button is clicked
    Then the inventory page should be displayed

    # Adding items to the cart
    When the products are added to the cart
    Then the shopping cart badge should display 2

    # Navigating to the cart
    When the shopping cart page is opened
    Then the cart should contain the product "Sauce Labs Backpack"
    And the cart should contain the product "Sauce Labs Bolt T-Shirt"

    # Proceeding to checkout
    When the checkout button is clicked
    Then the checkout information page should be displayed

    # Filling out shipping details
    When the first name "John", last name "Doe", and postal code "12345" are entered
    And the continue button is clicked
    Then the order summary page should be displayed

    # Completing the order
    When the finish button is clicked
    Then the order confirmation page should be displayed with the message "Thank you for your order!"

    # Returning to the home page
    When the back home button is clicked
    Then the inventory page should be displayed

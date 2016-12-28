Scenario: User creates an account

Given user opens http://demoqa.com/

When title is Demoqa | Just another WordPress site
Then go to registration page http://demoqa.com/registration/ and register:
|first|last|status|hobby|country|phone|username|email|password|
|John|Smith|single|reading|random|random|jhnsmth|random|random|
|random|random|single|reading|random|random|random|random|random|

When on http://demoqa.com/droppable/ page
Then drag and drop an element
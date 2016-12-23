Scenario: User tries to login
 
Given user opens http://phptravels.net

When title is PHPTRAVELS | Travel Technology Partner or FJ Travels | Travel Partner
Then go to login page http://phptravels.net/login

When on Login page
Then enter email user@phptravels.com, password demouser and submit
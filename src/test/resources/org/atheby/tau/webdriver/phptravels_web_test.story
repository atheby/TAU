Scenario: User tries to login and search for a flight

Given user opens http://phptravels.net

When title is PHPTRAVELS | Travel Technology Partner or FJ Travels | Travel Partner or FJ Travels & Tours
Then go to login page http://phptravels.net/login

When on "Login" page
Then enter email user@phptravels.com, password demouser and submit

When on "My Account" page
Then open page http://phptravels.net/flightsw

When on "Flights" page
Then search for a flight:
|from|to|outbound|inbound|class|
|London|Paris|2017-03-13|2017-03-20|Business|
|Berlin|Prague|2017-04-13|2017-04-20|Economy|
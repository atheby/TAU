

Scenario: Removes random number

Given a gra

When user enters 787
Then should remove random digit from it and output 78 or 87 or 77

When user enters single digit like 6
Then nothing should happen

Scenario: Switches the position of the two digits in a number

Given a gra

When user enters number like 386
Then after switch should return 836 or 683 or 368

Scenario: If present, switches digits with a pattern 3->8, 7->1, 6->9

Given a gra

When user enters number where one of the digit is present like 106
Then the result should be 109

When user enters number without digit from pattern like 54
Then the result should be 54


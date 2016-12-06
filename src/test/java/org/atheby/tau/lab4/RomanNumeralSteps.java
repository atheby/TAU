package org.atheby.tau.lab4;

import org.atheby.tau.lab1.RomanNumeral;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RomanNumeralSteps {

    private RomanNumeral rn;

    @Given("arabic number")
    public void setup() {}

    @Then("roman number should be: $numbers")
    public void output(ExamplesTable numbers) {
        for (Map<String, String> row : numbers.getRows()) {
            Integer arabic = Integer.parseInt(row.get("arabic"));
            String roman = row.get("roman");
            rn = new RomanNumeral(arabic);
            assertEquals(roman, rn.toString());
        }
    }
}
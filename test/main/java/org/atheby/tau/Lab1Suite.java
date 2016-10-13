package main.java.org.atheby.tau;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import main.java.org.atheby.tau.lab1.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  CalculatorTest.class,
  CalculatorDoubleTest.class,
  RomanNumeralTest.class,
  PsikusCyfrokradTest.class,
  PsikusHultajChohlaTest.class,
  PsikusNieksztaltekTest.class
})

public class Lab1Suite {}

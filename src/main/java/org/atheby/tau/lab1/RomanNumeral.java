package main.java.org.atheby.tau.lab1;

import java.util.*;

public class RomanNumeral {

	private int number;
	private LinkedHashMap<String, Integer> map;
	
	public RomanNumeral(int n) {
		if(n > 0)
			number = n;
		else
			throw new IllegalArgumentException("Number must be greater than 0");
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		return convert(getNumber());
	}
	
	private String convert(int num) {
		String romanNumeral = "";
		initMap();
	    for(Map.Entry<String, Integer> entry : map.entrySet()) {
	    	int multiplier = num / entry.getValue();
	    	romanNumeral += append(entry.getKey(), multiplier);
	    	num = num % entry.getValue();
	    }
	    return romanNumeral;
	}
	
	private String append(String numeral, int multiplier) {
		String str = "";
	    for(int x = 0; x < multiplier; x++) {
	        str += numeral;
	    }
	    return str;
	}
	
	private void initMap() {
		map = new LinkedHashMap<String, Integer>();
		map.put("M", 1000);
	    map.put("CM", 900);
	    map.put("D", 500);
	    map.put("CD", 400);
	    map.put("C", 100);
	    map.put("XC", 90);
	    map.put("L", 50);
	    map.put("XL", 40);
	    map.put("X", 10);
	    map.put("IX", 9);
	    map.put("V", 5);
	    map.put("IV", 4);
	    map.put("I", 1);
	}
}

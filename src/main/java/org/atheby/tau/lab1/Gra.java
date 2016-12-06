package org.atheby.tau.lab1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Gra implements Psikus {
	
	public Integer cyfrokrad(Integer liczba) {
		if(liczba >= -9 && liczba <= 9)
			return null;
		boolean sign = false;
		if(liczba < -9)
			sign = true;
		List<String> strNumber = getList(Math.abs(liczba));
		int pos = getRandom(0, strNumber.size() - 1);
		strNumber.remove(pos);
		liczba = getNumberFromList(strNumber);
		if(sign)
			liczba = -liczba;
		return liczba;
	}

	public Integer hultajchochla(Integer liczba) throws NieduanyPsikusException {
		if(liczba >= -9 && liczba <= 9)
			throw new NieduanyPsikusException("Wystapil NieudanyPsikusException");
		boolean sign = false;
		if(liczba < -9)
			sign = true;
		List<String> strNumber = getList(Math.abs(liczba));
		int firstPos, secondPos;
		firstPos = getRandom(0, strNumber.size() - 1);
		do {
			secondPos = getRandom(0, strNumber.size() - 1);
		} while (firstPos == secondPos);
		String temp = strNumber.get(firstPos);
		strNumber.set(firstPos, strNumber.get(secondPos));
		strNumber.set(secondPos, temp);
		liczba = getNumberFromList(strNumber);
		if(sign)
			liczba = -liczba;
		return liczba;
	}

	public Integer nieksztaltek(Integer liczba) {
		boolean sign = false;
		if(liczba < 0)
			sign = true;
		List<String> strNumber = getList(Math.abs(liczba));

		boolean isNumberFromPattern = false;
		for(int x = 0; x < strNumber.size(); x++) {
			String s = strNumber.get(x);
			if(s.equals("3") || s.equals("7") || s.equals("6"))
				isNumberFromPattern = true;
		}

		if(!isNumberFromPattern)
			return liczba;

		boolean isSwitched = false;
		do {
			int pos = getRandom(0, strNumber.size() - 1);
			if(strNumber.get(pos).equals("3")) {
				strNumber.set(pos, "8");
				isSwitched = true;
			}
			if(strNumber.get(pos).equals("7")) {
				strNumber.set(pos, "1");
				isSwitched = true;
			}
			if(strNumber.get(pos).equals("6")) {
				strNumber.set(pos, "9");
				isSwitched = true;
			}
		} while(!isSwitched);
		liczba = getNumberFromList(strNumber);
		if(sign)
			liczba = -liczba;
		return liczba;
	}

	private int getRandom(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	private List<String> getList(Integer number) {
		List<String> numberList = new ArrayList<>();
		String strNumber = number.toString();
		
		for(int x = 0; x < strNumber.length(); x++)
			numberList.add(strNumber.substring(x, x + 1));
		return numberList;
	}

	private Integer getNumberFromList(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < list.size(); x++)
			sb.append(list.get(x));
		return Integer.parseInt(sb.toString());
	}
}

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
		liczba = Math.abs(liczba);
		String[] arr = liczba.toString().split("");
		StringBuilder sb = new StringBuilder();
		int pos = getRandom(0, arr.length - 1);
		for(int x = 0; x < arr.length; x++)
			if(x == pos)
				continue;
			else
				sb.append(arr[x]);
		if(sign) {
			int tmp = Integer.parseInt(sb.toString());
			tmp = -tmp;
			return tmp;
		}
		return Integer.parseInt(sb.toString());
	}

	public Integer hultajchochla(Integer liczba) throws NieduanyPsikusException {
		if(liczba >= -9 && liczba <= 9)
			throw new NieduanyPsikusException("Wystapil NieudanyPsikusException");
		boolean sign = false;
		if(liczba < -9)
			sign = true;
		liczba = Math.abs(liczba);
		String[] arr = liczba.toString().split("");
		int firstPos, secondPos;
		do {
			firstPos = getRandom(0, arr.length - 1);
			secondPos = getRandom(0, arr.length - 1);
		} while (firstPos == secondPos);
		String temp = arr[firstPos];
		arr[firstPos] = arr[secondPos];
		arr[secondPos] = temp;
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < arr.length; x++)
			sb.append(arr[x]);
		if(sign) {
			int tmp = Integer.parseInt(sb.toString());
			tmp = -tmp;
			return tmp;
		}
		return Integer.parseInt(sb.toString());
	}

	public Integer nieksztaltek(Integer liczba) {
		List<Integer> positions = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder(liczba.toString());
		for(int x = 0; x < sb.length(); x++)
			positions.add(x);
		int randomPos;
		do {
			randomPos = positions.remove(getRandom(0, positions.size() - 1));
			if(sb.substring(randomPos, randomPos + 1).equals("3")) {
				sb.setCharAt(randomPos, '8');
				break;
			}
			if(sb.substring(randomPos, randomPos + 1).equals("7")) {
				sb.setCharAt(randomPos, '1');
				break;
			}
			if(sb.substring(randomPos, randomPos + 1).equals("6")) {
				sb.setCharAt(randomPos, '9');
				break;
			}
		} while(positions.size() > 0);
		return Integer.parseInt(sb.toString());
	}

	private int getRandom(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}

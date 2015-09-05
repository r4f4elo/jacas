package com.prafaelo.jacas.environment;

import java.util.Random;

public enum DirectionEnum {
	NORTH, SOUTH, EAST, WEST;
		
	public static DirectionEnum getDirectionRadom() {
		
		Random random = new Random();		
		int randomNumber = random.nextInt(DirectionEnum.values().length);
		
		for (DirectionEnum direction : DirectionEnum.values()) {
			if(direction.ordinal() == randomNumber) {
				return direction;
			}
		}
		throw new IllegalArgumentException("Random number is invalid: " + randomNumber);
	}
	
}

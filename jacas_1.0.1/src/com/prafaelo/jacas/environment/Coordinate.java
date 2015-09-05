package com.prafaelo.jacas.environment;

import java.util.Random;

public class Coordinate {

	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	
	public static Coordinate getCoordinateRandom(int lineQty, int columnQty){
		Random random = new Random();		
		int lineRandomNumber = random.nextInt(lineQty);
		int columnRandomNumber = random.nextInt(columnQty);
		
		return new Coordinate(lineRandomNumber, columnRandomNumber);
	}
	
	
	/**
	 * Retorna a linha<br>
	 * 
	 * @return linha
	 */
	public int getX() {
		return x;
	}

	/**
	 * Seta a linha<br>
	 * 
	 * @param linha
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Retorna a coluna<br>
	 * 
	 * @return linha
	 */
	public int getY() {
		return y;
	}


	/**
	 * Seta a coluna<br>
	 * 
	 * @param coluna
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "["+String.format("%2s %2s",x,y) +"]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}

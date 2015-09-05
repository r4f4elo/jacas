package com.prafaelo.jacas.environment;

import java.util.HashMap;
import java.util.Map;

public class Cell {

	private final Matrix matrix;
	private final Coordinate coordinate;
	private boolean isBorder;
	private Map <String, Object> objects = new HashMap<String, Object>();
	private String toString = String.format("%4s","");
	
	public Cell(final Coordinate coordinate, final Matrix matrix) {
		
		if(matrix == null){
			throw new NullPointerException();
		}
		
		if(coordinate == null){
			throw new NullPointerException();
		}
		
		this.matrix = matrix;
		this.coordinate = coordinate;
		setBorder();
	}
	
	public boolean isBorder() {
		return isBorder;
	}

	private void setBorder() {
		
		if(coordinate.getX() == matrix.getFirstLine() || coordinate.getX() == matrix.getLastLine()){
			this.isBorder = true;
		} else if(coordinate.getY() == matrix.getFirstColumn() || coordinate.getY() == matrix.getLastColumn()) {
			this.isBorder = true;			
		}
	}

	public Item getObjectsItem(){
		return (Item) getObjects().get("garbage");
	}
	
	public Map <String, Object> getObjects(){
		return objects;
	}

	public Object getObjects(Object object) {
		if(object instanceof Ant) {
			return objects.get("ant");			
		}
		if (object instanceof Garbage) {
			return objects.get("garbage");			
		}
		return null;
	}

	public Object getObjects(String key) {
		return objects.get(key.toLowerCase());			
	}	
	
	public synchronized void removeObjects(Object object) {
		if(object instanceof Ant) {
			objects.remove("ant");			
		} else if (object instanceof Garbage) {
			objects.remove("garbage");			
		}
	}
	public synchronized void setObjects(Object object) {
		if(object instanceof Ant) {
			objects.put("ant",object);			
		} else if (object instanceof Garbage) {
			objects.put("garbage",object);			
		}
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public void setToString(String toString) {
		this.toString = toString;
	}

	@Override
	public String toString() {
		String value = this.toString;		
//		String value = "[" + coordinate.getX() + coordinate.getY() + "]";
		
//		if(isBorder) {
//			value = String.format("%4s","");
//		}
			
		if (!objects.isEmpty()) {
			
			Ant ant = (Ant) objects.get("ant");
			if(ant != null) {
				if(ant.getMandibles() != null){
					return String.format("%3s","#");					
				}				
				return String.format("%2s","@");				
			}
			
			Object item = objects.get("garbage");
			if(item != null){
				return item.toString();				
			}
		}			
		return value;
	}
}

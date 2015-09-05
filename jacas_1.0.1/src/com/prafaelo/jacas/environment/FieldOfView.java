package com.prafaelo.jacas.environment;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class FieldOfView {

	private int scale=1;
	private int radius=3;
	private Map<Coordinate,Cell> cells = new LinkedHashMap<Coordinate,Cell>();
	
	public FieldOfView(int scale, Cell cell) {
		setScale(scale);
		generateFieldOfView(cell);
	}

	public FieldOfView(Cell cell) {
		setScale(1);
		generateFieldOfView(cell);
	}
	
	public void generateFieldOfView(Cell cell){
		
		getCells().clear();
		Coordinate coordinate = cell.getCoordinate();
		
		for(int x=coordinate.getX()-getScale();x<radius+coordinate.getX()-getScale();x++){
			for(int y=coordinate.getY()-getScale();y<radius+coordinate.getY()-getScale();y++){
				Coordinate coordinateTmp = new Coordinate(x, y);
				if(!coordinateTmp.equals(coordinate)){
					setCells(coordinateTmp, cell.getMatrix().getCells().get(new Coordinate(x, y)));
				}
			}
		}
		
		//System.out.println(toString());
	}
	
	public int getCountItems(Item item){
		return item.countItens(getCells().values());
	}
	
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {		
		this.scale = scale;
		setRadius();
	}
	
	private void setRadius(){
		int radius=3;
		for(int i=1; i<scale; i++){
			radius+= +2;
		}
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public Map<Coordinate, Cell> getCells() {
		return cells;
	}

	private void setCells(Coordinate coordinate, Cell cell) {
		if(cell != null){
			this.cells.put(coordinate, cell);			
		}
	}

	@Override
	public String toString() {
		
		String value ="";
		int cont =1;
		for(Cell cell : getCells().values()){
			if(cell==null){
				value+="null";
			} else {
				value+=cell.toString();				
			}
			if(cont==radius){
				cont=0;
				value+="\n";
			}
			cont++;
		}
		return value;
	}
}

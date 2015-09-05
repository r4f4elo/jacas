package com.prafaelo.jacas.environment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

public class Matrix implements Runnable{

	private final int lineQty;
	private final int columnQty;
	private final int firstLine;
	private final int lastLine;
	private final int firstColumn;
	private final int  lastColumn;
	
	private final Map<Coordinate, Cell> cells = new LinkedHashMap<Coordinate, Cell>();
	private final Map<Item, Collection<Item>> itemsCluster = new LinkedHashMap<Item,Collection<Item>>();

	
	public Matrix(int lineQty, int columnQty) {
		this.lineQty = lineQty;
		this.columnQty = columnQty;
		
		this.firstLine = 0;
		this.lastLine = this.lineQty -1; 
		
		this.firstColumn = 0;
		this.lastColumn = this.columnQty -1;
		
		createCells();
	}
	
	private void createCells() {
		for(int x = 0; x<lineQty; x++){
			for(int y =0; y<columnQty; y++){				
				Coordinate coordinate = new Coordinate(x, y);
				this.cells.put(coordinate, new Cell(coordinate, this));	
			}
		}
	}
	
	public Cell getCellRandom(){
		return cells.get(Coordinate.getCoordinateRandom(getLineQty(), getColumnQty()));
	}
	
	public void printMatrix(){
		System.out.println(toString());
	}
		
	public int getLineQty() {
		return lineQty;
	}

	public int getColumnQty() {
		return columnQty;
	}

	public int getFirstLine() {
		return firstLine;
	}

	public int getLastLine() {
		return  lastLine;
	}

	public int getFirstColumn() {
		return firstColumn;
	}

	public int getLastColumn() {
		return lastColumn;
	}

	public Map<Coordinate, Cell> getCells() {
		return cells;
	}
	
	public void generateItemsCluster(){
		if(getItemsCluster().size() > 0) {
			throw new RuntimeException("Erro!");
		}
		
		for(Cell cell : getCells().values()){
			
			Item item = cell.getObjectsItem();
			if(item != null){
				FieldOfView fieldOfView = new FieldOfView(cell);
				Item keyItensCluster=null;
				Item keyItensClusterFOV=null;
				for(Cell cellOfFOV : fieldOfView.getCells().values()){
					Item itemFOV = cellOfFOV.getObjectsItem();
					if(itemFOV != null){
						keyItensCluster = item.getkeyItensCluster();
						keyItensClusterFOV = itemFOV.getkeyItensCluster();
						
						if(keyItensClusterFOV != null){
							
							if(keyItensCluster!= null){
								if(keyItensCluster!=keyItensClusterFOV){
									
									Collection<Item> itemsClusterFOVColl = itemsCluster.get(keyItensClusterFOV);									
									for(Item itemsClusterFOV : itemsClusterFOVColl){
										itemsClusterFOV.setKeyItensCluster(keyItensCluster);
									}
									itemsCluster.get(keyItensCluster).addAll(itemsClusterFOVColl);
									itemsCluster.remove(keyItensClusterFOV);
								}															
							} else {
								item.setKeyItensCluster(keyItensClusterFOV);
								Collection<Item> coll = itemsCluster.get(keyItensClusterFOV);
								coll.add(item);									
							}
						}
					}
				}
				if(keyItensCluster==null && keyItensClusterFOV==null ){
					item.setKeyItensCluster(item);
					Set<Item> ItemsClusterCollection = new HashSet<Item>();
					ItemsClusterCollection.add(item);
					itemsCluster.put(item, ItemsClusterCollection);
				}
			}
		}
	}
	
	public Map<Item, Collection<Item>> getItemsCluster() {
		return itemsCluster;
	}

	@Override
	public String toString() {
		
		String matrix ="";
		for(Coordinate coordinate : cells.keySet()) {
			Cell cell = cells.get(coordinate);
			matrix = matrix + cell;				
			if(coordinate.getY() == lastColumn) {
				matrix = matrix + "\n";				
			}
		}
		return matrix;
	}

	@Override
	public void run() {
						
			TextArea textArea = new TextArea(toString());
			textArea.setBackground(Color.BLACK);
			textArea.setForeground(Color.WHITE);
			
			JFrame janela = new JFrame("JACAS");
			janela.add(textArea);
			janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			janela.setSize(new Dimension(new Double(362*2.3).intValue(), new Double(373*2.3).intValue()));
			janela.setVisible(true);
	 
			
		    while(true)
		    {   
		    	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	textArea.setText(toString());
		    }

	}
	
	
}

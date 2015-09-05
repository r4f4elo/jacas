package com.prafaelo.jacas.environment;

import java.util.Collection;

public class Garbage implements Item {

	private Cell currentCell;
	
	private int score=0;
	
	private String toString=String.format("%4s",".");
	
	private Item keyItemsCluster;
	
	public Garbage(Cell currentCell) {
		
		while (currentCell.getObjects("garbage") != null){
			currentCell = currentCell.getMatrix().getCellRandom();
		}
		setCurrentCell(currentCell);
	}

	public int getScore() {
		return score;
	}

	@Override
	public void setScore() {
		
		FieldOfView fov = new FieldOfView(getCurrentCell());
		this.score = fov.getCountItems(this);
	}	
	
	@Override
	public void setScore(Cell cell) {
		
		FieldOfView fov = new FieldOfView(cell);
		this.score = fov.getCountItems(this);
	}

	@Override
	public Cell getCurrentCell() {
		return currentCell;
	}

	@Override
	public void setCurrentCell(Cell currentCell) {
		currentCell.setObjects(this);
		this.currentCell = currentCell;
	}
	
	/**
	 * Conta quantos Garbage existem nas células passadas como parâmetro.
	 * 
	 * @param Coleção de células
	 * @return numero de garbage nas células
	 */
	@Override
	public int countItens(Collection<Cell> items) {

		int countItems=0;
		for (Cell cell : items){
			
			Item item = (Item) cell.getObjects(this);
			if(item != null){
				countItems++;
			}
		}
		
		return countItems;
	}
	
	public void setToString(String toString) {
		this.toString = toString;
	}

	@Override
	public String toString() {
		return toString;
	}

	/**
	 * Retorna a chave do cluster de items. Esta chave aponta para o item que se localiza na coordenada
	 * com o menor valor de X e menor valor de Y
	 * 
	 * @return key of matrix.Map<Item, Collection<Item>> itemsCluster
	 */
	@Override
	public Item getkeyItensCluster() {
		return keyItemsCluster;
	}
	
	/**
	 * Define a chave do cluster de items.
	 * 
	 * @param keyItensCluster
	 */
	@Override
	public void setKeyItensCluster(Item keyItensCluster){
		this.keyItemsCluster = keyItensCluster;
	}
}

package com.prafaelo.jacas.environment;

import java.util.Collection;

public interface Item {

	/**
	 * Conta quantos Item existem nas células passadas como parâmetro
	 * 
	 * @param Coleção de células
	 * @return numero de itens nas células
	 */
	int countItens(Collection<Cell> items);
	
	/**
	 * Retorna a chave do cluster de items.
	 * 
	 * @return chave do cluster de items
	 */
	Item getkeyItensCluster();
	
	/**
	 * Define a chave do cluster de items.
	 * 
	 * @param keyItensCluster
	 */
	void setKeyItensCluster(Item keyItensCluster);
	
	Cell getCurrentCell();

	void setCurrentCell(Cell currentCell);
	
	int getScore();
	
	void setScore();
	
	void setScore(Cell cell);
	
}

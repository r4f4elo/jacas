package com.prafaelo.jacas.environment.analysis;

import java.util.Collection;

import com.prafaelo.jacas.environment.Cell;
import com.prafaelo.jacas.environment.Garbage;
import com.prafaelo.jacas.environment.Item;
import com.prafaelo.jacas.environment.Matrix;

public class Analysis{

	private Matrix matrix;
	private Matrix garbageMatrix;
	private final long initialTime;
	private int scoreBiggestCluster=0;
	private int sizeItemsGarbageMatrix=0;
	private int stopScore=0;

	
	public Analysis(Matrix matrix) {	
		setMatrix(matrix);
		this.initialTime= System.currentTimeMillis();
	}
	
	public int getStopScore() {
		return stopScore;
	}

	public void setStopScore(int stopScore) {
		this.stopScore = stopScore;
	}

	public int getSizeItemsGarbageMatrix() {
		return sizeItemsGarbageMatrix;
	}

	public long getScoreBiggestCluster() {
		return scoreBiggestCluster;
	}

	private void setScoreBiggestCluster(int scoreBiggestCluster) {
		this.scoreBiggestCluster = scoreBiggestCluster;
	}

	private void setMatrix(Matrix matrix) {
		this.matrix = matrix;
		generateGarbageMatrix();
	}

	public Matrix getGarbageMatrix() {
		return garbageMatrix;
	}

	public void generateGarbageMatrix() {
		this.garbageMatrix = new Matrix(getMatrix().getLineQty(), getMatrix().getColumnQty());
		
		int sizeItemsGarbageMatrix = 0;
		
		for (Cell cell : getMatrix().getCells().values()){

			Cell cellGarbageMatrix = this.garbageMatrix.getCells().get(cell.getCoordinate());
			cellGarbageMatrix.setToString(cell.getCoordinate()+"x");

			if(cell.getObjects(Garbage.class.getSimpleName()) != null){
				Garbage garbage = new Garbage(cellGarbageMatrix);
				sizeItemsGarbageMatrix++;
				cellGarbageMatrix.setObjects(garbage);
				garbage.setScore(cell);
				garbage.setToString(cell.getCoordinate().toString()+garbage.getScore());
			}
		}
		
		/**
		 * TODO
		 * Gambiarra!!!!
		 */
		if(this.sizeItemsGarbageMatrix==0){
			this.sizeItemsGarbageMatrix = sizeItemsGarbageMatrix;
		};
		
		getGarbageMatrix().generateItemsCluster();
	}
	
	public void printClusters(){
		
		int sizeItemsClusters=0;
		int scoreBiggestCluster=0;
		int scoreCluster=0;
		Item cluster=null;
		int sizeBiggestClusterCollection=0;
		for(Item keyItemsCluster : getGarbageMatrix().getItemsCluster().keySet()){
			
			Collection<Item> itemsCluster = getGarbageMatrix().getItemsCluster().get(keyItemsCluster);
			scoreCluster=0;
			for(Item item : itemsCluster){
				scoreCluster+=item.getScore();
			}
			if(scoreBiggestCluster<scoreCluster){
				scoreBiggestCluster=scoreCluster;
				cluster=keyItemsCluster;
				sizeBiggestClusterCollection=itemsCluster.size();
			}
			sizeItemsClusters+=itemsCluster.size();
			//System.out.println("Cluster[Key={"+keyItemsCluster.getCurrentCell().getCoordinate()+"}, Size={"+itemsCluster.size()+"}, Score={"+scoreCluster+"}, Items={"+itemsCluster+"}]");
		}
		
		setScoreBiggestCluster(scoreBiggestCluster);

		System.out.println("TotalOfClusters="+getGarbageMatrix().getItemsCluster().size()+
				      ", TotalItemsClusters="+sizeItemsClusters+
				     ", BiggestCluster{key=["+cluster.getCurrentCell().getCoordinate()+
				                 "], Score=["+scoreBiggestCluster+
				                  "], Size=["+sizeBiggestClusterCollection+
				   "]}, EndTime="+(System.currentTimeMillis()-this.initialTime)/1000+"seg"+
				     ", StopScore="+getStopScore());
		
	}

	public Matrix getMatrix() {
		return matrix;
	}
}

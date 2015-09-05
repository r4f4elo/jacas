package com.prafaelo.jacas.environment.analysis;

import com.prafaelo.jacas.environment.Ant;
import com.prafaelo.jacas.environment.Matrix;

public class AnalysisReport implements Runnable{

	private final Analysis analysis;
	
	private float stopScore=0;
	
	public AnalysisReport(Matrix matrix, int stopScore) {
		this.analysis = new Analysis(matrix);
		setStopScore(stopScore);;
	}
	
	public float getStopScore() {
		return stopScore;
	}

	public void setStopScore(int stopScore) {
		if(stopScore>100){
			stopScore=100;
		}
		
		float x = (float) stopScore/100;
		
		this.stopScore = x;
	}

	public boolean isReportEnded(){
		int n = this.analysis.getSizeItemsGarbageMatrix();
		int x = (int) Math.sqrt(n) -1;
		int y = (int) (x*x*8*getStopScore());
		this.analysis.setStopScore(y);
		
		return y>this.analysis.getScoreBiggestCluster();
	}

	@Override
	public void run() {

		System.out.println("####################### BEGIN ANALYSIS");		
		while(true){
			try {
				

				this.analysis.generateGarbageMatrix();

				this.analysis.printClusters();
				
				//System.out.println(this.analysis.getGarbageMatrix());
				
				//System.out.println(this.analysis.getGarbageMatrix());
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(!isReportEnded()){
				System.out.println(this.analysis.getMatrix());
				Ant.setStop(true);
				
			}
		}		
		//System.out.println("####################### END ANALYSIS");
		
		
		//System.exit(0);
		
	}
}

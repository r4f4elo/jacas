package com.prafaelo.jacas;

import java.io.IOException;

import com.prafaelo.jacas.environment.Ant;
import com.prafaelo.jacas.environment.Garbage;
import com.prafaelo.jacas.environment.Matrix;
import com.prafaelo.jacas.environment.analysis.AnalysisReport;

class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Matrix matrix = new Matrix(50, 50);
		Thread t1 = new Thread(matrix);
		t1.start();
			
		//729
		for(int i=0; i<729; i++){			
			Garbage garbage = new Garbage(matrix.getCellRandom());
		}
		
		AnalysisReport analysisReport = new AnalysisReport(matrix,50);
		Thread analysisThread = new Thread(analysisReport,"analisys");
		analysisThread.start();
		
		Thread.sleep(1000);

		for (int i=0; i<500; i++) {
			Ant ant = new Ant(matrix.getCellRandom(),3);
			Thread t = new Thread(ant);
			t.start();
		}
		
	}
}

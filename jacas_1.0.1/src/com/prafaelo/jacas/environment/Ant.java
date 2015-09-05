package com.prafaelo.jacas.environment;

import java.util.Random;

public class Ant implements Runnable {

	private Cell currentCell;
	private Object mandibles;
	private DirectionEnum direction;
	private FieldOfView fieldOfView;
	private static boolean stop;
	
	public Ant(Cell currentCell) throws InterruptedException {
		setCurrentCell(currentCell);
		setDirection(DirectionEnum.getDirectionRadom());
		setFieldOfView(new FieldOfView(getCurrentCell()));
	}

	public Ant(Cell currentCell, int scaleFieldOfView) throws InterruptedException {
		setCurrentCell(currentCell);
		setDirection(DirectionEnum.getDirectionRadom());
		setFieldOfView(new FieldOfView(scaleFieldOfView, getCurrentCell()));
	}
	
	public Ant(Cell currentCell, DirectionEnum direction, int scaleFieldOfView) throws InterruptedException {
		setCurrentCell(currentCell);
		setDirection(direction);
		setFieldOfView(new FieldOfView(scaleFieldOfView, getCurrentCell()));
	}

	public synchronized void walk() throws InterruptedException {		
		switch (direction) {
		case NORTH:
			setCurrentCell(getCurrentCell().getMatrix().getCells().get(new Coordinate(getCurrentCell().getCoordinate().getX() -1, getCurrentCell().getCoordinate().getY())));						
			do {
				this.setDirection(DirectionEnum.getDirectionRadom());
			} while (getDirection().equals(DirectionEnum.SOUTH));	
			break;
		case SOUTH:
			setCurrentCell(getCurrentCell().getMatrix().getCells().get(new Coordinate(getCurrentCell().getCoordinate().getX() +1, getCurrentCell().getCoordinate().getY())));				
			do {
				this.setDirection(DirectionEnum.getDirectionRadom());
			} while (getDirection().equals(DirectionEnum.NORTH));
			break;
		case EAST:
			setCurrentCell(getCurrentCell().getMatrix().getCells().get(new Coordinate(getCurrentCell().getCoordinate().getX(), getCurrentCell().getCoordinate().getY() -1)));								
			do {
				this.setDirection(DirectionEnum.getDirectionRadom());
			} while (getDirection().equals(DirectionEnum.WEST));
			break;
		case WEST:
			setCurrentCell(getCurrentCell().getMatrix().getCells().get(new Coordinate(getCurrentCell().getCoordinate().getX(), getCurrentCell().getCoordinate().getY() +1)));								
			do {
				this.setDirection(DirectionEnum.getDirectionRadom());
			} while (getDirection().equals(DirectionEnum.EAST));				
			break;			
		default:
			break;
		}
		
		if(getCurrentCell().isBorder()){
			this.setDirection(DirectionEnum.getDirectionRadom());
		}
		
		getFieldOfView().generateFieldOfView(getCurrentCell());
	}
	
	public void hold(){
				
		if(getMandibles() == null){
			Ant ant = (Ant) getCurrentCell().getObjects(this);
			if(ant == null || ant == this){
				Garbage garbage = (Garbage) getCurrentCell().getObjects(Garbage.class.getSimpleName());
				if(garbage !=null){
					if(analyseHold()){
						setMandibles(garbage);
						getCurrentCell().removeObjects(garbage);						
					}
				}
			}
		}
	}
	
	public void drop(){
		if(getMandibles() != null){
			Ant ant = (Ant) getCurrentCell().getObjects(this);
			if(ant == null || ant == this){
				if(getCurrentCell().getObjects(Garbage.class.getSimpleName()) == null) {
					if(analyseDrop()){
						getCurrentCell().setObjects(getMandibles());
						setMandibles(null);
					}
				}				
			}
		}
	}
	
	public boolean analyseDrop(){
						
			Random random = new Random();		
			int randomNumber = random.nextInt(100);
			
			float cellsQty = getFieldOfView().getCells().size();
		
			float value = ((cellsQty-getGarbageQtyOfFOF())/cellsQty)*100;
			
			if(value < randomNumber){
				return true;
			}
		
		return false;
	}

	public boolean analyseHold(){
		return !analyseDrop();
	}
	
	public int getGarbageQtyOfFOF() {
		int garbageQty=0;
		for(Cell cell : getFieldOfView().getCells().values()){
			if(cell.getObjects(Garbage.class.getSimpleName()) != null){
				garbageQty++;
			}
		}
		return garbageQty;
	}

	
	public Cell getCurrentCell() {
		return currentCell;
	}


	public void setCurrentCell(Cell currentCell) {
		if(currentCell != null){
			currentCell.setObjects(this);
			
			if(this.getCurrentCell() == null) {
				this.currentCell = currentCell;			
			} else {
				if(this.currentCell != currentCell){
					this.currentCell.removeObjects(this);
					this.currentCell = currentCell;
				}
			}
		}
	}

	public Object getMandibles() {
		return mandibles;
	}

	public void setMandibles(Object mandibles) {
		this.mandibles = mandibles;
	}

	public DirectionEnum getDirection() {
		return direction;
	}

	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}

	public FieldOfView getFieldOfView() {
		return fieldOfView;
	}

	public void setFieldOfView(FieldOfView fieldOfView) {
		this.fieldOfView = fieldOfView;
	}
	
	public static boolean isStop() {
		return stop;
	}

	public static void setStop(boolean stop) {
		Ant.stop = stop;
	}

	@Override
	public void run() {
		try {
			
			while(!isStop()){
				hold();
				drop();
				walk();
				Thread.sleep(5);
			}
			
			while(getMandibles()!=null){
				drop();
				walk();
				Thread.sleep(5);				
			}
			
			getCurrentCell().removeObjects(this);
			
			return;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}

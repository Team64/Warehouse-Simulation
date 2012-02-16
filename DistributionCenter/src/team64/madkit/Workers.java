/*
 * Worker.java - A worker of any type in the warehouse. Workers have a role of either a sorter, picker, or packer.
 * */
 
package team64.madkit;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Vector;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Message;
import madkit.message.ObjectMessage;

/**
 * @author Team 64
 * 
 */
public class Workers extends AbstractAgent
{

	/** if this worker is doing its job then working will be true */
	boolean working = true;
	
	Vector <Task> tasks = new Vector<Task>();
	static float minimumWage=6.50f; //so we can calculate the cost of using the workers
	protected float hourlyCost=minimumWage;	
	//starting location in the warehouse (the entry door?)
	protected static int startx = 10;
	protected static int starty = 10;
	WorkerInformation workerInfo= new WorkerInformation();
	
	public int estimateDistance(){
		int taskTimes = 0;
		for (int i = 0; i < tasks.size(); i++){
			Task current = tasks.get(0);
			if (current.toPickup){
				Warehouse.findDistance(workerInfo.location.x,workerInfo.location.y,current.pickup.x,current.pickup.y);
			} else {
				Warehouse.findDistance(workerInfo.location.x,workerInfo.location.y,current.dropoff.x,current.dropoff.y);
			}
		}
		return taskTimes;
	}
	
	
	
	private void taskFinished(){
		tasks.remove(0);
	}
	
	private void pickUpDone(){
		tasks.get(0).toPickup = false;
	}

	public void activate(){	
	requestRole("warehouse","workers", "idle",null);
	}

	public void move()
	{
		if(logger != null) {
			logger.info("Scheduler has said that this worker should move");   
		}
		//move if working
		if (working) {
			moveRandomly();			
		} if (!working) { //move it anyway
			moveRandomly();	
			//got to the lunch room?
		}
 
	}

/** used by workers that are milling around (if they do not have any work orders) */
	public void moveRandomly() {
		int randStep = Warehouse.getRandomStep();
		if(logger != null) {
			logger.finest("random step for x direction is " + randStep);   
		}
		int xadd = randStep;
		int yadd = Warehouse.getRandomStep();
		int newx = xadd + workerInfo.location.x;
		if (newx < 0) {
			newx=0;
		} 
		if (newx > Warehouse.warehouseWidth) {
			newx = Warehouse.warehouseWidth;
		}
		
		int newy = yadd + workerInfo.location.y;
		if (newy < 0) {
			newy=0;
		} 
		if (newy > Warehouse.warehouseHeight) {
			newy = Warehouse.warehouseHeight;
		}			
		workerInfo.setLocation(new Point(newx,newy));		
	}

	
	@SuppressWarnings("unchecked")
	protected Message getBestRoute(List<Message> routes) {
		ObjectMessage<Integer> best = (ObjectMessage<Integer>) routes.get(0);
		for(Message m : routes){
			ObjectMessage<Integer> distance = (ObjectMessage<Integer>) m;
			if(best.getContent() > distance.getContent()){
				best = distance;
			}
		}
		return best;
	}

	public void receiveMessage(Message m) {
		if (m != null) {
			if(logger != null) {
				logger.info("General Worker got a Message from " + m.getSender()+ " says: " +m.toString());   
			}
		}
	}
	


	
	/** Called by a property probe for a Worker. */
	class WorkerInformation {
		int orderNumber = 0; // 0 means it has not been assigned to an order	
		/** different workers have different colors */
		protected Color workerColor;	
		Point location = null; //where the worker is in the warehouse

		public WorkerInformation() {		    
			workerColor = Color.magenta;	
			location = new Point(Workers.startx,Workers.starty);
		}

		/**
		 * @return the worker's Color
		 */
		public Color getWorkerColor() {
			return workerColor;
		}


		/**
		 * @param workerColor the workerColor to set
		 */
		public void setWorkerColor(Color workerColor) {
			this.workerColor = workerColor;
		}

		/**Gets the location of the worker in the warehouse
		 * @return the location
		 */
		public Point getLocation() {
			return location;
		}

		/**Sets the location of the worker in the warehouse
		 * @param location the location of the worker 
		 */
		public void setLocation(Point location) {
			this.location = location;
		}
		
		/** gets the order number that the worker has been assigned. If 0 then no work order has been assigned */
		public int getOrderNumber() {
			return orderNumber;
		}
		
		/** sets the order number for the worker. If 0 then no work order has been assigned */
		public void setOrderNumber(int orderNumber) {
			this.orderNumber = orderNumber;
		}


	}
	
	
}










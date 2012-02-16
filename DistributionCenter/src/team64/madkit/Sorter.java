package team64.madkit;

import java.awt.Color;

import madkit.kernel.Message;
import madkit.message.ActMessage;
import madkit.message.ObjectMessage;

public class Sorter extends Workers {

	public Sorter() {
		workerInfo.setWorkerColor(Color.red);
		//no location, put this worker at the entrance gridUnit
	}

	public void activate()
	{
		createGroupIfAbsent("warehouse","workers",true,null);
		requestRole("warehouse","workers", "worker",null);
		requestRole("warehouse","workers", "sorter",null);

	}

	/** called by the worker scheduler */
	public void move() {

		if(logger != null) {
			logger.fine("Scheduler has said that this sorter worker should move");   
		}

		if (working) {
			moveRandomly();
		}
		else {
			//stay in place (or go to the lunch room?)
		}
		//after tote is emptied and placed back on conveyor...
		//working = false;
	}

	/** this will be a message to go put something on the storage shelves */	
	public void receiveMessage(Message m) {
		ActMessage actionMessage = (ActMessage)m;
		if (m != null) {
			if(logger != null) {
				logger.info("Sorter got a Message from " + actionMessage.getSender()+ " says: " + actionMessage.getAction());   
			}
			if (working && logger != null) {
				logger.info("I received a request to sort, but I am already working");
				sendReply(actionMessage,new ActMessage("already working"));
			} else {
				working=true;
				handleSortingRequest();		
			}
		}
	}


	/** wait for your tote, find a path to the shelf bin, go to the bin, empty the tote, put the tote back on the conveyor */
	protected void handleSortingRequest() {

		if (logger!=null){
			logger.info("I received a request to put an item on the storage shelf");
		}
		//path to the item is requested from the pathfinder agent
		//find a path to the item to pick
		sendMessage("warehouse","pathfinder","sorter", new ObjectMessage<String>("find-path,location.x,location.y"));
		working = true;

		if(logger != null) {
			logger.info("Going to get the item");
		}

	}
}


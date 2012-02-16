package team64.madkit;

import java.awt.Color;

import madkit.kernel.Message;
import madkit.message.ActMessage;
import madkit.message.ObjectMessage;

public class Picker extends Workers {
	

	public Picker() {
		workerInfo.setWorkerColor(Color.yellow);
	}
	
	public void activate()
	{
		createGroupIfAbsent("warehouse","workers",true,null);
		requestRole("warehouse","workers", "worker",null);
		requestRole("warehouse","workers", "picker",null);
		
	}

	/** called by the worker scheduler */
	public void move()
	{
		if(logger != null) {
			logger.fine("Scheduler has said that this picker worker should move");   
		}
		if (working) {
			moveRandomly();
		}
		else {
			//stay in place (or go to the lunch room?)
		}
		//after tote is filled with item and placed back on conveyor...
		//working = false;

	}


/** wait for your tote, find a path to the item, go get it, fill the tote, put the tote back on the conveyor */
	protected void handlePickingRequest() {
        
	   if (logger!=null){
			logger.info("I received a request to pick an item for an order");
		}
		
		//find a path to the item to pick from where I am now
		sendMessage("warehouse","pathfinder","picker", new ObjectMessage<String>("find-path,location.x,location.y"));
	    working = true;
				

		
			if(logger != null) {
				logger.info("Going to get the item");
			}
		
		
	}
	
	/** this will be a message to go pick one or more items from the storage shelves */	
	public void receiveMessage(Message m) {
    ActMessage actionMessage = (ActMessage)m;
	if (m != null) {
		if(logger != null) {
			logger.info("Picker got a Message from " + actionMessage.getSender()+ " says: " + actionMessage.getAction());   
		}
		if (working && logger != null) {
			logger.info("I received a request to pick, but I am already working");
			sendReply(actionMessage,new ActMessage("already working"));
		} else {
			working=true;
			handlePickingRequest();		
		}
	}
	
}
	
}

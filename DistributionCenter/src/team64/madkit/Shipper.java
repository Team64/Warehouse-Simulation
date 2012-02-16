package team64.madkit;

import madkit.kernel.Message;
import madkit.message.ActMessage;

public class Shipper extends Workers {
	
	public void activate()
	{
		createGroupIfAbsent("warehouse","workers",true,null);
		requestRole("warehouse","workers", "worker",null);
		requestRole("warehouse","workers", "shipper",null);		
	}
	
	public void move()
	{
		if(logger != null) {
			logger.fine("Scheduler has said that this shipper should move");   
		}
	}
	
	
	public void receiveMessage(Message m) {
	    ActMessage actionMessage = (ActMessage)m;
		if (m != null) {
			if(logger != null) {
				logger.info("Shipper got a Message from " + actionMessage.getSender()+ " says: " + actionMessage.getAction());   
			}
		}
	}
}
	
	
	/*private void handleShippingRequest(ObjectMessage<String> request) {
		if(! request.getSender().exists())
			return;
		if (hasGUI()) {
			blinkPanel.setBackground(Color.YELLOW);
		}
		if(logger != null)
			logger.info("I received a request to sort from "+request.getContent()+" \nfrom "+request.getSender());
		List<Message> routes = broadcastMessageWithRoleAndWaitForReplies(
				"warehouse",
				"pathfinder",  
				"pathforsorter",
				new ObjectMessage<String>("find-path,0,0,request.getContent()"),
				"sorter",
				900);
		if(routes == null){
			if(logger != null)
				logger.info("Cannot put the item away !!");
			if (hasGUI()) {
				blinkPanel.setBackground(Color.LIGHT_GRAY);
			}
			return;
		}
		Message m = getBestRoute(routes);
		if (m != null) {
			if(logger != null) {
				logger.info("Found the best way to sort the item "+m.getSender());
			}
			
		}
		if (hasGUI()) {
			blinkPanel.setBackground(Color.LIGHT_GRAY);
		}
	}*/
	


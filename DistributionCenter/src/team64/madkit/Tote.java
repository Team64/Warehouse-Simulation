package team64.madkit;

import java.awt.Color;
import java.awt.Point;
import java.util.Hashtable;
import java.util.logging.Level;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Message;
import madkit.message.ActMessage;

/** Totes go around on conveyers until filled with an order. They are used by workers to add or remove items. 
 * Totes can be moved by the conveyors or by workers.
 */

public class Tote extends AbstractAgent {
	
	
	static int capacity = 100; //the capacity of the tote is a fixed value (100)

	ToteInformation toteInfo = new ToteInformation();
	
	public void activate()
	{
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		createGroupIfAbsent("warehouse","container",true,null);
		//createGroupIfAbsent("warehouse","warehouse",true,null);	
		requestRole("warehouse","container", "tote",null);	
	
	}

	/** Move the tote. Called by the scheduler for either the worker or the conveyor. */
	public void move()
	{
		if(logger != null) {
			logger.info("Scheduler has said that this tote should move on the conveyer");   
		}

	}

	
	public void receiveMessage(Message m) {
		ActMessage actionMessage = (ActMessage)m;
		if (m != null) {
			if(logger != null) {
				logger.info("Tote got a Message from " + actionMessage.getSender()+ " says: " + actionMessage.getAction());   
			}
		}

	}
	
	/** Called by a property probe for a Tote. */
	class ToteInformation {
		int orderNumber = 0; // 0 means it has not been assigned to an order		
		/** state color is used to show if a tote has anything in it or if it is empty */
		protected Color stateColor;
		Hashtable<Integer, Integer> contents = null; //hashtable contains the order line item (key) and quantity of these in the tote
		boolean filled  = false; //might be true even if all the items are not in the tote
		Point location = new Point(0,0); //where the tote is in the warehouse

		public ToteInformation() {		    
			stateColor = Color.green;				
		}

		/** gets the color to draw the tote in the graphics.
		 * @return the stateColor
		 */
		public Color getStateColor() {
			return stateColor;
		}


		/** sets the state color for the tote
		 * @param stateColor the stateColor to set
		 */
		public void setStateColor(Color stateColor) {
			this.stateColor = stateColor;
		}

		/**Gets the location of the tote in the warehouse
		 * @return the location
		 */
		public Point gettLocation() {
			return location;
		}

		/**Sets the location of the tote in the warehouse
		 * @param location the location of the tote 
		 */
		public void setLocation(Point location) {
			this.location = location;
		}
		
		/** gets the order number for the tote. Does not have to be unique if more thatn one tote is needed for an order
		 
		 */
		public int getOrderNumber() {
			return orderNumber;
		}
		
		/** sets the order number for the tote.  
		 * a zero order number means the tote is available for any other job.
		 */
		public void setOrderNumber(int orderNumber) {
			this.orderNumber = orderNumber;
		}

	}
	
}

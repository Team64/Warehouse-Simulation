/*
 * The Conveyor System is a linked list of conveyor segments. It runs within the confines of the warehouse layout.
 */
package team64.madkit;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.JFrame;

import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Message;


/**
 * @author team64
 * @2012
 * 
 */
//@SuppressWarnings("serial")
public class ConveyorSystem extends Agent
{

	static boolean conveyorSystemRunning=true;
	protected ConveyorScheduler scheduler;
	protected static int numberOfSegments = 100; //initial number of segments in the conveyor
	protected ConveyorView panel;
	protected ConveyorDisplay conveyorDisplay;
	protected ConveyorInformation conveyorInfo;
	//protected List<AgentAddress> conveyorSegmentAgents  = null;


	protected void activate(){
		
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.INFO);

		createGroupIfAbsent("warehouse","conveyorsystem",true,null);
		requestRole("warehouse","conveyorsystem","launcher"); //need this for shutdown purposes

		if(logger != null) {
			logger.info("Launching Conveyor System");
		}
		List<AgentAddress> segmentList = launchConveyorSegments();
		conveyorInfo = new ConveyorInformation(segmentList);
		
		conveyorDisplay = new ConveyorDisplay();
		launchAgent(conveyorDisplay,true);

		scheduler = new ConveyorScheduler();
		if(logger != null) {
			logger.info("Launching conveyor segment scheduler");
		}
		launchAgent(scheduler,false);



	}

	//@SuppressWarnings("unchecked")

	protected void live()
	{

		while (conveyorSystemRunning) {
			Message m = waitNextMessage(500);
		}

	}

	/** sets up all the conveyor segments for this conveyor system 
	 * and returns the List of segment agent addresses 
	*/
	private List<AgentAddress> launchConveyorSegments() {
		//normally READ conveyor setup information from a file or database

		if(logger != null) {
			logger.info("Launching conveyor segments");
		}
		//create, launch, and configure a bunch of conveyor segments
		List <AgentAddress> conveyorSegmentAgents  = null;
		for (int i=0;i<numberOfSegments;i++){	
			int location = i+1;//for now the conveyor segments are given simple locations (later real graphical locations)
			ConveyorSegment cs = new ConveyorSegment(location);				
			launchAgent(cs,10,false);
			if (hasGUI()) {
				panel.setText(i,"seg " + i);
			}
		}
		conveyorSegmentAgents = getAgentsWithRole("warehouse","conveyorsystem","segment"); 
		for (int i=0;i<numberOfSegments-1;i++){	
			//send the next segment's AgentAddress in the list to each of the segments one by one
			sendMessage(conveyorSegmentAgents.get(i), new ConveyorSegmentMessage(conveyorSegmentAgents.get(i+1)));				
		}	
		//send a message to the last conveyor segment that its next segment is the first one (circular)
		sendMessage(conveyorSegmentAgents.get(numberOfSegments-1), new ConveyorSegmentMessage(conveyorSegmentAgents.get(0)));
		if (hasGUI()) {
			panel.setBackground(Color.yellow);
		}
		return conveyorSegmentAgents;
	}
	
	

	@Override
	protected void end() {
		conveyorSystemRunning=false;
		conveyorInfo.segmentList = null;
		conveyorInfo=null;
		//sortersList=null;
		if(logger != null) {
			logger.info("Stopping Warehouse Simulation");
		}
	}



	@Override
	public void setupFrame(JFrame frame) {	   	
		panel = new ConveyorView();
		panel.setBounds(0, 0, 400,400);
		frame.add(panel);

		frame.setSize(400,430);
	}

	/**
	 * Can be used to test the conveyor system simulation
	 */

	public static void main(String[] args) {
		executeThisAgent(args,1,false);
	}

	public AgentAddress getRandomConveyorSegment() {
		Random randomGenerator = new Random();
		int rand = randomGenerator.nextInt(numberOfSegments);
		return conveyorInfo.segmentList.get(rand);
	}	
	
	/** Called by a property probe for a Conveyor System (a group of conveyor segments). */
	class ConveyorInformation {
		
		/** where the first segment of the conveyor is */
		Point location;
		/** a list of conveyor segments */
		List <AgentAddress> segmentList;
		

		public ConveyorInformation(List<AgentAddress> segmentList) {		    
			this.segmentList = segmentList;				
		}

		

		/**Sets the location of the first segment of the Conveyor System
		 * @param location the location of the tote 
		 */
		public void setLocation(Point location) {
			this.location = location;
		}
		
		/** gets the warehouse location of the first conveyor segment */
	    public Point getLocation(){
	    	return location;
	    }

	}

}


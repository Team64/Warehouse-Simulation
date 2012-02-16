/*
 * Warehouse contains the Main for running the simulation.
 * (c) 2012 Team 64 New Mexico Supercomputing Challenge 
 */
package team64.madkit;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Message;
import madkit.message.ActMessage;



public class Warehouse extends Agent
{

	static boolean simulationRunning=true;
	/**size of the warehouse in grid units (basically a grid unit holds one tote, one 
	conveyor segment, one worker, or a tote + conveyor segment)*/
	protected static int warehouseWidth = 25;
	protected static int warehouseHeight= 25;
	/** schedules worker actions for the simulation */
	protected WorkerScheduler workerScheduler;
	/** schedules the conveyor system actions for the simulation */
	protected ConveyorSystem conveyorSystem;
	/** schedules the painting of the main warehouse graphics */
	protected DisplayScheduler displayScheduler;
	/** sets up probes for the agents in the warehouse and sets up the main graphical display */
	protected WarehouseWatcher warehouseWatcher;
	/** rand is used to randomly move an agent 1 grid step when needed 0=no movement, 1=+1, 2=-1 */
	protected static Random rand = new Random(2);
	/* time in milliseconds for graphics updates */
	protected static int updateSpeed = 500; 
	protected static int lunchroomWidth = 4;
	protected static int lunchroomDepth = 4;
	protected static Point lunchroomLocation = new Point(warehouseWidth-lunchroomWidth,0);
	protected static Point entranceLocation = new Point(0,warehouseHeight/2);
	
    static Connection dbConnection; //connection to the database
    
    public static GridUnit[][] grid = null; //the environment grid
	
	//private ArrayList<AbstractAgent> pickersList = new ArrayList<AbstractAgent>(6);
	//private ArrayList<AbstractAgent> sortersList = new ArrayList<AbstractAgent>(6);
	

	protected void activate(){
	
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.INFO);
		createGroupIfAbsent("warehouse","conveyorsystem",true,null);//needs to be in this group to send messages to conveyors
		createGroupIfAbsent("warehouse","workers",true,null); //needs to be in this group to send messages to workers
		createGroupIfAbsent("warehouse","warehouse",true,null);	
		requestRole("warehouse","warehouse", "warehouse");
		//the master warehouse layout
		grid  = new GridUnit[warehouseWidth][warehouseHeight];
		for  (int column=0;column<warehouseWidth;column++) {
			for (int row=0;row<warehouseHeight;row++) {
			grid[column][row]=new GridUnit();
			}
		}
		if(logger != null) {
			logger.info("Launching workers");
		}
		launchSorters(3);
		launchPickers(3); 
		
		conveyorSystem= new ConveyorSystem();
		launchAgent(conveyorSystem,false);
		launchTotes(2);
		
		//test only		
	
	/*	AgentAddress p = getAgentWithRole("warehouse", "workers", "picker");	
		AgentAddress s = getAgentWithRole("warehouse", "workers", "sorter");		
		grid[0][0].addAgent(p);
		boolean result = grid[0][0].checkOccupantCompatibility(s);
		result = grid[0][1].checkOccupantCompatibility(s);
		
		AgentAddress t = getAgentWithRole("warehouse", "container", "tote");

		AgentAddress cs = getAgentWithRole("warehouse","conveyorsystem", "segment");
		
		grid[2][2].addAgent(cs); 
		result = grid[2][2].checkOccupantCompatibility(t);
		result = grid[2][2].checkOccupantCompatibility(s);*/
		
		
		
		
		workerScheduler= new WorkerScheduler();
		if(logger != null) {
			logger.info("Launching worker scheduler");
		}
		launchAgent(workerScheduler,false);

		/** set up a watcher for the warehouse with a graphical display*/
		warehouseWatcher = new WarehouseWatcher();
		launchAgent(warehouseWatcher,true);
		/** set up a scheduler for the watcher */
		displayScheduler = new DisplayScheduler();        
		launchAgent(displayScheduler,false);
	}

	//@SuppressWarnings("unchecked")

	protected void live()
	{
		
		while (simulationRunning) {
			Message m = waitNextMessage(500);
		}

	}
	
	/** returns 0, +1 or -1  for a random grid movement */
	public static int getRandomStep() {
		int step  = rand.nextInt(3);	
		return step-1;
	}

	private void launchSorters(int numberOfSorters) {

		if(logger != null) {
			logger.info("Launching "+numberOfSorters+" sorters");
		}

		for (int i=0;i<numberOfSorters;i++){
			launchAgent(new Sorter(),10,false);
		}
	}
	
	private AgentAddress getPickerAtLocation(int x, int y){
		this.sendMessage("warehouse", "workers", "pickers", new Message());
		return null;
		
	}
	
	static int findDistance(int x1, int y1, int x2, int y2){
		return (int) Math.hypot(x1-x2, y1-y2);
	}


	private void launchPickers(int numberOfPickers){
		if(logger != null)
			logger.info("Launching "+numberOfPickers+" pickers");


		for (int i=0;i<numberOfPickers;i++){
			launchAgent(new Picker(),10,false);
		}
	}
	
	/* launches totes an randomly sets them on the conveyor */
	private void launchTotes(int numberOfTotes) {

		if(logger != null) {
			logger.info("Launching "+numberOfTotes+" totes and placing them randomly on the conveyor");
		}

		for (int i=0;i<numberOfTotes;i++){			
			launchAgent(new Tote(),10,false);			
		}
		//get all the totes and put them on random conveyor segments
		List<AgentAddress> toteAgents  = null;
		toteAgents = getAgentsWithRole("warehouse","container", "tote");
		for (int i=0;i<numberOfTotes;i++){
			AgentAddress conveyorSegment = conveyorSystem.getRandomConveyorSegment();
			if(logger != null) {
				logger.fine("tote "+(i+1) + "is on segment " + conveyorSegment);
			}
			//message to the conveyor segment that it is getting a tote
			sendMessage(conveyorSegment,new ToteMessage(toteAgents.get(i)));
		}
		
	}


	protected void end() {
		simulationRunning=false;
		//pickersList=null;
		//sortersList=null;
		if(logger != null) {
			logger.info("Stopping Warehouse Simulation");
		}
	}



	public void setupFrame(JFrame frame) {
	   	
		WarehouseTestPanel panel = new WarehouseTestPanel(this);
		panel.setBounds(0, 0, 240,280);
		frame.add(panel);
		/*panel.setLayout(null);
		
		JButton btnNewOrder = new JButton("New Order Received");
		btnNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//generate a new order and message to the warehouse
				//Note: normally we would query the database to see if there is a new order
				sendMessage("warehouse","warehouse","warehouse",new ActMessage("a new order is in, wait for your tote and start picking!"));
			}
		});
		btnNewOrder.setBounds(22, 74, 200, 23);
		panel.add(btnNewOrder);
		
		JButton btnNewShipment = new JButton("New Shipment Received");
		btnNewShipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//message to the warehouse that there is a new shipment of items to put away
				sendMessage("warehouse","workers","sorter",new ActMessage("get to your sorting job!"));
				
			}
		});
		btnNewShipment.setBounds(22, 130, 200, 23);
		panel.add(btnNewShipment);*/
		frame.setSize(500,350);
	}

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		executeThisAgent(args,1,true);

	}

}
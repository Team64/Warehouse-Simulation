
package team64.madkit;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import madkit.kernel.AgentAddress;


final public class GridUnit 
{
	protected Color color;
	/** a collection of agents occupying the grid unit */
	protected Set<AgentAddress> occupants = null;


	public GridUnit() {
		color = Color.black;
		occupants = new HashSet<AgentAddress>();
	}
	
	/*public void activate()
	{
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		createGroupIfAbsent("warehouse","warehouse",true,null);	
		requestRole("warehouse","warehouse", "grid",null);	
	}
	
	public void receiveMessage(Message m) {
		ObjectMessage message = (ObjectMessage)m;
		if (m != null) {
			if(logger != null) {
				logger.info("Grid Unit got an object Message from " + message.getSender());   
			}
		}

	}*/

	/** returns the color of the grid unit for drawing the graphic */
	public Color getColor(){
		return color;

	}

	/** sets the color of the grid unit for drawing the graphic */
	public void setColor(Color color){
		this.color = color;

	}
	
	/** add an agent to the occupied set */
	protected void addAgent (AgentAddress agent) {
		occupants.add(agent);
	}
	
	/** remove an agent to the occupied set */
	protected void removeAgent (AgentAddress agent) {
		occupants.remove(agent);		
	}
	
	/** checks if there is an occupant if the candidate can
	 * also occupy the grid unit. Returns true if the spot is empty or
	 * compatible, false otherwise.
	 * Rules are: 
	 * 1) if the class is a worker, nothing else can occupy the spot
	 * 2) if the class is a tote, only a conveyor segment can occupy the spot as well
	 * 3) if the class is a conveyor segment, only a tote can occupy the spot as well
	 * 
	 */
	public boolean checkOccupantCompatibility(AgentAddress agent) {
		if (occupants.size()>0) { //only check if there are occupants already
			// Iterating over the elements in the set
			if (occupants.size()>1){
				return false;//Definitely cannot have more than 2 things in this spot
			}
			//otherwise we have to get the occupant's group and see if we can put agent in this spot too
			//only way to get the occupant is to iterate
			String agentGroup = agent.getGroup();
			Iterator<AgentAddress> it = occupants.iterator();
			while (it.hasNext()) {
				// Get group of 
				String occupierGroup = it.next().getGroup();
				if (agentGroup.equals("workers")){
					return false; //no matter what the agent is it cannot occupy with a Worker			    
				} else {
					if (agent.getRole().equals("tote")){
						//if the agent is a tote, and the occupier is a conveyor segment return true, otherwise return false
						if(occupierGroup.equals("conveyorsystem")) {
							return true;
						} else {
							return false;
						}
					}
				}

			}
		} 
		//if the occupants set is null then there is no question the new agent can occupy		
		return true;
		
	}
	
	
	
	
/*
	*//** gets the AgentAddress of the worker in this gridUnit. This will return null if no worker is here.  *//*  
	public AgentAddress getWorker() {
		return worker;
	}

	*//** sets the AgentAddress of the worker (if any) in this gridUnit.*//*  
	public  void setWorker(AgentAddress worker) {
		 this.worker = worker;
	}
	
	*//** gets the AgentAddress of the converyorSegment in this gridUnit. This will return null if no conveyor is here. *//*  
	public AgentAddress getConveyorSegment() {
		return conveyorSegment;
	}

	*//** sets the AgentAddress of the conveyorSegment (if any) in this gridUnit.  *//*  
	public  void setConveyorSegment(AgentAddress conveyorSegment) {
		 this.conveyorSegment = conveyorSegment;
	}
	
	*//** gets the AgentAddress of the tote in this gridUnit. This will return null if no tote is here.*//*  
	public AgentAddress getTote() {
		return tote;
	}

	*//** sets the AgentAddress of the tote (if any) in this gridUnit.  *//*  
	public  void setTote(AgentAddress tote) {
		 this.tote = tote;
	}
	
	*//** gets the AgentAddress of the storage bin in this gridUnit. This will return null if no storage bin is here. *//*  
	public AgentAddress getStorageBin() {
		return storageBin;
	}

	*//** sets the AgentAddress of the storage bin (if any) in this gridUnit.  *//*  
	public  void setStorageBin(AgentAddress storageBin) {
		 this.storageBin = storageBin;
	}

*/
	/** for testing *//*
	public static void main(String[] args) {
		GridUnit[][] environment = null; //the environment grid 
		environment  = new GridUnit[10][10];
		Picker aPicker = new Picker();
		aPicker.activate();
		AgentAddress p = aPicker.getAgentWithRole("warehouse", "worker", "picker");
		Sorter aSorter = new Sorter();
		aPicker.activate();
		AgentAddress s = aPicker.getAgentWithRole("warehouse", "worker", "sorter");
		
		environment[0][0].addAgent(p);
		boolean result = environment[0][0].checkOccupantCompatibility(s);
		result = environment[0][1].checkOccupantCompatibility(s);
		Tote aTote = new Tote();
		aTote.activate();
		AgentAddress t = aTote.getAgentWithRole("warehouse", "container", "tote");
		ConveyorSegment aCS = new ConveyorSegment(2);
		aCS.activate();
		AgentAddress cs = aCS.getAgentWithRole("warehouse","conveyorsystem", "segment");
		
		environment[2][2].addAgent(cs); 
		result = environment[2][2].checkOccupantCompatibility(t);
		result = environment[2][2].checkOccupantCompatibility(s);
	}*/

}

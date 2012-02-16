/*
 * ConveyerSegment.java - One segment of a conveyor system. It moves the tote, if any, to the next segment when commanded to move.
 *   One segment can hold one tote. 
 *   There are different types of conveyor segments: regular segment (moves at a constant speed),curved segment (changes direction vector),
 *    timer segment (can change speed to space totes), transfer segment (it can push totes off the conveyor), 
 *    and sensor segment (it tells the system where the tote is when it passes).
 *   
 */

package team64.madkit;

import java.awt.Color;
import java.util.logging.Level;

import madkit.kernel.AbstractAgent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Message;
import madkit.message.ActMessage;
import madkit.message.ObjectMessage;

/**
 * @author Team 64
 * 
 */
public class ConveyorSegment extends AbstractAgent
{

	AgentAddress toteID=null; //if this conveyor segment has a tote, this is the tote's id
	AgentAddress nextConveyorSegmentAddress = null; //so we know where the tote goes next
	public SegmentInformation segmentInfo = new SegmentInformation();//public visibility speeds up the probing process

	public ConveyorSegment(int location)
	{		
		segmentInfo.setSegmentLocation(location);
	}

	public void activate() {
		setLogLevel(Level.INFO);
		requestRole("warehouse","conveyorsystem", "segment",null);	

	}

	public void moveTote() {
		if(logger != null) {
			logger.finer("Scheduler has said to move my tote to the next segment if I have one...");   
		}
		if (segmentInfo.getHasTote()) {			
			sendMessage(nextConveyorSegmentAddress,new ToteMessage(toteID));			
			toteID=null;
			if (toteID!=null){		
				segmentInfo.setStateColor(Color.green);
				segmentInfo.setHasTote(true);
			}
			else {
				segmentInfo.setStateColor(Color.white);
				segmentInfo.setHasTote(false);
			}
		}
	}

	public void ejectTote() {
		if(logger != null) {
			logger.finer("Scheduler has said that if I get a tote to eject it ...");   
		}

	}

	public void receiveMessage(Message m) {
		if (m != null) {
			if(logger != null) {
				logger.finer("Conveyor Segment got a Message from " + m.getSender()+ " says: " +m.toString());   
			}
			String senderGroup = m.getSender().getGroup();
			String senderRole =  m.getSender().getRole();	
			//check if it is an object message
			if ((m instanceof ToteMessage)) {
				AgentAddress aa = ((ToteMessage) m).getToteAddress();
				addToteToConveyor(aa);
			}
			else if ((m instanceof ConveyorSegmentMessage)){
				AgentAddress aa = ((ConveyorSegmentMessage) m).getSegmentAddress();
				setNextConveyorLink(aa);
			}
			else if ((m instanceof ActMessage)) {
				if (toteID !=null) {
					sendMessage(nextConveyorSegmentAddress,new ToteMessage(toteID));
					toteID=null;
				}
			}
		}//m is null??
	}			
	
	/* sets the next conveyor segment to transfer a tote */
	public void setNextConveyorLink(AgentAddress nextConveyorSegment) {				 
			nextConveyorSegmentAddress =   nextConveyorSegment;
		} 
	

	public void addToteToConveyor(AgentAddress toteAddress) {
		if (toteID==null) {//this means we do not already have a tote sitting on us
			toteID = toteAddress;
		} else { //we have a tote already so we have to move our existing tote to the next conveyor segment
			sendMessage(nextConveyorSegmentAddress,new ToteMessage(toteID));
			toteID=toteAddress;
		}
		if (toteID!=null){		
			segmentInfo.setStateColor(Color.green);
			segmentInfo.setHasTote(true);
		}
		else {
			segmentInfo.setStateColor(Color.white);
			segmentInfo.setHasTote(false);
		}		
	}
	
	

public SegmentInformation getSegmentInfo(){
	return segmentInfo;
}

/** for drawing the conveyor segment */
class SegmentInformation {
	protected int segmentLocation; //a location to draw the segment representation
	protected Color stateColor;
	protected Color fontColor;
	protected boolean hasTote=false;

	public SegmentInformation() {		    
		stateColor = Color.white;
		//segmentLocation = new Point (0,0);
		segmentLocation=0; //this is the index of the textarea array
	}

	/**
	 * @return the stateColor
	 */
	public Color getStateColor() {
		return stateColor;
	}


	/**
	 * @param stateColor the stateColor to set
	 */
	public void setStateColor(Color stateColor) {
		this.stateColor = stateColor;
	}

	/**
	 * @return the segmentLocation
	 */
	public int getSegmentLocation() {
		return segmentLocation;
	}

	/**
	 * @param segmentLocation the segmentLocation to set
	 */
	public void setSegmentLocation(int segmentLocation) {
		this.segmentLocation = segmentLocation;
	}

	/**
	 * @return whether or not this segment has a tote on it
	 */
	public boolean getHasTote() {
		return hasTote;
	}

	/**
	 * @param sets whether or not this segment has a tote on it
	 */
	public void setHasTote(boolean hasTote) {
		this.hasTote = hasTote;
	}
}

}








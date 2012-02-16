package team64.madkit;

import madkit.kernel.AgentAddress;
import madkit.kernel.Message;


public class ConveyorSegmentMessage extends Message {
   
	protected AgentAddress segmentAddress;

	public ConveyorSegmentMessage(AgentAddress segment) {
		segmentAddress = segment;
	}
	
	/** gets the conveyor segment's agent address from the message */
	public AgentAddress getSegmentAddress() {
		return segmentAddress;
	}
	
	/** sets the conveyor segment's agent address for this message */
	public void setSegmentAddress(AgentAddress segmentAddress) {
		this.segmentAddress = segmentAddress;
	}


}

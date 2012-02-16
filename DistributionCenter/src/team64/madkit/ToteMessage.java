package team64.madkit;

/** this is a message sent by a tote to any other agent */

import madkit.kernel.AgentAddress;
import madkit.kernel.Message;


public class ToteMessage extends Message {
   
	protected AgentAddress toteAddress;

	public ToteMessage(AgentAddress tote) {
		toteAddress = tote;
	}
	
	/** gets the tote's agent address from the message */
	public AgentAddress getToteAddress() {
		return toteAddress;
	}
	
	/** sets the tote's agent address for this message */
	public void setToteAddress(AgentAddress toteAddress) {
		this.toteAddress = toteAddress;
	}


}

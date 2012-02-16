package team64.madkit;

import java.awt.Point;

import madkit.kernel.AgentAddress;
import madkit.kernel.Message;


public class LocationMessage extends Message {
   
	protected Point location;
	protected int estimatedDistance;
	

	public LocationMessage(Point location, int estimatedDistance) {
		this.location = location;
		this.estimatedDistance = estimatedDistance;
	}


	public Point getLocation() {
		return location;
	}


	public void setLocation(Point location) {
		this.location = location;
	}


	public int getEstimatedDistance() {
		return estimatedDistance;
	}


	public void setEstimatedDistance(int estimatedDistance) {
		this.estimatedDistance = estimatedDistance;
	}
	
	


}

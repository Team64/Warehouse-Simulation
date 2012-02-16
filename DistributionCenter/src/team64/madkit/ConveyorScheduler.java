/*
 * Makes the conveyor segments move totes to the next segment in the conveyor system
 */
package team64.madkit;

import java.util.logging.Level;

import madkit.action.SchedulingAction;
import madkit.kernel.Message;
import madkit.kernel.Scheduler;
import madkit.message.SchedulingMessage;
import madkit.simulation.GenericBehaviorActivator;

/**
 *
 * @author Team64
 */
public class ConveyorScheduler extends Scheduler
{


	private GenericBehaviorActivator<ConveyorSegment> conveyorSegmentActivator;
	private GenericBehaviorActivator<ConveyorSegment> conveyorEjectorActivator;
	private GenericBehaviorActivator<ConveyorSegment> conveyorViewerActivator;
	protected int conveyorSpeed = 5000; //time in milliseconds for conveyor segment movement
	

	/** generic constructor */
	ConveyorScheduler(){
	
	}

	public void activate() {
	
		super.activate();
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		requestRole("warehouse","conveyorsystem","scheduler",null);
		conveyorSegmentActivator = new GenericBehaviorActivator<ConveyorSegment>("warehouse","conveyorsystem", "segment","moveTote");
		conveyorEjectorActivator = new GenericBehaviorActivator<ConveyorSegment>("warehouse","conveyorsystem", "ejectorsegment","ejectTote");	
		conveyorViewerActivator = new GenericBehaviorActivator<ConveyorSegment>("warehouse","conveyorsystem","conveyorwatcher","showStatus");
		addActivator(conveyorSegmentActivator);	
		addActivator(conveyorViewerActivator);	
		setDelay(conveyorSpeed);			
		//auto starting myself the agent way
		receiveMessage(new SchedulingMessage(SchedulingAction.RUN));
	}

		@Override
	protected void checkMail(Message m) {
		if (m != null) {

		}
		super.checkMail(m);
	}


}

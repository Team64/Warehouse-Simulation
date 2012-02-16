/*
 * Used by the WarehouseWatcher to schedule updates to the graphical display 
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
public class DisplayScheduler extends Scheduler
{


	private GenericBehaviorActivator<Warehouse> warehouseViewerActivator;
	
	

	/** generic constructor */
	DisplayScheduler(){
	
	}

	public void activate() {
	
		super.activate();
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		requestRole("warehouse","warehouse","scheduler",null);
		/** this sets up the way to call "updateDisplay" for the watcher when the scheduler ticks */
		warehouseViewerActivator = new GenericBehaviorActivator<Warehouse>("warehouse","warehouse","watcher","updateDisplay");
		addActivator(warehouseViewerActivator);
		setDelay(Warehouse.updateSpeed);			
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

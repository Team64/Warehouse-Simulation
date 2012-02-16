/*
 * Makes the workers move on their tasks every second
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
public class WorkerScheduler extends Scheduler{

	
	private GenericBehaviorActivator<Picker> pickers;
	private GenericBehaviorActivator<Sorter> sorters;

	/** generic constructor */
	WorkerScheduler(){	
	}

	public void activate()
	{
		super.activate();
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		requestRole("warehouse","workers","scheduler",null);
		pickers = new GenericBehaviorActivator<Picker>("warehouse","workers","picker","move");
		sorters = new GenericBehaviorActivator<Sorter>("warehouse","workers","sorter","move");
		addActivator(pickers);		
		addActivator(sorters);	
		setDelay(2000);		
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

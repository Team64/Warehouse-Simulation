/*
 * A Watcher for any change in the Conveyor Segment information
 */
package team64.madkit;

import java.awt.Color;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;

import team64.madkit.ConveyorSegment.SegmentInformation;

import madkit.action.KernelAction;
import madkit.action.SchedulingAction;
import madkit.kernel.Watcher;
import madkit.message.KernelMessage;
import madkit.message.SchedulingMessage;
import madkit.simulation.PropertyProbe;

/**
 * @2012
 * @Team 64 
 */

public class ConveyorDisplay extends Watcher {


	private ConveyorView display;	
	protected static PropertyProbe<ConveyorSegment,SegmentInformation> pp;
	

	public ConveyorDisplay() {

	}

	@Override
	protected void activate()
	{
		super.activate();
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		requestRole("warehouse","conveyorsystem","conveyorwatcher");		
		//add a property probe that watches for changes in the conveyor segments
		pp = new PropertyProbe<ConveyorSegment, SegmentInformation>("warehouse","conveyorsystem","segment","segmentInfo");	
		addProbe(pp);
	}

	@Override
	protected void end() {
		removeProbe(pp);
		sendMessage("warehouse","conveyorsystem", "launcher", new KernelMessage(KernelAction.EXIT));
		sendMessage("warehouse","conveyorsystem", "scheduler", new SchedulingMessage(SchedulingAction.SHUTDOWN));//stopping the scheduler
		leaveRole("warehouse","conveyorsystem","conveyorwatcher");
	}

	public void showStatus()
	{
		if(! (display.isVisible() && isAlive())){
			return;
		}
		if(logger != null) {
			logger.finest("Updating conveyor display for " + pp.size()+ " conveyor segments");
		}
		List<ConveyorSegment> segmentList = pp.getShuffledList();
		for (int i=0;i<pp.size();i++) {
			SegmentInformation si=segmentList.get(i).getSegmentInfo();
			Color c = si.getStateColor();
			int graphicIndex = si.getSegmentLocation()-1;
			//map this to the display fields
			display.setColor(graphicIndex,c);
			display.setText(graphicIndex, "seg " + (graphicIndex+1));
			if (si.getHasTote()) {
			  display.setTextColor(graphicIndex,Color.white);
			} else {
				display.setTextColor(graphicIndex,Color.black);
			}
		}
		//display.repaint();
	}


	@Override
	public void setupFrame(JFrame frame) {
		display = new ConveyorView();
		display.setBounds(0, 0, 800,800);
		frame.add(display);		
		frame.setSize(800,800);
	}



}
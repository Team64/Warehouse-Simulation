/*
 * A Watcher for any change in the Conveyor Segment information
 */
package team64.madkit;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

import madkit.action.KernelAction;
import madkit.action.SchedulingAction;
import madkit.kernel.Watcher;
import madkit.message.KernelMessage;
import madkit.message.SchedulingMessage;
import madkit.simulation.PropertyProbe;
import team64.madkit.ConveyorSystem.ConveyorInformation;
import team64.madkit.Tote.ToteInformation;
import team64.madkit.Workers.WorkerInformation;

/**
 * @2012
 * @Team 64 
 */

public class WarehouseWatcher extends Watcher {


	private WarehouseGraphics display;	
	//private ReceivingDockPanel receivingDockArea;
	private LunchRoomArea lunchRoomArea = new LunchRoomArea();
	protected static PropertyProbe<ConveyorSystem,ConveyorInformation> ppConveyors;
	protected static PropertyProbe<Workers,WorkerInformation> ppWorkers;
	protected static PropertyProbe<Tote,ToteInformation> ppTotes;
	protected boolean drawGrid = true;
	

	public WarehouseWatcher() {

	}

	@Override
	protected void activate()
	{
		super.activate();
		getLogger().setWarningLogLevel(Level.INFO);
		setLogLevel(Level.FINEST);
		requestRole("warehouse","warehouse","watcher");		
		//set up a property probe that watches for changes in the warehouse's conveyor(s)
		ppConveyors = new PropertyProbe<ConveyorSystem, ConveyorInformation>("warehouse","conveyorsystem","conveyor","conveyorInfo");
		//set up a property probe that watches for changes in the warehouse's workers
		ppWorkers = new PropertyProbe<Workers,WorkerInformation>("warehouse","workers","worker","workerInfo");	
		//set up a property probe that watches for changes in the warehouse's totes
		ppTotes = new PropertyProbe<Tote,ToteInformation>("warehouse","container", "tote","toteInfo");	
		addProbe(ppConveyors);
		addProbe(ppWorkers);
		addProbe(ppTotes);
	}

	@Override
	protected void end() {
		removeProbe(ppConveyors);
		removeProbe(ppWorkers);
		removeProbe(ppTotes);
		sendMessage("warehouse","conveyorsystem", "launcher", new KernelMessage(KernelAction.EXIT));
		sendMessage("warehouse","conveyorsystem", "scheduler", new SchedulingMessage(SchedulingAction.SHUTDOWN));//stopping the scheduler
		leaveRole("warehouse","conveyorsystem","conveyorwatcher");
	}

	public void updateDisplay()
	{
		if(! (display.isVisible() && isAlive())){
			return;
		}
		if(logger != null) {
			logger.finest("Updating warehouse display ...");
		}
		display.repaint();
	}
	
	/** used by a watcher to paint the worker graphics */
	private void paintWorkers(Graphics g) {
		List<Workers> workerList = ppWorkers.getShuffledList();
		int gridSize = WarehouseGraphics.gridSize;
		for (int i=0;i<ppWorkers.size();i++) {
			WorkerInformation worker=workerList.get(i).workerInfo;
			Color c = worker.getWorkerColor();
			g.setColor(c);
			Point loc = worker.getLocation();
			//need to convert the grid location to a graphical gridunit location
			Point graphicLocation= WarehouseGraphics.convertGridToDisplay(loc.x, loc.y);
			g.fillOval(graphicLocation.x+gridSize/4,graphicLocation.y+gridSize/4,gridSize/2 ,gridSize/2);
			if(logger != null) {
				logger.finest("Painting a worker at x:" + graphicLocation.x + " y:"+graphicLocation.y);
			}
		}
	}
	
	/** used by a watcher to paint the conveyor systems graphics */
	private void paintConveyors(Graphics g) {
		List<ConveyorSystem> conveyorList = ppConveyors.getShuffledList();
		for (int i=0;i<ppConveyors.size();i++) {
			ConveyorInformation conveyor=conveyorList.get(i).conveyorInfo;
			//get enough information to draw the conveyor at its location
		}
	}
	
	/** used by a watcher to paint the tote graphics */
	private void paintTotes(Graphics g) {
		List<Tote> toteList = ppTotes.getShuffledList();
		for (int i=0;i<ppTotes.size();i++) {
			ToteInformation conveyor=toteList.get(i).toteInfo;
			//get enough information to draw the totes at their locations
		}
	}

	/** paint the lunchroom area */
	private void paintLunchRoom(Graphics g) {	 
	   g.setColor(lunchRoomArea.fillColor);
	   g.fillRect(lunchRoomArea.location.x,lunchRoomArea.location.y,lunchRoomArea.size.width,lunchRoomArea.size.height);
	}



	@Override
	public void setupFrame(JFrame frame) {
		display = new WarehouseGraphics(){	
			/** need to override the default so that updated information is used */
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				paintConveyors(g);
				paintWorkers(g);
				paintTotes(g);  
				paintLunchRoom(g);
				if(logger != null) {
					logger.finest("Graphics is " + g);
				}
				if (drawGrid) {
					g.setColor(Color.black);
					for (int column=0;column<Warehouse.warehouseWidth;column++) {
						//draw a square the gridSize x gridSize
						for (int row=0;row<Warehouse.warehouseHeight;row++) {
							g.drawRect(column*gridSize,row*gridSize,gridSize,gridSize);
						}
					}
				}					
			}
		};
        JLayeredPane jlp = new JLayeredPane();       
        JScrollPane jsp = new JScrollPane(jlp);
        jlp.setPreferredSize(WarehouseGraphics.convertGridToDimension());
        jsp.setViewportView(jlp);  
       
        jlp.add(display,0);
        jlp.add(new Canvas());
        
		//JScrollPane jsp = new JScrollPane(display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//jsp.setViewportView(display);
        //frame.add(jlp);
		
		//display.add(lunchRoomArea);
		//frame.add(receivingDockArea);
		frame.getContentPane().add(jsp,BorderLayout.CENTER);
		frame.setVisible(true);		
		frame.setPreferredSize(new Dimension(800, 800));
	}



}
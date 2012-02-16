package team64.madkit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;


public class LunchRoomArea {
	
	public Color fillColor = new Color(255,0,255,20); //light purple
	public Point location = new Point (Warehouse.lunchroomLocation.x*WarehouseGraphics.gridSize,Warehouse.lunchroomLocation.y*WarehouseGraphics.gridSize);
	public Dimension size = new Dimension(Warehouse.lunchroomWidth*WarehouseGraphics.gridSize,Warehouse.lunchroomDepth*WarehouseGraphics.gridSize);
	public LunchRoomArea() {
	    
	}
	

}

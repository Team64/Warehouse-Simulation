package team64.madkit;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;

public class WarehouseGraphics extends JPanel {
	public static int gridSize=40; //size of the grid in pixels (for drawing)

	public WarehouseGraphics() {
		setSize(new Dimension(Warehouse.warehouseWidth*gridSize,Warehouse.warehouseWidth*gridSize));
	}

	/** returns the upper left graphics point based on the grid unit location */
	public static Point convertGridToDisplay(int x, int y) {
		return new Point(x * gridSize, y * gridSize);
	}
	
	/** returns the upper left graphics point based on the grid unit location */
	public static Point convertGridToDisplay(Point p) {		
		return new Point(p.x * gridSize, p.y * gridSize);
	}
	
	/** returns the upper left graphics point based on the grid unit location */
	public static Dimension convertGridToDimension() {		
		return new Dimension (gridSize*Warehouse.warehouseWidth,gridSize*Warehouse.warehouseHeight);
	}
	
	
}

package team64.madkit;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextArea;

public class ConveyorView extends JPanel {
	JTextArea[] jta = null;
	public ConveyorView() {
		setLayout(new GridLayout(10, 10, 0, 0));
		jta = new JTextArea[100];		
		for (int i=0;i<100;i++) {
			jta[i] = new JTextArea();
			Font font = new Font(Font.DIALOG, Font.BOLD,12);
			jta[i].setFont(font);
			add(jta[i]);
		}
		
	}
	
	public void setText(int textAreaIndex,String text){
		jta[textAreaIndex].setText(text);
	}
	
	public void setColor(int textAreaIndex,Color c){
		jta[textAreaIndex].setBackground(c);
	}
	
	public void setTextColor(int textAreaIndex,Color c){
		jta[textAreaIndex].setForeground(c);
	}

}

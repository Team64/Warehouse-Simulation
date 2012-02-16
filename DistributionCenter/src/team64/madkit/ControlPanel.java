package team64.madkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class ControlPanel extends JFrame {
	public ControlPanel() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(425, 224, -424, -187);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewOrder = new JButton("New Order Received");
		btnNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//generate a new order and message to the warehouse
				//Note: normally we would query the database to see if there is a new order
			}
		});
		btnNewOrder.setBounds(22, 74, 157, 23);
		getContentPane().add(btnNewOrder);
		
		JButton btnNewShipment = new JButton("New Shipment Received");
		btnNewShipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//message to the warehouse that there is a new shipment of items to put away
				
			}
		});
		btnNewShipment.setBounds(22, 130, 169, 23);
		getContentPane().add(btnNewShipment);
	}
}

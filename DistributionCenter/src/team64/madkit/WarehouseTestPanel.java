package team64.madkit;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import madkit.message.ActMessage;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WarehouseTestPanel extends JPanel {
	public WarehouseTestPanel(final Warehouse w) {
		setLayout(null);
		
		JButton btnConveyorControl = new JButton("Stop Conveyors");
		btnConveyorControl.setToolTipText("stop or start conveyors");
		btnConveyorControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConveyorControl.setBounds(20, 112, 111, 23);
		add(btnConveyorControl);
		
		JButton btnNewOrder = new JButton("New Order To Pick");
		btnNewOrder.setToolTipText("Assign a Tote to an order");
		btnNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				w.sendMessage("warehouse","workers","picker",new ActMessage("a new order is in, wait for your tote and start picking!"));
			}
		});
		btnNewOrder.setBounds(20, 48, 153, 23);
		add(btnNewOrder);
		
		JButton btnAddPicker = new JButton("Add Another Picker");
		btnAddPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddPicker.setBounds(259, 198, 153, 23);
		add(btnAddPicker);
		
		JButton btnShipmentArrived = new JButton("Incoming Shipment");
		btnShipmentArrived.setToolTipText("Start a sorting process");
		btnShipmentArrived.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//message to the warehouse that there is a new shipment of items to put away
				w.sendMessage("warehouse","workers","sorter",new ActMessage("get to your sorting job!"));
			}
		});
		btnShipmentArrived.setBounds(223, 48, 171, 23);
		add(btnShipmentArrived);
		
		JButton btnLunchTime = new JButton("Lunchtime");
		btnLunchTime.setToolTipText("Send all workers to the lunchroom");
		btnLunchTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLunchTime.setBounds(154, 112, 101, 23);
		add(btnLunchTime);
		
		JButton btnAddSorter = new JButton("Add Another Sorter");
		btnAddSorter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddSorter.setBounds(20, 198, 143, 23);
		add(btnAddSorter);
		
		JButton btnRemoveSorter = new JButton("Remove A Sorter");
		btnRemoveSorter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemoveSorter.setBounds(20, 232, 143, 23);
		add(btnRemoveSorter);
		
		JButton btnRemovePicker = new JButton("Remove A Picker");
		btnRemovePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemovePicker.setBounds(259, 236, 143, 23);
		add(btnRemovePicker);
		
		JButton btnIdle = new JButton("Make All Workers Idle");
		btnIdle.setToolTipText("All workers abandon their roles");
		btnIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIdle.setBounds(284, 112, 143, 23);
		add(btnIdle);
		
		JLabel lblNewLabel = new JLabel("Manual Simulation Control");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 0, 277, 26);
		add(lblNewLabel);
		
		JButton btnGetToWork = new JButton("Everyone Get To Work!");
		btnGetToWork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGetToWork.setToolTipText("Make all workers do something");
		btnGetToWork.setBounds(182, 146, 195, 23);
		add(btnGetToWork);
	}
}

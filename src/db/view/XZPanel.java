package db.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import db.dao.PersonDAO_impl;
import db.dao.impl.PersonDAO_Mock;
import db.model.XZTableModel;

public class XZPanel extends JPanel
{
	JTable tbl;
	XZTableModel mdl;

	public XZPanel(XZCommand cmd)
	{
		cmd.bp = this;
		this.setLayout(null);
		mdl = new XZTableModel(new PersonDAO_Mock());
		tbl = new JTable(mdl);
		
		JScrollPane scr = new JScrollPane(tbl);
		scr.setBounds(10, 10, 960, 500);
		this.add(scr);

		JComboBox<String> changer = new JComboBox<String>(PersonDAO_impl.getDBNames());
		changer.setBounds(1020, 10, 250, 25);
		changer.addActionListener(cmd.aCombo);
		add(changer);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 560, 960, 30);
		buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));

		String[] strButton = {"create", "read", "update", "delete", "search"};
		for(int i = 0; i < strButton.length; i++)
		{
			JButton btn = new JButton(strButton[i]);
			btn.setBackground(Color.blue);
			btn.setForeground(Color.yellow);
			switch(i)
			{
			//case 0: btn.addActionListener(new ActionCreate()); break;
			case 0: btn.addActionListener(cmd.aCreate); break;
			case 1: btn.addActionListener(new ActionRead()); break;
			//case 1: btn.addActionListener(cmd.aRead); break;
			//case 2: btn.addActionListener(new ActionUpdate()); break;
			case 2: btn.addActionListener(cmd.aUpdate); break;
			//case 3: btn.addActionListener(new ActionDelete()); break;
			case 3: btn.addActionListener(cmd.aDelete); break;
			//case 4: btn.addActionListener(new ActionSearch()); break;
			case 4: btn.addActionListener(cmd.aSearch); break;
			}
			buttonPanel.add(btn);
		}
		add(buttonPanel);
		setVisible(true);
	}

	class ActionRead implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			mdl.read();				
		}
	}
}
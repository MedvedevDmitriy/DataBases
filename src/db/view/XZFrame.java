package db.view;

import javax.swing.*;

public class XZFrame extends JFrame
{
	public XZFrame()
	{	
		setBounds(175, 50, 1400, 650);
		setTitle("Table");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		XZCommand cmd = new XZCommand();
		add(new XZPanel(cmd));
		setVisible(true);
	}
}
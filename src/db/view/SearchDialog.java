package db.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import db.model.Person;
import db.model.XZTableModel;

public class SearchDialog extends JDialog
{
	JTextField idStart, idEnd, ageStart, ageEnd, fnameRegex, lnameRegex;
	ButtonGroup group;
	
	private ArrayList<Person> filteredList = new ArrayList<Person>();
	private XZTableModel tableModel = null;
	
//	JTextField		idStart;
//	JTextField		idEnd;
//	JTextField		text1;
//	JTextField		text2;
//	JTextField		ageStart;
//	JTextField		ageEnd;
	
	public static int OK_RESULT = 1;
	private int result = 0;
	
	public SearchDialog()
	{
		setLayout(null);
		setTitle("Search");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(150, 130, 300, 360);
		setModal(true);

		// Selection radio buttons
		group = new ButtonGroup();
		SearchChange changer = new SearchChange();

		JRadioButton byId = new JRadioButton("By ID:");
		byId.setSelected(true);
		byId.setBounds(10, 10, 80, 20);
		byId.setActionCommand("id");
		byId.addActionListener(changer);

		JRadioButton byAge = new JRadioButton("By Age:");
		byAge.setBounds(10, 80, 80, 20);
		byAge.setActionCommand("age");
		byAge.addActionListener(changer);

		JRadioButton byFname = new JRadioButton("By First Name:");
		byFname.setBounds(10, 150, 120, 20);
		byFname.setActionCommand("fname");
		byFname.addActionListener(changer);

		JRadioButton byLname = new JRadioButton("By Last Name:");
		byLname.setBounds(10, 220, 120, 20);
		byLname.setActionCommand("lname");
		byLname.addActionListener(changer);

		group.add(byId);
		group.add(byAge);
		group.add(byFname);
		group.add(byLname);

		add(byId);
		add(byAge);
		add(byFname);
		add(byLname);

		// Text fields
		idStart = new JTextField();
		idEnd = new JTextField();
		ageStart = new JTextField();
		ageEnd = new JTextField();
		fnameRegex = new JTextField();
		lnameRegex = new JTextField();

		idStart.setBounds(10, 40, 100, 20);
		idEnd.setBounds(120, 40, 100, 20);
		ageStart.setBounds(10, 110, 100, 20);
		ageEnd.setBounds(120, 110, 100, 20);
		fnameRegex.setBounds(10, 180, 100, 20);
		lnameRegex.setBounds(10, 250, 100, 20);

		ageStart.setEditable(false);
		ageEnd.setEditable(false);
		fnameRegex.setEditable(false);
		lnameRegex.setEditable(false);

		add(idStart);
		add(idEnd);
		add(ageStart);
		add(ageEnd);
		add(fnameRegex);
		add(lnameRegex);
		
		// Confirm button
		JButton find = new JButton("Find");
		find.setBounds(10, 280, 80, 20);
		find.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = 1;
				setVisible(false);
			}
		});
		add(find);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(100, 280, 80, 20);
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = 0;
				setVisible(false);
			}
		});
		add(cancel);
	}
	
	/*public SearchDialog(JFrame owner, final XZTableModel tableModel)
	{
		super(owner, "Search Person", true);
		
		this.tableModel = tableModel;
		
		setLayout(null);
		
		JLabel jl = new JLabel("id");
		jl.setBounds(10,130,50,50);
		add(jl);
		
		idStart = new JTextField();
		idStart.setBounds(70, 150, 100, 20);
		add(idStart);
		
		idEnd = new JTextField();
		idEnd.setBounds(180, 150, 100, 20);
		add(idEnd);
		
		JLabel jl2 = new JLabel("fname");
		jl2.setBounds(10,160,50,50);
		add(jl2);
		
		text1 = new JTextField();
		text1.setBounds(70, 180, 100, 20);
		add(text1);
		
		JLabel jl3 = new JLabel("lname");
		jl3.setBounds(10,190,50,50);
		add(jl3);
		
		text2 = new JTextField();
		text2.setBounds(70, 210, 100, 20);
		add(text2);
		
		JLabel jl4 = new JLabel("age");
		jl4.setBounds(10,220,50,50);
		add(jl4);
		
		ageStart = new JTextField();
		ageStart.setBounds(70, 240, 100, 20);
		add(ageStart);
		
		ageEnd = new JTextField();
		ageEnd.setBounds(180, 240, 100, 20);
		add(ageEnd);	
		
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		
		ok.addActionListener(new OKListener());
		cancel.addActionListener(new CancelListener());
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.add(ok);
		panel.add(cancel);
		add(panel, BorderLayout.SOUTH);
		setBounds(20, 20, 400, 500);
		
		setResizable(false);
		setModal(true);
		setVisible(true);
	}
	
	class OKListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			search();
			dispose();
		}		
	}
	
	class CancelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			dispose();
		}	
	}*/
	
	public int getShowResult()
	{
		return result;
	}
	
	public ArrayList<Person> getSearchResults(ArrayList<Person> pp)
	{
		ButtonModel model = group.getSelection();
		String command = model.getActionCommand();
		ArrayList<Person> reply = new ArrayList<Person>();
		
		switch(command)
		{
		case "id":
			int start = Integer.parseInt(idStart.getText());
			int end = Integer.parseInt(idEnd.getText());
			for(Person p : pp)
			{
				if (p.id >= start && p.id <= end)
					reply.add(p);
			}
			break;
		case "age":
			int from = Integer.parseInt(ageStart.getText());
			int to = Integer.parseInt(ageEnd.getText());
			for(Person p : pp)
			{
				if (p.age >= from && p.age <= to)
					reply.add(p);
			}
			break;
		case "fname":
			String fRegex = fnameRegex.getText();
			for(Person p : pp)
			{
				if (p.fname.toLowerCase().matches("(.*)" + fRegex.toLowerCase() + "(.*)"))
					reply.add(p);
			}
			break;
		case "lname":
			String lRegex = lnameRegex.getText();
			for(Person p : pp)
			{
				if (p.lname.toLowerCase().matches("(.*)" + lRegex.toLowerCase() + "(.*)"))
					reply.add(p);
			}
			break;
		}
		
		return reply;
	}

	private class SearchChange implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			idStart.setText("");
			idEnd.setText("");
			ageStart.setText("");
			ageEnd.setText("");
			fnameRegex.setText("");
			lnameRegex.setText("");

			idStart.setEditable(false);
			idEnd.setEditable(false);
			ageStart.setEditable(false);
			ageEnd.setEditable(false);
			fnameRegex.setEditable(false);
			lnameRegex.setEditable(false);

			String command = e.getActionCommand();

			switch(command)
			{
				case "id":
					idStart.setEditable(true);
					idEnd.setEditable(true);
					break;
				case "age":
					ageStart.setEditable(true);
					ageEnd.setEditable(true);
					break;
				case "fname":
					fnameRegex.setEditable(true);
					break;
				case "lname":
					lnameRegex.setEditable(true);
					break;
			}
		}
	}
	
	/*private void search()
	{		
		for (Person p : tableModel.pp)
		{
			boolean bContinueSearching = true;
			
			if (!idStart.getText().isEmpty() && !idEnd.getText().isEmpty())
			{
				if (p.id < Integer.parseInt(idStart.getText()) || p.id > Integer.parseInt(idEnd.getText()))
					bContinueSearching = false;
			}
			else if (!idStart.getText().isEmpty())
			{
				if (p.id != Integer.parseInt(idStart.getText()))
					bContinueSearching = false;
			}
			else if (!idEnd.getText().isEmpty())
			{
				if (p.id != Integer.parseInt(idEnd.getText()))
					bContinueSearching = false;
			}
			
			if (bContinueSearching && !p.fname.matches("(.*)" + text1.getText() + "(.*)"))
				bContinueSearching = false;
			
			if (bContinueSearching && !p.lname.matches("(.*)" + text2.getText() + "(.*)"))
				bContinueSearching = false;
			
			if (bContinueSearching && !ageStart.getText().isEmpty() && !ageEnd.getText().isEmpty())
			{
				if (p.age < Integer.parseInt(ageStart.getText()) || p.age > Integer.parseInt(ageEnd.getText()))
					bContinueSearching = false;
			}
			else if (bContinueSearching && !ageStart.getText().isEmpty())
			{
				if (p.age != Integer.parseInt(ageStart.getText()))
					bContinueSearching = false;
			}
			else if (bContinueSearching && !ageEnd.getText().isEmpty())
			{
				if (p.age != Integer.parseInt(ageEnd.getText()))
					bContinueSearching = false;
			}

			if (bContinueSearching)
				filteredList.add(p);
		}
		if (	idStart.getText().isEmpty()		&&
				idEnd.getText().isEmpty()		&&
				text1.getText().isEmpty()		&&
				text2.getText().isEmpty()		&&
				ageStart.getText().isEmpty()	&&
				ageEnd.getText().isEmpty()
			)
		{
			return;
		}
		else
		{
			tableModel.pp = filteredList;
			tableModel.fireTableDataChanged();
		}				
	}*/
}

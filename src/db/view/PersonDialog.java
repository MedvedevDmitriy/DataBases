package db.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import db.model.Person;
import db.model.XZTableModel;

public class PersonDialog extends JDialog
{
	JTextField id;	
	JTextField fname;
	JTextField lname;	
	JTextField age;
	
	Person p;
	String command = "";
	
	ArrayList<Person> filteredList = new ArrayList<Person>();
	XZTableModel tableModel = null;
	
	public static int OK_RESULT = 1;
	private int result = 0;
	
//	public PersonDialog(JFrame owner, XZTableModel xztm)
//	{
//		super(owner, "Handle Person", true);
//		init(owner, xztm);
//	}
	
	private PersonDialog()
	{
	}

	public PersonDialog(String mode)
	{
		setLayout(null);
		setTitle(mode);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 150);

		JLabel lId = new JLabel("Id");
		JLabel lFname = new JLabel("First Name");
		JLabel lLname = new JLabel("Lirst Name");
		JLabel lAge = new JLabel("Age");

		lId.setBounds(55,10,20,20);
		lFname.setBounds(140,10,100,20);
		lLname.setBounds(260,10,100,20);
		lAge.setBounds(400,10,40,20);

		add(lId);
		add(lFname);
		add(lLname);
		add(lAge);

		id = new JTextField();
		fname = new JTextField();
		lname = new JTextField();
		age = new JTextField();

		id.setBounds(10,40,100,20);
		fname.setBounds(120,40,100,20);
		lname.setBounds(240,40,100,20);
		age.setBounds(360,40,100,20);

		add(id);
		add(fname);
		add(lname);
		add(age);

		JButton but_ok = new JButton(mode);
		but_ok.setBounds(190, 80, 100, 20);
		but_ok.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = 1;
				setVisible(false);
			}
		});
		add(but_ok);

		JButton cancel = new JButton("Cancel");
		cancel.setBounds(310, 80, 100, 20);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = 0;
				setVisible(false);
			}
		});
		add(cancel);
	}

	/*public PersonDialog(JFrame owner, XZTableModel tableModel, Person person, String command)
	{
		super(owner, "Handle Person", true);
		this.command = command;
		p = person;
		init(owner, tableModel);		
	}
	
	void init(JFrame owner, XZTableModel tableModel)
	{
		this.tableModel = tableModel;

		setLayout(null);

		JLabel jl = new JLabel("id");
		jl.setBounds(10, 130, 50, 50);
		add(jl);

		id = new JTextField();
		id.setBounds(70, 150, 100, 20);
		if (p != null)
			id.setText("" + p.id);
		add(id);

		JLabel jl2 = new JLabel("fname");
		jl2.setBounds(10,160,50,50);
		add(jl2);

		fname = new JTextField();
		fname.setBounds(70, 180, 100, 20);
		if (p != null)
			fname.setText(p.fname);
		add(fname);

		JLabel jl3 = new JLabel("lname");
		jl3.setBounds(10,190,50,50);
		add(jl3);

		lname = new JTextField();
		lname.setBounds(70, 210, 100, 20);
		if (p != null)
			lname.setText(p.lname);
		add(lname);

		JLabel jl4 = new JLabel("age");
		jl4.setBounds(10,220,50,50);
		add(jl4);

		age = new JTextField();
		age.setBounds(70, 240, 100, 20);
		if (p != null)
			age.setText("" + p.age);
		add(age);

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
			handlePerson();
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
	}

	private void handlePerson()
	{
		p = new Person(Integer.parseInt(id.getText()), fname.getText(), lname.getText(), Integer.parseInt(age.getText()));
		switch(command)
		{
			case "create": tableModel.create(p); break;
			case "update": tableModel.update(p); break;
			case "delete": tableModel.delete(p); break;
		}
	}*/
	
	public int getShowResult()
	{
		return result;
	}
	
	public Person getPerson()
	{
		Person p = new Person();

		p.setId(Integer.parseInt(id.getText()));
		p.setFName(fname.getText());
		p.setLName(lname.getText());
		p.setAge(Integer.parseInt(age.getText()));
		
		return p;
	}
}
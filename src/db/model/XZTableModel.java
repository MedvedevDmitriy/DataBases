package db.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import db.dao.PersonDAO;
import db.dao.impl.PersonDAO_Mock;

public class XZTableModel extends AbstractTableModel
{
	PersonDAO pd = new PersonDAO_Mock();
	public ArrayList<Person> pp = new ArrayList<Person>();
	
	public XZTableModel(PersonDAO pd)
	{
		this.pd = pd;
		pp = pd.read();
	}
	
	@Override
	public int getColumnCount() 
	{
		return 4;
	}

	@Override
	public String getColumnName(int column) 
	{
		String[] name = {"ID","FName","LName","Age"};
		return name[column];
	}
	
	@Override
	public int getRowCount()
	{
		return pp.size();
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Person p = pp.get(row);
		Object ret = null;	
		switch (col)
		{
			case 0: ret = p.id; break;
			case 1: ret = p.fname; break;
			case 2: ret = p.lname; break;
			case 3: ret = p.age; break;
		}	
		return ret;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		return true;
	}
	
	public void create(Person p)
	{
		pd.create(p);
		read();
	}
	
	public void read()
	{
		pp = pd.read();
		fireTableDataChanged();
	}
	
	public void delete(Person p)
	{
		pd.delete(p);
		read();
	}
	
	public void update(Person p)
	{
		pd.update(p);
		read();
	}
	
	public void searchRefresh(ArrayList<Person> pp)
	{
		this.pp = pp;
		fireTableDataChanged();
	}
}
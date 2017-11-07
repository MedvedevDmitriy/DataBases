package db.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import db.dao.PersonDAO;
import db.dao.PersonDAO_impl;

import db.model.Person;
import db.model.XZTableModel;

public class XZCommand
{
	ActionCreate aCreate = new ActionCreate();
	ActionUpdate aUpdate = new ActionUpdate();
	ActionDelete aDelete = new ActionDelete();
	//ActionRead   aRead   = new ActionRead();
	ActionCombo  aCombo  = new ActionCombo();
	ActionSearch aSearch = new ActionSearch();

	public XZPanel bp = null;
	
	class ActionCreate implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			PersonDialog bd = new PersonDialog("Create");
			bd.setVisible(true);
				if( bd.getShowResult() == PersonDialog.OK_RESULT)
				{
					bp.mdl.create(bd.getPerson());
				}
			
			//new PersonDialog(frame, tableModel, null, e.getActionCommand());
		}
	}

//	class ActionRead implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			tableModel.read();				
//		}
//	}
	
	class ActionUpdate implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			PersonDialog bd = new PersonDialog("Update");
			bd.setVisible(true);
			if(bd.getShowResult() == PersonDialog.OK_RESULT)
			{
				bp.mdl.update(bd.getPerson());
			}
			
//			int numRow = table.getSelectedRow();
//			if (numRow >= 0)
//			{
//				Person p = tableModel.pp.get(numRow);
//				new PersonDialog(frame, tableModel, p, e.getActionCommand());
//			}
//			tableModel.read();
		}
	}
	
	class ActionDelete implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			PersonDialog bd = new PersonDialog("Delete");
			bd.setVisible(true);
			if(bd.getShowResult() == PersonDialog.OK_RESULT)
			{
				bp.mdl.delete(bd.getPerson());
			}
			
//			int numRow = table.getSelectedRow();
//			if (numRow >= 0)
//			{
//				Person p = tableModel.pp.get(numRow);
//				new PersonDialog(frame, tableModel, p, e.getActionCommand());
//			}
//			tableModel.read();
		}
	}

	class ActionSearch implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			SearchDialog bds = new SearchDialog();
			bds.setVisible(true);
			if(bds.getShowResult() == SearchDialog.OK_RESULT)
			{
				ArrayList<Person> pp = bds.getSearchResults(bp.mdl.pp);
				bp.mdl.searchRefresh(pp);
			}
			
			//new SearchDialog(frame, tableModel);			
		}
	}
	
	class ActionCombo implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox box = (JComboBox)e.getSource();
			String item = (String)box.getSelectedItem();
			
			bp.mdl = new XZTableModel(PersonDAO_impl.getImpl(item));
			bp.tbl.setModel(bp.mdl);
			bp.mdl.read();
		}
	}
}
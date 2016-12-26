package com.mrcrayfish.modelcreator.util.components;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class Table<T> extends JScrollPane
{
	TableModel model;
	ArrayList<String[]> rows = new ArrayList<String[]>();
	Field[] fields;
	String[] fieldNames;
	JTable table;
	private SelectionListener<T> listener;
	private T[] records;
	
	public Table(T dummy)
	{
		table = new JTable();
		
		Field[] reflectedFields = getFields(dummy);

		ArrayList<String> fieldNamesList =  new ArrayList<String>();
		ArrayList<Field> fieldList = new ArrayList<Field>();
		
		for (Field f : reflectedFields)
		{
			if (!Modifier.isStatic(f.getModifiers()))
			{
				Annotation annotation = f.getAnnotation(Header.class);
							
				if (annotation != null)
				{
					Table.Header h = (Table.Header)annotation;
					fieldNamesList.add(h.name());
				}
				else
					fieldNamesList.add(f.getName());
				
				fieldList.add(f);
			}
		}
		fieldNames = fieldNamesList.toArray(new String[0]);
		fields = fieldList.toArray(new Field[0]);
		
		setViewportView(table);

		clearContents();

		ListSelectionModel selectionModel = table.getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	if (e.getValueIsAdjusting() && listener != null)
		    	{
			    	int row = table.getSelectedRow();
			    	if (row >= 0)
			    		listener.selectionChanged(records[row]);
		    	}
		    }
		});		
	}

	public void setSelectionListener(SelectionListener<T> listener)
	{
		this.listener = listener;
	}
	
	public T getSelectedItem()
	{
    	int row = table.getSelectedRow();
  		return records[row];
	}
	
	public void setContents(T[] records)
	{						
		this.records = records;
		table.setModel(new TableModel()
		{
			@Override
			public Object getValueAt(int row, int column)
			{
				T record = records[row];
				
				try
				{
					return fields[column].get(record);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					return null;
				}
			}

			@Override
			public void addTableModelListener(TableModelListener arg0)
			{
			}

			@Override
			public Class<?> getColumnClass(int arg0)
			{
				return fields[arg0].getType(); 
			}

			@Override
			public int getColumnCount()
			{
				return fields.length;
			}

			@Override
			public String getColumnName(int arg0)
			{
				return fieldNames[arg0];
			}

			@Override
			public int getRowCount()
			{
				return records.length;
			}

			@Override
			public boolean isCellEditable(int arg0, int arg1)
			{
				return false;
			}

			@Override
			public void removeTableModelListener(TableModelListener arg0)
			{
			}

			@Override
			public void setValueAt(Object arg0, int arg1, int arg2)
			{
			}
		});
		
		((JLabel)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);		
	}

	public void clearContents()
	{
		this.records = null;
		table.setModel(new TableModel()
		{
			@Override
			public Object getValueAt(int row, int column)
			{
				return null;
			}

			@Override
			public void addTableModelListener(TableModelListener arg0)
			{
			}

			@Override
			public Class<?> getColumnClass(int arg0)
			{
				return fields[arg0].getType(); 
			}

			@Override
			public int getColumnCount()
			{
				return fields.length;
			}

			@Override
			public String getColumnName(int arg0)
			{
				return fieldNames[arg0];
			}

			@Override
			public int getRowCount()
			{
				return 0;
			}

			@Override
			public boolean isCellEditable(int arg0, int arg1)
			{
				return false;
			}

			@Override
			public void removeTableModelListener(TableModelListener arg0)
			{
			}

			@Override
			public void setValueAt(Object arg0, int arg1, int arg2)
			{
			}
		});
		
		((JLabel)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);				
	}
	
	private Field[] getFields(T dummy)
	{
		return dummy.getClass().getFields();
	}

	public interface SelectionListener<T>
	{
		void selectionChanged(T record);
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)	
	public @interface Header
	{
		String name();
	}
}

package com.mrcrayfish.modelcreator.util.undo;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.panels.CuboidTabbedPane;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class RemoveCubeTask extends UndoQueue.Task
{
	private DefaultListModel<Element> model;
	private JList<Element> list;
	private Element element;
	private int oldSelectedIndex;
	private JTextField name;
	private String oldName;
	private CuboidTabbedPane tabbedPane;

	public RemoveCubeTask(DefaultListModel<Element> model, JList<Element> list, JTextField name, CuboidTabbedPane tabbedPane)
	{
		this.model = model;
		this.list = list;
		this.tabbedPane = tabbedPane;
		element = list.getSelectedValue();
		oldSelectedIndex = list.getSelectedIndex();
		oldName = name.getText();
	}

	@Override
	public void undo()
	{
		model.addElement(element);
		list.setSelectedIndex(oldSelectedIndex);
		name.setText(oldName);
		tabbedPane.updateValues();
		name.setEnabled(true);
	}

	@Override
	public void redo()
	{
		model.removeElement(element);
		list.setSelectedIndex(oldSelectedIndex);
		tabbedPane.updateValues();
		name.setEnabled(false);
	}
}

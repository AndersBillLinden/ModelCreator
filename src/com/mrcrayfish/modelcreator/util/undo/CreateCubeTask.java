package com.mrcrayfish.modelcreator.util.undo;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CreateCubeTask extends UndoQueue.Task
{
	private DefaultListModel<Element> model;
	private JList<Element> list;
	private Element element;
	private int oldSelectedIndex;

	public CreateCubeTask(DefaultListModel<Element> model, JList<Element> list)
	{
		this.model = model;
		this.list = list;
		oldSelectedIndex = list.getSelectedIndex();
	}

	@Override
	public void undo()
	{
		element = model.lastElement();
		model.removeElement(element);
		list.setSelectedIndex(oldSelectedIndex);
	}

	@Override
	public void redo()
	{
		model.addElement(element);
		list.setSelectedIndex(model.size() - 1);		
	}
}

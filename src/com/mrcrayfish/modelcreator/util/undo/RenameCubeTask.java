package com.mrcrayfish.modelcreator.util.undo;

import javax.swing.JList;
import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class RenameCubeTask extends UndoQueue.Task
{
	private Element cube;
	private String oldName;
	private String newName;
	private JTextField name;
	private JList<Element> list;

	public RenameCubeTask(Element cube, JTextField name, JList<Element> list)
	{
		this.cube = cube;
		this.name = name;
		this.list = list;
		
		oldName = cube.getName();
		newName = name.getText();
	}

	@Override
	public void undo()
	{
		cube.setName(oldName);
		name.setText(oldName);
		list.updateUI();		
		
	}

	@Override
	public void redo()
	{
		cube.setName(newName);
		name.setText(newName);
		list.updateUI();		
	}
}

package com.mrcrayfish.modelcreator.util.undo;

import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.panels.PositionPanel;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeAddStartXTask extends UndoQueue.Task
{
	private Element cube;
	private float delta;
	private JTextField xPositionField;
	private double oldStartX;
	
	public CubeAddStartXTask(Element cube, float delta, JTextField xPositionField)
	{
		this.cube = cube;
		this.delta = delta;
		this.xPositionField = xPositionField;
		oldStartX = cube.getStartX();
	}
	
	@Override
	public void undo()
	{
		cube.setStartX(oldStartX);
		update();
	}

	@Override
	public void redo()
	{
		cube.addStartX(delta);
		update();
	}
	
	private void update()
	{
		xPositionField.setText(PositionPanel.df.format(cube.getStartX()));		
	}
}

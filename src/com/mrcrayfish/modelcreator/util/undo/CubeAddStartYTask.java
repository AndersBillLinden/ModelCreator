package com.mrcrayfish.modelcreator.util.undo;

import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.panels.PositionPanel;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeAddStartYTask extends UndoQueue.Task
{
	private Element cube;
	private float delta;
	private JTextField yPositionField;
	private double oldStartY;
	
	public CubeAddStartYTask(Element cube, float delta, JTextField yPositionField)
	{
		this.cube = cube;
		this.delta = delta;
		this.yPositionField = yPositionField;
		oldStartY = cube.getStartY();
	}
	
	@Override
	public void undo()
	{
		cube.setStartY(oldStartY);
		update();
	}

	@Override
	public void redo()
	{
		cube.addStartY(delta);
		update();
	}
	
	private void update()
	{
		yPositionField.setText(PositionPanel.df.format(cube.getStartY()));		
	}
}

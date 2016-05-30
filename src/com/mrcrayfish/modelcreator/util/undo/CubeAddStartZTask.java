package com.mrcrayfish.modelcreator.util.undo;

import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.panels.PositionPanel;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeAddStartZTask extends UndoQueue.Task
{
	private Element cube;
	private float delta;
	private JTextField zPositionField;
	private double oldStartZ;
	
	public CubeAddStartZTask(Element cube, float delta, JTextField zPositionField)
	{
		this.cube = cube;
		this.delta = delta;
		this.zPositionField = zPositionField;
		oldStartZ = cube.getStartZ();
	}
	
	@Override
	public void undo()
	{
		cube.setStartZ(oldStartZ);
		update();
	}

	@Override
	public void redo()
	{
		cube.addStartZ(delta);
		update();
	}
	
	private void update()
	{
		zPositionField.setText(PositionPanel.df.format(cube.getStartZ()));		
	}
}

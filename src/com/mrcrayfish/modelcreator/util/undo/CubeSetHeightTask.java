package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeSetHeightTask extends UndoQueue.Task
{
	private Element cube;
	private double newHeight;
	private ElementManager manager;
	private double oldHeight;

	public CubeSetHeightTask(Element cube, double newHeight, ElementManager manager)
	{
		this.cube = cube;
		this.newHeight = newHeight;
		this.manager = manager;
		oldHeight = cube.getHeight();
	}

	@Override
	public void undo()
	{
		cube.setHeight(oldHeight);
		cube.updateUV();
		manager.updateValues();
	}

	@Override
	public void redo()
	{
		cube.setHeight(newHeight);
		cube.updateUV();
		manager.updateValues();		
	}
}

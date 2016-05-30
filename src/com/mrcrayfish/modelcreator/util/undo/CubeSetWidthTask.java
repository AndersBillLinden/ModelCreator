package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeSetWidthTask extends UndoQueue.Task
{
	private Element cube;
	private double newWidth;
	private ElementManager manager;
	private double oldWidth;

	public CubeSetWidthTask(Element cube, double newWidth, ElementManager manager)
	{
		this.cube = cube;
		this.newWidth = newWidth;
		this.manager = manager;
		oldWidth = cube.getWidth();
	}

	@Override
	public void undo()
	{
		cube.setWidth(oldWidth);
		cube.updateUV();
		manager.updateValues();
	}

	@Override
	public void redo()
	{
		cube.setWidth(newWidth);
		cube.updateUV();
		manager.updateValues();		
	}
}

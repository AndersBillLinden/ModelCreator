package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeAddWidthTask extends UndoQueue.Task
{
	private Element cube;
	private float delta;
	private ElementManager manager;
	private double oldWidth;

	public CubeAddWidthTask(Element cube, float delta, ElementManager manager)
	{
		this.cube = cube;
		this.delta = delta;
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
		cube.addWidth(delta);
		cube.updateUV();
		manager.updateValues();
	}
}

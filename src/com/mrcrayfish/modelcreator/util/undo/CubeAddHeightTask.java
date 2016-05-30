package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeAddHeightTask extends UndoQueue.Task
{
	private Element cube;
	private float delta;
	private ElementManager manager;
	private double oldHeight;

	public CubeAddHeightTask(Element cube2, float delta, ElementManager manager)
	{
		this.cube = cube2;
		this.delta = delta;
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
		cube.addHeight(delta);
		cube.updateUV();
		manager.updateValues();
	}

}

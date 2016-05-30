package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeAddDepthTask extends UndoQueue.Task
{
	private Element cube;
	private float delta;
	private ElementManager manager;
	private double oldDepth;

	public CubeAddDepthTask(Element cube, float delta, ElementManager manager)
	{
		this.cube = cube;
		this.delta = delta;
		this.manager = manager;
		oldDepth = cube.getDepth();
	}

	@Override
	public void undo()
	{
		cube.setDepth(oldDepth);
		cube.updateUV();
		manager.updateValues();
	}

	@Override
	public void redo()
	{
		cube.addDepth(delta);
		cube.updateUV();
		manager.updateValues();		
	}

}

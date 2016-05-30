package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class CubeSetDepthTask extends UndoQueue.Task
{
	private Element cube;
	private double newDepth;
	private ElementManager manager;
	private double oldDepth;

	public CubeSetDepthTask(Element cube, double newDepth, ElementManager manager)
	{
		this.cube = cube;
		this.newDepth = newDepth;
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
		cube.setDepth(newDepth);
		cube.updateUV();
		manager.updateValues();		
	}

}

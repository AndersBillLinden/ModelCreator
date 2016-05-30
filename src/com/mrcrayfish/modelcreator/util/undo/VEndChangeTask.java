package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class VEndChangeTask extends UndoQueue.Task
{
	private double delta;
	private Element cube;
	private Face face;
	private ElementManager manager;
	private boolean oldAutoUVEnabled;

	public VEndChangeTask(double delta, Element cube, Face face, ElementManager manager)
	{
		this.delta = delta;
		this.cube = cube;
		this.face = face;
		this.manager = manager;
		oldAutoUVEnabled = face.isAutoUVEnabled();
	}
	
	@Override
	public void undo()
	{
		face.addTextureYEnd(-delta);
		cube.updateUV();
		manager.updateValues();
		face.setAutoUVEnabled(oldAutoUVEnabled);
	}

	@Override
	public void redo()
	{
		face.addTextureYEnd(delta);
		cube.updateUV();
		manager.updateValues();
		face.setAutoUVEnabled(false);
	}
}

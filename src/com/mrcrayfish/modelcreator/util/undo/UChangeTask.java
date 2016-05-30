package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class UChangeTask extends UndoQueue.Task
{
	private double delta;
	private Element cube;
	private Face face;
	private ElementManager manager;

	public UChangeTask(double delta, Element cube, Face face, ElementManager manager)
	{
		this.delta = delta;
		this.cube = cube;
		this.face = face;
		this.manager = manager;
	}
	
	@Override
	public void undo()
	{
		face.addTextureX(-delta);
		cube.updateUV();
		manager.updateValues();
	}

	@Override
	public void redo()
	{
		face.addTextureX(delta);
		cube.updateUV();
		manager.updateValues();		
	}
}

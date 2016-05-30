package com.mrcrayfish.modelcreator.util.undo;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class VChangeTask extends UndoQueue.Task
{
	private double delta;
	private Element cube;
	private Face face;
	private ElementManager manager;

	public VChangeTask(double delta, Element cube, Face face, ElementManager manager)
	{
		this.delta = delta;
		this.cube = cube;
		this.face = face;
		this.manager = manager;
	}
	
	@Override
	public void undo()
	{
		face.addTextureY(-delta);
		cube.updateUV();
		manager.updateValues();
	}

	@Override
	public void redo()
	{
		face.addTextureY(delta);
		cube.updateUV();
		manager.updateValues();		
	}

}

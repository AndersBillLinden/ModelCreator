package com.mrcrayfish.modelcreator.util.components;

import java.awt.Dimension;

import javax.swing.JPanel;

public class VGap extends JPanel
{
	public VGap(int height)
	{
		setPreferredSize(new Dimension(0, height));
	}
	
	public static VGap withhHeight(int height)
	{
		return new VGap(height);
	}
}

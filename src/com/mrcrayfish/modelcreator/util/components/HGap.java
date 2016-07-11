package com.mrcrayfish.modelcreator.util.components;

import java.awt.Dimension;

import javax.swing.JPanel;

public class HGap extends JPanel
{
	public HGap(int width)
	{
		setPreferredSize(new Dimension(width, 0));
	}
	
	public static HGap withhWidth(int width)
	{
		return new HGap(width);
	}	
}

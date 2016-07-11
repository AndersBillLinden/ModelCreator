package com.mrcrayfish.modelcreator.util.components;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MarginBox extends JPanel
{
	public MarginBox(Component component)
	{
		this.add(component);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
}

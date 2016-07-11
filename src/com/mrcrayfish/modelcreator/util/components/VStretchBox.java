package com.mrcrayfish.modelcreator.util.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class VStretchBox extends JPanel
{
	private Component component;

	public VStretchBox(Component component)
	{
		this.component = component;

		add(component);
		setLayout(null);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(-1, component.getPreferredSize().height); 
	}
	
	@Override
	public void setSize(Dimension d)
	{
		super.setSize(d);
		component.setSize(d);
	}
}

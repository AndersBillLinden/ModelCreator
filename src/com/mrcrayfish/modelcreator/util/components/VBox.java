package com.mrcrayfish.modelcreator.util.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class VBox extends JPanel
{
	int y = 0;
	int maxWidth = 0;

	public VBox(Component... components)
	{
		ArrayList<Component> stretchList = new ArrayList<Component>(); 
		setLayout(null);
		
		for (Component comp : components)
		{
			add(comp);
			comp.setLocation(new Point(0, y));
			
			Dimension size = comp.getPreferredSize();

			if (size.width > 0)
			{
				comp.setSize(size);
				
				if (size.width > maxWidth)
					maxWidth = size.width;
			}
			else
			{
				stretchList.add(comp);
			}

			y += size.height;
		}

		for (Component comp : stretchList)
		{
			Dimension size = comp.getPreferredSize();
			size.width = maxWidth;
			comp.setSize(size);
		}
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(maxWidth, y);
	}
}

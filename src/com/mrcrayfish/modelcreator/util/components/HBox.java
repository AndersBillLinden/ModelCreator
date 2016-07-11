package com.mrcrayfish.modelcreator.util.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class HBox extends JPanel
{
	int x = 0;
	int maxHeight = 0;

	public HBox(Component... components)
	{
		ArrayList<Component> stretchList = new ArrayList<Component>(); 		
		setLayout(null);

		for (Component comp : components)
		{
			add(comp);
			comp.setLocation(new Point(x, 0));
			Dimension size = comp.getPreferredSize();
			
			if (size.width > 0)
			{
				comp.setSize(size);
				
				if (size.height > maxHeight)
					maxHeight = size.height;
			}
			else
			{
				stretchList.add(comp);
			}

			x += size.width;
		}
		
		for (Component comp : stretchList)
		{
			Dimension size = comp.getPreferredSize();
			size.height = maxHeight;
			comp.setSize(size);
		}		
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(x, maxHeight);
	}
}

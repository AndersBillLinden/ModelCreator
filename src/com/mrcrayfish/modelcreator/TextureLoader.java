package com.mrcrayfish.modelcreator;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;

public class TextureLoader implements ITextureLoader
{

	@Override
	public Texture getTexture(String format, InputStream stream)
	{
		org.newdawn.slick.opengl.Texture result = null;
		try
		{
			result = org.newdawn.slick.opengl.TextureLoader.getTexture(format, stream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}

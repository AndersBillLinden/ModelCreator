package com.mrcrayfish.modelcreator;

import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;

public interface ITextureLoader
{
	public Texture getTexture(String format, InputStream stream);
}

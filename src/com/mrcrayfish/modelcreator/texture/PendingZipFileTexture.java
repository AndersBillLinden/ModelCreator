package com.mrcrayfish.modelcreator.texture;

import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;

import org.newdawn.slick.opengl.Texture;

public class PendingZipFileTexture implements IPendingTexture
{
	private String texture;
	private TextureCallback callback;
	private ForgeZipFile zipfile;
	private Map<String, String> textureMap;

	public PendingZipFileTexture(ForgeZipFile zipfile, String texture, Map<String, String> textureMap)
	{
		this.zipfile = zipfile;
		this.texture = texture;
		this.textureMap = textureMap;
	}
	
	public PendingZipFileTexture(ForgeZipFile zipfile, String texture, TextureCallback callback)
	{
		this.zipfile = zipfile;
		this.texture = texture;
		this.callback = callback;		
	}

	public void load()
	{
		try
		{
			boolean result = false;
			
			if (texture.startsWith("#"))
				texture = textureMap.get(texture.substring(1));
			
			ZipEntry textureEntry = new ZipEntry("assets/minecraft/textures/" + texture + ".png");

			String fileName = textureEntry.getName().replace(".png", "").replaceAll("\\d*$", "");
			Texture texture = TextureManager.getTexture(fileName);
			if (texture == null)
			{
				texture = zipfile.loadTexture(textureEntry);
				result = TextureManager.addToCache(zipfile, textureEntry, texture);
			}
			else
			{
				result = true;
			}

			if (callback != null)
				callback.callback(result, fileName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

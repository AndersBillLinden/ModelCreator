package com.mrcrayfish.modelcreator.tests;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.newdawn.slick.opengl.Texture;

import com.mrcrayfish.modelcreator.ForgeImporter;
import com.mrcrayfish.modelcreator.ITextureLoader;
import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.texture.ForgeZipFile;
import com.mrcrayfish.modelcreator.texture.IPendingTexture;
import com.mrcrayfish.modelcreator.util.components.ImportedModel;

import org.junit.Assert;

public class TestImportModel
{
	@Test
	public void test()
	{
		ArrayList<String> versions = ForgeImporter.GetAvailableMinecraftVersions();
		ForgeZipFile file = new ForgeZipFile(versions.get(0));
		ImportedModel[] models = file.getModelJsonFiles();
		FakeTextureLoader textureLoader = new FakeTextureLoader();

		ImportedModel fooModel = null;
		for(ImportedModel model : models)
		{
			if (model.name.equals("normal_torch"))
				fooModel = model;
		}
		
		FakeElementManager manager = new FakeElementManager();
		ForgeImporter importer = new ForgeImporter(manager, fooModel);
		
		importer.importFromJSON();
		
		Assert.assertEquals(3, manager.getElementCount());
		
		Assert.assertEquals(2, manager.textures.size());
		manager.textures.get(0).load(textureLoader);
		//Assert.assertEquals(expected, texture.);
		
		for (Element e: manager.getAllElements())
		{
			Face[] faces = e.getAllFaces();
			Assert.assertEquals(6, faces.length);
			
			for(Face f: faces)
			{
				//Assert.assertEquals("torch", f.getTextureName());
			}
		}
	}

	private static class FakeElementManager implements ElementManager
	{
		public String log = "";
		@Override
		public Element getSelectedElement()
		{
			return null;
		}

		@Override
		public void setSelectedElement(int pos)
		{
		}

		@Override
		public List<Element> getAllElements()
		{
			return elements;
		}

		@Override
		public Element getElement(int index)
		{
			return elements.get(0);
		}

		@Override
		public int getElementCount()
		{
			return elements.size();
		}

		@Override
		public void clearElements()
		{
			elements.clear();
			log += "Ce";
		}

		@Override
		public void updateName()
		{
			log += "Un";
		}

		@Override
		public void updateValues()
		{
			log += "Uv";
		}

		private ArrayList<IPendingTexture> textures = new ArrayList<IPendingTexture>();

		@Override
		public void addPendingTexture(IPendingTexture texture)
		{
			textures.add(texture);
			log += "Apt";
		}

		@Override
		public boolean getAmbientOcc()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setAmbientOcc(boolean occ)
		{
			// TODO Auto-generated method stub
			
		}
		
		ArrayList<Element> elements = new ArrayList<Element>();

		@Override
		public void addElement(Element e)
		{
			elements.add(e);
			log += "Ae";
		}

		@Override
		public void setParticle(String texture)
		{
			// TODO Auto-generated method stub
			log += "Sp(" + texture + ")";
		}

		@Override
		public String getParticle()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void reset()
		{
			log += "R";			
		}		
	}
	
	private static class FakeTextureLoader implements ITextureLoader
	{
		@Override
		public Texture getTexture(String format, InputStream stream)
		{
			return new FakeTexture();
		}
	}
	
	private static class FakeTexture implements Texture
	{
		@Override
		public void bind()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public float getHeight()
		{
			return 16;
		}

		@Override
		public int getImageHeight()
		{
			return 16;
		}

		@Override
		public int getImageWidth()
		{
			return 16;
		}

		@Override
		public byte[] getTextureData()
		{
			return null;
		}

		@Override
		public int getTextureHeight()
		{
			return 16;
		}

		@Override
		public int getTextureID()
		{
			return 0;
		}

		@Override
		public String getTextureRef()
		{
			return "faketexture";
		}

		@Override
		public int getTextureWidth()
		{
			return 16;
		}

		@Override
		public float getWidth()
		{
			return 16;
		}

		@Override
		public boolean hasAlpha()
		{
			return false;
		}

		@Override
		public void release()
		{
		}

		@Override
		public void setTextureFilter(int arg0)
		{
		}
	}
}

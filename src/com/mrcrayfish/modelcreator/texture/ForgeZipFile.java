package com.mrcrayfish.modelcreator.texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.mrcrayfish.modelcreator.ForgeImporter.BlockState;
import com.mrcrayfish.modelcreator.ITextureLoader;
import com.mrcrayfish.modelcreator.util.components.ImportedModel;

public class ForgeZipFile
{
	private File jar;	

	public ForgeZipFile(String version)
	{
		File homeDir = new File(System.getProperty("user.home"));
		jar = new File(homeDir, ".gradle/caches/minecraft/net/minecraft/minecraft/"
			+ version + "\\minecraft-" + version + ".jar");
	}
	
	public String openJsonFile(String filename)
	{
		return null;
	}

	public String openModel(ImportedModel model)
	{
		StringBuilder out = new StringBuilder();
		
		try
		{
			ZipFile zipFile = new ZipFile(jar);
			
			InputStream stream = zipFile.getInputStream(model.getEntry());

			int bufferSize = 4096;
			final char[] buffer = new char[bufferSize];
			try (Reader in = new InputStreamReader(stream, "UTF-8"))
			{
		        for (;;)
		        {
		            int rsz = in.read(buffer, 0, buffer.length);
		            if (rsz < 0)
		                break;
		            out.append(buffer, 0, rsz);
		        }
		    }
		    catch (UnsupportedEncodingException ex)
			{
		    }
			
			zipFile.close();
		}
		catch (IOException e)
		{
		}
		
		return out.toString();		
	}
	
	public Texture loadTexture(ZipEntry image, ITextureLoader loader)
	{
		Texture result = null;

		try
		{
			ZipFile zipFile = new ZipFile(jar);
			InputStream stream = zipFile.getInputStream(image);
			
			if (stream == null)
			{
				System.out.println("stream == null");
			}
			result = loader.getTexture("PNG", stream);
			zipFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public InputStream getInputStream(ZipEntry entry) throws IOException
	{
		ZipFile zipFile = new ZipFile(jar);
		
		return zipFile.getInputStream(entry);
	}

	public ImportedModel[] getModelJsonFiles()
	{
		ArrayList<ImportedModel> result = new ArrayList<ImportedModel>();
		ZipFile zipFile;
		try
		{
			zipFile = new ZipFile(jar);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				
				String name = entry.getName();
				if (ImportedModel.blockJsonPattern.matcher(name).find())
				{
					result.add(new ImportedModel(this, entry));
				}
			}			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		ImportedModel[] arr = result.toArray(new ImportedModel[0]);
		Arrays.sort(arr);

		return arr;
	}
}

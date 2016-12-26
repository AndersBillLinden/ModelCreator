package com.mrcrayfish.modelcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.forge.ArchiveModel;
import com.mrcrayfish.modelcreator.texture.PendingZipFileTexture;
import com.mrcrayfish.modelcreator.util.components.ImportedModel;
import com.mrcrayfish.modelcreator.util.components.ImportedModel.BlockState;

public class ForgeImporter
{
	private Map<String, String> textureMap = new HashMap<String, String>();
	private String[] faceNames = { "north", "east", "south", "west", "up", "down" };

	// Model Variables
	private ElementManager manager;

	private boolean ignoreTextures = false;
	private ImportedModel model;

	public ForgeImporter(ElementManager manager, ImportedModel model)
	{
		this.manager = manager;
		this.model = model;
	}

	public void ignoreTextureLoading()
	{
		this.ignoreTextures = true;
	}

	public ArchiveModel importFromJSON()
	{
		ArchiveModel result = null;
		manager.clearElements();
		
		try
		{
			InputStream stream = model.getFile().getInputStream(model.getEntry());
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			result = readComponents(reader, manager);
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}

	public static BlockState[] getBlockStates(ImportedModel model)
	{
		String filename = "assets/minecraft/blockstates/" + model.name + ".json";

		try
		{
			ZipEntry entry = new ZipEntry(filename);
			InputStream stream = model.getFile().getInputStream(entry);
			
			if (stream == null)
				return null;
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			
			JsonParser parser = new JsonParser();
			JsonElement read = parser.parse(reader);
						
			if (read.isJsonObject())
			{
				JsonObject obj = read.getAsJsonObject();

				// load variants
				if (obj.has("variants") && obj.get("variants").isJsonObject())
				{
					JsonObject variants = obj.get("variants").getAsJsonObject();
					
					Set<Entry<String, JsonElement>> keys = variants.entrySet();
					
					List<BlockState> result = new ArrayList<BlockState>();
					
					for(Entry<String, JsonElement> k: keys)
					{
						String key = k.getKey();
						result.add(new BlockState(key));
					}
					
					return result.toArray(new BlockState[0]);
				}
			}
			else
				return null;
			
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
		
	private void readComponents(BufferedReader reader, ElementManager manager) throws IOException
	{
		JsonParser parser = new JsonParser();
		JsonElement read = parser.parse(reader);

		if (read.isJsonObject())
		{
			JsonObject obj = read.getAsJsonObject();

			// load elements
			if (obj.has("elements") && obj.get("elements").isJsonArray())
			{
				JsonArray elements = obj.get("elements").getAsJsonArray();

				for (int i = 0; i < elements.size(); i++)
				{
					if (elements.get(i).isJsonObject())
					{
						readElement(elements.get(i).getAsJsonObject(), manager);
					}
				}
			}

			manager.setAmbientOcc(true);
			if (obj.has("ambientocclusion") && obj.get("ambientocclusion").isJsonPrimitive())
			{
				manager.setAmbientOcc(obj.get("ambientocclusion").getAsBoolean());
			}
			
			loadTextures(obj);

			if (obj.has("parent") && obj.get("parent").isJsonPrimitive())
			{
				String parent = obj.get("parent").getAsString();
				
				ZipEntry parentEntry = new ZipEntry("assets/minecraft/models/" + parent + ".json");

				// Load Parent
				InputStream parentStream = model.getFile().getInputStream(parentEntry);
				BufferedReader parentReader = new BufferedReader(new InputStreamReader(parentStream, "UTF-8"));
				readComponents(parentReader, manager);
			}			
		}
	}

	private void loadTextures(JsonObject obj)
	{
		if (obj.has("textures") && obj.get("textures").isJsonObject())
		{
			JsonObject textures = obj.get("textures").getAsJsonObject();

			for (Entry<String, JsonElement> entry : textures.entrySet())
			{
				if (entry.getValue().isJsonPrimitive())
				{
					String texture = entry.getValue().getAsString();

					if (texture.startsWith("#"))
					{
						texture = textureMap.get(texture);
					}
					else
					{
						if (entry.getKey().equals("particle"))
						{
							manager.setParticle(texture);
						}
						else
						{
							textureMap.put(texture, entry.getKey());
						}
					}
					manager.addPendingTexture(new PendingZipFileTexture(model.getFile(), texture, textureMap));
				}
			}
		}
	}

	public static ArrayList<String> GetAvailableMinecraftVersions()
	{
		ArrayList<String> result = new ArrayList<String>();

		File homeDir = new File(System.getProperty("user.home"));
		File dir = new File(homeDir, ".gradle/caches/minecraft/net/minecraft/minecraft");

		File[] files = dir.listFiles();

		for (File file : files)
		{
			result.add(file.getName());
		}

		return result;
	}
		
	private void readElement(JsonObject obj, ElementManager manager)
	{
		
		
	}

	private void readFace(JsonObject obj, String name, Element element)
	{
		Face face = null;
		for (Face f : element.getAllFaces())
		{
			if (f.getSide() == Face.getFaceSide(name))
			{
				face = f;
			}
		}

		if (face != null)
		{
			face.setEnabled(true);

			// automatically set uv if not specified
			face.setEndU(element.getFaceDimension(face.getSide()).getWidth());
			face.setEndV(element.getFaceDimension(face.getSide()).getHeight());
			face.setAutoUVEnabled(true);

			if (obj.has("uv") && obj.get("uv").isJsonArray())
			{
				JsonArray uv = obj.get("uv").getAsJsonArray();

				double uStart = uv.get(0).getAsDouble();
				double vStart = uv.get(1).getAsDouble();
				double uEnd = uv.get(2).getAsDouble();
				double vEnd = uv.get(3).getAsDouble();

				face.setStartU(uStart);
				face.setStartV(vStart);
				face.setEndU(uEnd);
				face.setEndV(vEnd);
				face.setAutoUVEnabled(false);
			}

			if (obj.has("texture") && obj.get("texture").isJsonPrimitive())
			{
				String loc = obj.get("texture").getAsString().replace("#", "");

				if (textureMap.containsKey(loc))
				{
					String tloc = textureMap.get(loc);
					String location = tloc.substring(0, tloc.lastIndexOf('/') + 1);
					String tname = tloc.replace(location, "");

					face.setTextureLocation(location);
					face.setTexture(tname);
				}
			}

			if (obj.has("rotation") && obj.get("rotation").isJsonPrimitive())
			{
				face.setRotation((int) obj.get("rotation").getAsDouble() / 90);
			}

			// TODO cullface with different direction than face,tintindex
			if (obj.has("cullface") && obj.get("cullface").isJsonPrimitive())
			{
				String cullface = obj.get("cullface").getAsString();

				if (cullface.equals(Face.getFaceName(face.getSide())))
				{
					face.setCullface(true);
				}
			}
		}
	}
}

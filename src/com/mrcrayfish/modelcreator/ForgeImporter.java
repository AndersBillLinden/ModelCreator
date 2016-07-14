package com.mrcrayfish.modelcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.texture.PendingZipFileTexture;
import com.mrcrayfish.modelcreator.util.components.ImportedModel;
import com.mrcrayfish.modelcreator.util.components.Table;

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

	public void importFromJSON()
	{
		manager.clearElements();
		
		try
		{
			InputStream stream = model.getFile().getInputStream(model.getEntry());
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			readComponents(reader, manager);
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
		String name = "Element";
		JsonArray from = null;
		JsonArray to = null;

		if (obj.has("name") && obj.get("name").isJsonPrimitive())
		{
			name = obj.get("name").getAsString();
		}
		else if (obj.has("comment") && obj.get("comment").isJsonPrimitive())
		{
			name = obj.get("comment").getAsString();
		}
		if (obj.has("from") && obj.get("from").isJsonArray())
		{
			from = obj.get("from").getAsJsonArray();
		}
		if (obj.has("to") && obj.get("to").isJsonArray())
		{
			to = obj.get("to").getAsJsonArray();
		}

		if (from != null && to != null)
		{
			double x = from.get(0).getAsDouble();
			double y = from.get(1).getAsDouble();
			double z = from.get(2).getAsDouble();

			double w = to.get(0).getAsDouble() - x;
			double h = to.get(1).getAsDouble() - y;
			double d = to.get(2).getAsDouble() - z;

			Element element = new Element(w, h, d);
			element.setName(name);
			element.setStartX(x);
			element.setStartY(y);
			element.setStartZ(z);

			if (obj.has("rotation") && obj.get("rotation").isJsonObject())
			{
				JsonObject rot = obj.get("rotation").getAsJsonObject();

				if (rot.has("origin") && rot.get("origin").isJsonArray())
				{
					JsonArray origin = rot.get("origin").getAsJsonArray();

					double ox = origin.get(0).getAsDouble();
					double oy = origin.get(1).getAsDouble();
					double oz = origin.get(2).getAsDouble();

					element.setOriginX(ox);
					element.setOriginY(oy);
					element.setOriginZ(oz);
				}

				if (rot.has("axis") && rot.get("axis").isJsonPrimitive())
				{
					element.setPrevAxis(Element.parseAxisString(rot.get("axis").getAsString()));
				}

				if (rot.has("angle") && rot.get("angle").isJsonPrimitive())
				{
					element.setRotation(rot.get("angle").getAsDouble());
				}

				if (rot.has("rescale") && rot.get("rescale").isJsonPrimitive())
				{
					element.setRescale(rot.get("rescale").getAsBoolean());
				}
			}

			element.setShade(true);
			if (obj.has("shade") && obj.get("shade").isJsonPrimitive())
			{
				element.setShade(obj.get("shade").getAsBoolean());
			}

			for (Face face : element.getAllFaces())
			{
				face.setEnabled(false);
			}

			if (obj.has("faces") && obj.get("faces").isJsonObject())
			{
				JsonObject faces = obj.get("faces").getAsJsonObject();

				for (String faceName : faceNames)
				{
					if (faces.has(faceName) && faces.get(faceName).isJsonObject())
					{
						readFace(faces.get(faceName).getAsJsonObject(), faceName, element);
					}
				}
			}

			manager.addElement(element);
		}
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

	public static class BlockState
	{
		@Table.Header(name = "Block state")
		public String name;

		public BlockState(String name)
		{
			this.name = name;
		}
	}
}

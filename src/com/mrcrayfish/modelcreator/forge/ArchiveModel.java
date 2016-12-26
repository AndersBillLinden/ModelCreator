package com.mrcrayfish.modelcreator.forge;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.Face;

public class ArchiveModel
{
	public ArchiveModel parent;
	public Element[] elements;
	
	public static class Element
	{
		private JsonObject obj;
		public static Element fromJsonObject(JsonObject json)
		{
			this.obj = obj;
		}
		
		public String getName()
		{
			if (obj.has("name") && obj.get("name").isJsonPrimitive())
				return obj.get("name").getAsString();
			else if (obj.has("comment") && obj.get("comment").isJsonPrimitive())
				return obj.get("comment").getAsString();
			else
				return "Element";
		}
		
			JsonArray from = null;
			JsonArray to = null;

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
	}
}

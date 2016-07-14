package com.mrcrayfish.modelcreator.util.components;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import com.mrcrayfish.modelcreator.ForgeImporter.BlockState;
import com.mrcrayfish.modelcreator.texture.ForgeZipFile;

public class ImportedModel implements Comparable<ImportedModel> 
{
	public static Pattern blockJsonPattern
		= Pattern.compile("assets/minecraft/models/block/([^\\.]+)\\.json");
	
	private ForgeZipFile file;
	private ZipEntry entry;
	public ImportedModel()
	{
	}
	
	public ImportedModel(ForgeZipFile file, ZipEntry entry)
	{
		this.file = file;
		this.entry = entry;
		
		name = entry.getName();
		Matcher match = blockJsonPattern.matcher(name);
		match.find();
		this.name = match.group(1);
	}
	
	@Table.Header(name = "Model name")
	public String name;
	
	private String filename;
	
	@Override
	public int compareTo(ImportedModel other)
	{
		return name.compareTo(other.name);
	}

	public ForgeZipFile getFile()
	{
		return file;
	}
	
	public ZipEntry getEntry()
	{
		return entry;
	}

	public BlockState[] getBlockStates()
	{
		ArrayList<BlockState> result = new ArrayList<BlockState>();
		String filename = name = "assets/minecraft/blockstates/" + entry.getName() + ".json";

		return result.toArray(new BlockState[0]);
	}
}

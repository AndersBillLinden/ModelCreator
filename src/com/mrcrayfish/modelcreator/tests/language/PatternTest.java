package com.mrcrayfish.modelcreator.tests.language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class PatternTest extends TestCase
{
	@Test
	public void testMatchMinecraftPattern()
	{
		Pattern pattern = Pattern.compile("assets/minecraft/models/block/([^\\.]+)\\.json");
		
		String input = "assets/minecraft/models/block/blue_stained_glass_pane_side.json";
		
		Matcher matcher = pattern.matcher(input);
		Assert.assertTrue(matcher.find());
	}
}

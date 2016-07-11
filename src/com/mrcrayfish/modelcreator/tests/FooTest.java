package com.mrcrayfish.modelcreator.tests;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class FooTest extends TestCase
{
	@Test
	public void testFoo()
	{
		int a = 3;
		int b = 5;

		Assert.assertNotEquals(a, b);
	}
}

package nz.silence.cerberus.test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.silence.cerberus.attribute.GroupAttribute;
import nz.silence.cerberus.attribute.NationalityAttribute;
import nz.silence.cerberus.core.Attribute;

class TestAttribute {

	@Test
	public void testGroupAttributeEquals() {
		Attribute a = new GroupAttribute("A");
		Attribute b = new GroupAttribute("A");
		assertEquals(a, b);
	}
	
	@Test
	public void testGroupAttributeNotEquals() {
		Attribute a = new GroupAttribute("A");
		Attribute b = new GroupAttribute("B");
		assertFalse(a.equals(b));
	}

	@Test
	public void testCorrectNatInit() {
		try {
			new NationalityAttribute("NZ");
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test
	public void testIncorrectNatInit() {
		assertThrows(IllegalArgumentException.class,
				() -> new NationalityAttribute("XXZ"),
				"Expected non-matching country code to raise an exception, but it didn't");
	}
}

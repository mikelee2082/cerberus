package nz.silence.cerberus.test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nz.silence.cerberus.attribute.GroupAttribute;
import nz.silence.cerberus.attribute.NationalityAttribute;
import nz.silence.cerberus.attribute.OrgAttribute;
import nz.silence.cerberus.control.SimpleAccessControl;
import nz.silence.cerberus.core.Principal;
import nz.silence.cerberus.principal.SimplePrincipal;

class TestAccessControl {

	@Test
	public void testSimpleInit() {
		new SimpleAccessControl.SimpleAccessControlBuilder().build();
	}
	
	@Test
	public void testBuild() {
		SimpleAccessControl ac = new SimpleAccessControl.SimpleAccessControlBuilder()
				.allowNationality(new NationalityAttribute("NZ"))
				.allowNationality(new NationalityAttribute("HK"))
				.allowOrg(new OrgAttribute("RUPTURE FARMS"))
				.andGroup(new GroupAttribute("APPLE_PICKER"))
				.build();
		assertEquals(ac.getAllowedNationality().size(), 2);
		assertEquals(ac.getAllowedOrganisations().size(), 1);
		assertEquals(ac.getAndGroups().size(),1);
		assertEquals(ac.getOrGroups().size(), 0);
		assertTrue(ac.getAllowedNationality().contains(new NationalityAttribute("NZ")));
		assertTrue(ac.getAllowedOrganisations().contains(new OrgAttribute("RUPTURE FARMS")));
	}
	
	@Test
	public void testAllowedAccess() {
		SimpleAccessControl ac = new SimpleAccessControl.SimpleAccessControlBuilder()
				.allowNationality(new NationalityAttribute("NZ"))
				.allowOrg(new OrgAttribute("RUPTURE_FARMS"))
				.andGroup(new GroupAttribute("APPLE_PICKER"))
				.orGroup(new GroupAttribute("PEAR_EATER"))
				.build();
		Principal p = constructPrincipal();
		assertTrue(ac.isAllowed(p));
	}
	
	@Test
	public void testNotAllowedAccess() {
		SimpleAccessControl ac = new SimpleAccessControl.SimpleAccessControlBuilder()
				.allowNationality(new NationalityAttribute("NZ"))
				.allowOrg(new OrgAttribute("RUPTURE_FARMS"))
				.andGroup(new GroupAttribute("APPLE_PICKER"))
				.andGroup(new GroupAttribute("STRAWBERRY_EATER"))
				.orGroup(new GroupAttribute("PEAR_EATER"))
				.build();
		Principal p = constructPrincipal();
		assertFalse(ac.isAllowed(p));
	}
	
	@Test
	public void serializeNotAllowed() throws IOException, ClassNotFoundException {
		SimpleAccessControl ac = new SimpleAccessControl.SimpleAccessControlBuilder()
				.allowNationality(new NationalityAttribute("NZ"))
				.allowOrg(new OrgAttribute("RUPTURE_FARMS"))
				.andGroup(new GroupAttribute("APPLE_PICKER"))
				.andGroup(new GroupAttribute("STRAWBERRY_EATER"))
				.orGroup(new GroupAttribute("PEAR_EATER"))
				.build();
		Principal p = constructPrincipal();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(buffer);
		oos.writeObject(ac);
		oos.close();
		byte[] barr = buffer.toByteArray();
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr));
		SimpleAccessControl bc = (SimpleAccessControl) ois.readObject();
		assertFalse(bc.isAllowed(p));
		assertNotEquals(ac, bc);
	}
	
	private Principal constructPrincipal() {
		Set<GroupAttribute> groups = new HashSet<>();
		groups.add(new GroupAttribute("APPLE_PICKER"));
		groups.add(new GroupAttribute("APPLE_EATER"));
		groups.add(new GroupAttribute("PEAR_EATER"));
		NationalityAttribute nationality = new NationalityAttribute("NZ");
		OrgAttribute org = new OrgAttribute("RUPTURE_FARMS");
		String username = "superuser";
		return new SimplePrincipal(username, nationality, org, groups);
	}

}

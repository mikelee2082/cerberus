package nz.silence.cerberus.core;

import java.util.Set;

import nz.silence.cerberus.attribute.GroupAttribute;
import nz.silence.cerberus.attribute.NationalityAttribute;
import nz.silence.cerberus.attribute.OrgAttribute;

public interface Principal {
	
	public NationalityAttribute getNationality();
	public OrgAttribute getOrganisation();
	public Set<GroupAttribute> getGroups();
	public String getUserName();

}

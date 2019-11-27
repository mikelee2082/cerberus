package nz.silence.cerberus.principal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.silence.cerberus.attribute.GroupAttribute;
import nz.silence.cerberus.attribute.NationalityAttribute;
import nz.silence.cerberus.attribute.OrgAttribute;
import nz.silence.cerberus.core.Principal;

public class SimplePrincipal implements Principal {
	
	private NationalityAttribute nationality;
	private OrgAttribute organisation;
	private Set<GroupAttribute> groups;
	private final String username;
	
	public SimplePrincipal(String username, NationalityAttribute nationality, OrgAttribute organisation, Collection<? extends GroupAttribute> groups) {
		this.nationality = nationality;
		this.organisation = organisation;
		this.groups = new HashSet<>(groups);
		this.username = username;
	}

	@Override
	public NationalityAttribute getNationality() {
		return nationality;
	}

	@Override
	public OrgAttribute getOrganisation() {
		return organisation;
	}

	@Override
	public Set<GroupAttribute> getGroups() {
		return Collections.unmodifiableSet(groups);
	}

	@Override
	public String getUserName() {
		return username;
	}

}

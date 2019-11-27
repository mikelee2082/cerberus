package nz.silence.cerberus.control;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.silence.cerberus.attribute.*;
import nz.silence.cerberus.core.AccessControl;
import nz.silence.cerberus.core.Principal;

public class SimpleAccessControl implements AccessControl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1336158379012299034L;
	private final Set<NationalityAttribute> allowedNationality;
	private final Set<OrgAttribute> allowedOrganisations;
	private final Set<GroupAttribute> orGroups;
	private final Set<GroupAttribute> andGroups;
	
	private SimpleAccessControl(Set<NationalityAttribute> allowedNationality,
			Set<OrgAttribute> allowedOrganisations,
			Set<GroupAttribute> orGroups,
			Set<GroupAttribute> andGroups) {
		this.allowedNationality = allowedNationality;
		this.allowedOrganisations = allowedOrganisations;
		this.orGroups = orGroups;
		this.andGroups = andGroups;
	}
	
	public boolean isAllowed(Principal principal) {
		if (!(allowedNationality.contains(principal.getNationality()))) return false;
		if (!(allowedOrganisations.contains(principal.getOrganisation()))) return false;
		if (Collections.disjoint(orGroups, principal.getGroups())) return false;
		if (!(principal.getGroups().containsAll(andGroups))) return false;
		return true;
	}

	public Set<NationalityAttribute> getAllowedNationality() {
		return allowedNationality;
	}

	public Set<OrgAttribute> getAllowedOrganisations() {
		return allowedOrganisations;
	}

	public Set<GroupAttribute> getOrGroups() {
		return orGroups;
	}

	public Set<GroupAttribute> getAndGroups() {
		return andGroups;
	}
	
	public static class SimpleAccessControlBuilder {
		
		private Set<NationalityAttribute> allowedNationality;
		private Set<OrgAttribute> allowedOrganisations;
		private Set<GroupAttribute> orGroups;
		private Set<GroupAttribute> andGroups;
		
		public SimpleAccessControlBuilder() {
			this.allowedNationality = new HashSet<>();
			this.allowedOrganisations = new HashSet<>();
			this.orGroups = new HashSet<>();
			this.andGroups = new HashSet<>();
		}
		
		public SimpleAccessControlBuilder allowNationality(NationalityAttribute attribute) {
			this.allowedNationality.add(attribute);
			return this;
		}
		
		public SimpleAccessControlBuilder allowNationality(Collection<? extends NationalityAttribute> attribute) {
			this.allowedNationality.addAll(attribute);
			return this;
		}
		
		public SimpleAccessControlBuilder allowOrg(OrgAttribute attribute) {
			this.allowedOrganisations.add(attribute);
			return this;
		}
		
		public SimpleAccessControlBuilder allowOrg(Collection<? extends OrgAttribute> attribute) {
			this.allowedOrganisations.addAll(attribute);
			return this;
		}
		
		public SimpleAccessControlBuilder orGroup(GroupAttribute attribute) {
			this.orGroups.add(attribute);
			return this;
		}
		
		public SimpleAccessControlBuilder orGroup(Collection<? extends GroupAttribute> attribute) {
			this.orGroups.addAll(attribute);
			return this;
		}
		
		public SimpleAccessControlBuilder andGroup(GroupAttribute attribute) {
			this.andGroups.add(attribute);
			return this;
		}

		public SimpleAccessControlBuilder andGroup(Collection<? extends GroupAttribute> attribute) {
			this.andGroups.addAll(attribute);
			return this;
		}
		
		public SimpleAccessControl build() {
			if (!Collections.disjoint(orGroups, andGroups)) throw new IllegalArgumentException("OrGroups and AndGroups must be disjoint");
			return new SimpleAccessControl(allowedNationality, allowedOrganisations, orGroups, andGroups);
		}
		
	}

}

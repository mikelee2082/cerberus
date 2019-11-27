package nz.silence.cerberus.attribute;

import nz.silence.cerberus.core.Attribute;

public class GroupAttribute implements Attribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5423832833105041797L;
	private final String name;
	
	public GroupAttribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			GroupAttribute o = (GroupAttribute)other;
			return o.getName().equals(name);
		}
		catch (ClassCastException cce) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
}

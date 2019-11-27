package nz.silence.cerberus.attribute;

import nz.silence.cerberus.core.Attribute;

public class OrgAttribute implements Attribute {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8779663559432704886L;
	private final String name;
	
	public OrgAttribute(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof OrgAttribute)) return false;
		OrgAttribute o = (OrgAttribute)other;
		return o.getName().equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String getName() {
		return name;
	}

}

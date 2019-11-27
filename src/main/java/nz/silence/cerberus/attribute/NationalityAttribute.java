package nz.silence.cerberus.attribute;

import com.neovisionaries.i18n.CountryCode;

import nz.silence.cerberus.core.Attribute;

public class NationalityAttribute implements Attribute {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -484182616216219205L;
	private final CountryCode cc;
	
	public NationalityAttribute(String digraph) {
		cc = CountryCode.getByCode(digraph);
		if (cc == null) {
			throw new IllegalArgumentException(digraph + " is not a valid country code");
			
		}
	}
	
	public String getDigraph() {
		return cc.getAlpha2();
	}
	
	public String getTrigraph() {
		return cc.getAlpha3();
	}
	
	public CountryCode getCountryCode() {
		return cc;
	}
	
	public String getName() {
		return getDigraph();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof NationalityAttribute)) return false;
		NationalityAttribute o = (NationalityAttribute) other;
		return o.getCountryCode() == cc;
	}
	
	@Override
	public int hashCode() {
		return cc.hashCode();
	}

}

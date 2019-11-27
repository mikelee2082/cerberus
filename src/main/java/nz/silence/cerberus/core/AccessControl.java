package nz.silence.cerberus.core;

import java.io.Serializable;

public interface AccessControl extends Serializable {
	
	public boolean isAllowed(Principal principal);

}

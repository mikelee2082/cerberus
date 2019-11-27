package nz.silence.cerberus.example;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import nz.silence.cerberus.attribute.GroupAttribute;
import nz.silence.cerberus.attribute.NationalityAttribute;
import nz.silence.cerberus.attribute.OrgAttribute;
import nz.silence.cerberus.control.SimpleAccessControl;
import nz.silence.cerberus.core.AccessControl;
import nz.silence.cerberus.core.Principal;
import nz.silence.cerberus.principal.SimplePrincipal;

public class AccessControlExample {
	
	private static Principal createPrincipal(String username) {
		NationalityAttribute nat = new NationalityAttribute("NZ");
		OrgAttribute org = new OrgAttribute("POLICE");
		Set<GroupAttribute> groups = new HashSet<>();
		groups.add(new GroupAttribute("READER"));
		return new SimplePrincipal(username,nat,org,groups);
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter your username: ");
		Principal p = createPrincipal(scan.nextLine());
		scan.close();
		System.out.println();
		AccessControl ac = new SimpleAccessControl.SimpleAccessControlBuilder()
				.allowNationality(new NationalityAttribute("NZ"))
				.allowOrg(new OrgAttribute("POLICE"))
				.allowOrg(new OrgAttribute("CUSTOMS"))
				.orGroup(new GroupAttribute("READER"))
				.orGroup(new GroupAttribute("WRITER"))
				.build();
		if (ac.isAllowed(p)) {
			System.out.println("Access granted for user: " + p.getUserName());
		} else {
			System.out.println("Access denied for user: " + p.getUserName());
		}
	}

}

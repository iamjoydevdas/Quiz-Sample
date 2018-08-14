package com.devoteam.dls.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * <p>This enum RoleType defined the user role and administrator role for the web
 * application learning service Devoteam Munich.</p>
 * 
 * @author Devoteam Munich,Germanys, work student (Besmir Beka, Bastien
 *         Thibaud).
 * @version 1.0.
 * @since 06.08.2018.
 * 
 */ 
public enum RoleType {

	ROLE_ADMIN, ROLE_USER;

	private static final Logger LOG = LogManager.getLogger(RoleType.class);

	/**
	 * <p>Compares if the types of the enum RoleType is equal to the parameter input.</p>
	 * 
	 * @param type - the user role in the web application
	 * @return if successfully ROLE_ADMIN | ROLE_USER else null
	 */
	public static RoleType findByType(String type) {
		for (RoleType role : values()) {
			LOG.info("Method findByType invoked  {}", type);
			if (role.name().equals(type)) {
				LOG.info("Type and RoleType equal");
				return role;
			}
		}
		LOG.info("Type and RoleType not equal");
		return null;
	}
}

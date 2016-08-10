/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 4, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.model;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceType.
 */
@Component
public class ResourceType {
	
	/** The type id. */
	private int typeId;
	
	/** The resource name. */
	private String resourceName;
	
	/**
	 * Gets the type id.
	 *
	 * @return the type id
	 */
	public int getTypeId() {
		return typeId;
	}
	
	/**
	 * Sets the type id.
	 *
	 * @param typeId the new type id
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * Gets the resource name.
	 *
	 * @return the resource name
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * Sets the resource name.
	 *
	 * @param resourceName the new resource name
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	
}

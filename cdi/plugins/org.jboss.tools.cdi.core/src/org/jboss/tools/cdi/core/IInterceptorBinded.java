/******************************************************************************* 
 * Copyright (c) 2010 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.tools.cdi.core;

import java.util.Collection;

/**
 * Represents an element which can have interceptor bindings.
 * 
 * @author Alexey Kazakov
 */
public interface IInterceptorBinded {

	/**
	 * Obtains the interceptor binding declarations of the class bean or method or stereotype or interceptor binding type.
	 * This set includes bindings declared in this java class bean or method or stereotype or interceptor binding type.
	 * Use getInterceptorBindings() to get all bindings (i.g. from Stereotypes)   
	 * 
	 * @return the set of interceptor binding declarations
	 */
	Collection<IInterceptorBindingDeclaration> getInterceptorBindingDeclarations(boolean includeInherited);

	/**
	 * Obtains the interceptor bindings of the bean class or method or stereotype or interceptor binding type.
	 * 
	 * @return the set of interceptor bindings
	 */
	Collection<IInterceptorBinding> getInterceptorBindings();
}
/*
 *    Copyright 2013 Ebrahim Aharpour
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 *   Partially sponsored by Smile B.V
 */
package net.sourceforge.mavenhippo.gen.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.SupperClassHandler;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.Constants;
import net.sourceforge.mavenhippo.utils.NamespaceUtils;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoDocument;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultSupperClassHandler extends SupperClassHandler {

	public DefaultSupperClassHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject, ClassLoader classLoader, Set<String> namespaces) {
		super(beansOnClassPath, beansInProject, classLoader, namespaces);
	}

	@Override
	public ClassReference getSupperClass(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
			String packageName) {
		ClassReference result = null;
		List<String> supertypes = contentTypeBean.getSupertypes();
		result = extendsGeneratedBean(packageName, supertypes);

		if (result == null) {
			result = extendsExistingBeans(result, supertypes);
		}
		if (result == null) {
			result = new ClassReference(HippoDocument.class);
		}
		importRegistry.register(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	private ClassReference extendsExistingBeans(ClassReference result, List<String> supertypes) {
		SortedSet<Class<? extends HippoBean>> supperClasses = new TreeSet<Class<? extends HippoBean>>(
				classExtensionComparator);
		for (String superType : supertypes) {
			if (Constants.NodeType.HIPPO_COMPOUND.equals(superType)) {
				Class<HippoCompound> hippoCompoundClass = HippoCompound.class;
				supperClasses.add(hippoCompoundClass);
			} else if (beansOnClassPath.containsKey(superType)) {
				HippoBeanClass hippoBeanClass = beansOnClassPath.get(superType);
				Class<?> clazz = getClass(hippoBeanClass);
				supperClasses.add((Class<? extends HippoBean>) clazz);
			}
		}
		if (supperClasses.size() > 0) {
			result = new ClassReference(supperClasses.last());
		}
		return result;
	}

	private ClassReference extendsGeneratedBean(String packageName, List<String> supertypes) {
		ClassReference result = null;
		for (String superType : supertypes) {
			String ns = NamespaceUtils.getNamespace(superType);
			if (StringUtils.isNotBlank(ns) && namespaces.contains(ns)) {
				result = new ClassReference(getClassName(packageName, superType));
				break;
			}
		}
		return result;
	}

	private String getClassName(String packageName, String superType) {
		String result;
		if (StringUtils.isNotBlank(packageName)) {
			result = packageName + Constants.Language.PACKAGE_SEPARATOR + classNameHandler.getClassName(superType);
		} else {
			result = classNameHandler.getClassName(superType);
		}
		return result;
	}

	private Class<?> getClass(HippoBeanClass hippoBeanClass) {
		try {
			return Class.forName(hippoBeanClass.getFullyQualifiedName(), true, classLoader);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private Comparator<Class<? extends HippoBean>> classExtensionComparator = new Comparator<Class<? extends HippoBean>>() {

		@Override
		public int compare(Class<? extends HippoBean> o1, Class<? extends HippoBean> o2) {
			int result;
			if (o1 != null && o2 == null) {
				result = 1;
			} else if (o1 == null && o2 != null) {
				result = -1;
			} else if (o1.equals(o1)) {
				result = 0;
			} else if (o1.isAssignableFrom(o2)) {
				result = 1;
			} else if (o2.isAssignableFrom(o1)) {
				result = -1;
			} else {
				throw new IllegalArgumentException("the given arguments are not comparable");
			}

			return result;
		}
	};

}
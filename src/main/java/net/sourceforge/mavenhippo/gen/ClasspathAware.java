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
package net.sourceforge.mavenhippo.gen;

import java.util.Map;

import net.sourceforge.mavenhippo.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class ClasspathAware {

    private final Map<String, HippoBeanClass> beansOnClassPath;
    private final Map<String, HippoBeanClass> beansInProject;

    public ClasspathAware(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
        this.beansInProject = beansInProject;
        this.beansOnClassPath = beansOnClassPath;
    }

    protected Map<String, HippoBeanClass> getBeansOnClassPath() {
        return beansOnClassPath;
    }

    protected Map<String, HippoBeanClass> getBeansInProject() {
        return beansInProject;
    }

}

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
package net.sourceforge.mavenhippo.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class HippoBeanClass {

    private final String packageName;
    private final String name;
    private final String nodeType;

    public HippoBeanClass(String packageName, String name, String nodeType) {
        this.packageName = packageName;
        this.name = name;
        this.nodeType = nodeType;
    }

    public String getFullyQualifiedName() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(name)) {
            sb.append(name);
        } else {
            sb.append(packageName).append('.').append(name);
        }
        return sb.toString();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getNodeType() {
        return nodeType;
    }

}

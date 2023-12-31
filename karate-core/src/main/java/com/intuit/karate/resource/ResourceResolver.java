/*
 * The MIT License
 *
 * Copyright 2022 Karate Labs Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate.resource;

import com.intuit.karate.FileUtils;

/**
 *
 * @author pthomas3
 */
public class ResourceResolver {

    public final boolean classpath;
    public final String root;

    private static final String EMPTY = "";
    private static final String SLASH = "/";

    public ResourceResolver(String root) {
        if (root == null) {
            root = EMPTY;
        }
        classpath = root.startsWith(Resource.CLASSPATH_COLON);
        root = ResourceUtils.removePrefix(root);
        if (!root.isEmpty() && !root.endsWith(SLASH)) {
            root = root + SLASH;
        }
        this.root = root;
    }

    public Resource resolve(String path) {
        return resolve(null, path);
    }

    public Resource resolve(String caller, String path) {
        if (path.startsWith(Resource.CLASSPATH_COLON)) {
            return get(path);
        }
        String prefix = classpath ? Resource.CLASSPATH_COLON + root : root;
        if (path.startsWith(Resource.THIS_COLON) && caller != null) {
            return get(prefix + ResourceUtils.getParentPath(caller) + path.substring(5));
        }
        return get(prefix + (path.charAt(0) == '/' ? path.substring(1) : path));
    }

    private Resource get(String path) {
        return ResourceUtils.getResource(FileUtils.WORKING_DIR, path);
    }

    @Override
    public String toString() {
        return classpath ? Resource.CLASSPATH_COLON + root : root;
    }

}

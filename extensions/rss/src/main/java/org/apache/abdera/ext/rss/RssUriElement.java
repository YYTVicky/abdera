/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */
package org.apache.abdera.ext.rss;

import javax.xml.namespace.QName;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.ElementWrapper;
import org.apache.abdera.model.IRIElement;

public class RssUriElement extends ElementWrapper implements IRIElement {

    public RssUriElement(Element internal) {
        super(internal);
    }

    public RssUriElement(Factory factory, QName qname) {
        super(factory, qname);
    }

    public IRI getResolvedValue() {
        return getValue();
    }

    public IRI getValue() {
        return new IRI(getText());
    }

    public IRIElement setNormalizedValue(String iri) {
        throw new UnsupportedOperationException("Modifications are not allowed");
    }

    public IRIElement setValue(String iri) {
        throw new UnsupportedOperationException("Modifications are not allowed");
    }

}

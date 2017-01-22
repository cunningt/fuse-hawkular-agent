/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.agent.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operationDmrType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="operationDmrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="param" type="{urn:org.hawkular.agent:agent:1.0}operationParamDmr" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="internal-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operationDmrType", propOrder = {
    "param"
})
public class OperationDmrType {

    protected List<OperationParamDmr> param;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "path")
    protected String path;
    @XmlAttribute(name = "internal-name")
    protected String internalName;

    /**
     * Gets the value of the param property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the param property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParam().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperationParamDmr }
     *
     *
     */
    public List<OperationParamDmr> getParam() {
        if (param == null) {
            param = new ArrayList<OperationParamDmr>();
        }
        return this.param;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the path property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the internalName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInternalName() {
        return internalName;
    }

    /**
     * Sets the value of the internalName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInternalName(String value) {
        this.internalName = value;
    }

}

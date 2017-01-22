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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for metricPrometheusType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="metricPrometheusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-id-template" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-tags" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metricPrometheusType")
public class MetricPrometheusType {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "metric-id-template")
    protected String metricIdTemplate;
    @XmlAttribute(name = "metric-tags")
    protected String metricTags;

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
     * Gets the value of the metricIdTemplate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricIdTemplate() {
        return metricIdTemplate;
    }

    /**
     * Sets the value of the metricIdTemplate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricIdTemplate(String value) {
        this.metricIdTemplate = value;
    }

    /**
     * Gets the value of the metricTags property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricTags() {
        return metricTags;
    }

    /**
     * Sets the value of the metricTags property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricTags(String value) {
        this.metricTags = value;
    }

}

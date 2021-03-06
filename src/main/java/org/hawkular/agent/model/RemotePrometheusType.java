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
 * <p>Java class for remotePrometheusType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="remotePrometheusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="url" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="username" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="password" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="security-realm" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="labels" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="interval" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="time-units" type="{urn:org.hawkular.agent:agent:1.0}timeUnitsType" />
 *       &lt;attribute name="metric-sets" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tenant-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-id-template" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-labels" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "remotePrometheusType")
public class RemotePrometheusType {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "url", required = true)
    protected String url;
    @XmlAttribute(name = "username")
    protected String username;
    @XmlAttribute(name = "password")
    protected String password;
    @XmlAttribute(name = "security-realm")
    protected String securityRealm;
    @XmlAttribute(name = "labels")
    protected String labels;
    @XmlAttribute(name = "interval")
    protected Integer interval;
    @XmlAttribute(name = "time-units")
    protected TimeUnitsType timeUnits;
    @XmlAttribute(name = "metric-sets")
    protected String metricSets;
    @XmlAttribute(name = "tenant-id")
    protected String tenantId;
    @XmlAttribute(name = "metric-id-template")
    protected String metricIdTemplate;
    @XmlAttribute(name = "metric-labels")
    protected String metricLabels;

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
     * Gets the value of the enabled property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the url property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the username property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the securityRealm property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSecurityRealm() {
        return securityRealm;
    }

    /**
     * Sets the value of the securityRealm property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSecurityRealm(String value) {
        this.securityRealm = value;
    }

    /**
     * Gets the value of the labels property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLabels() {
        return labels;
    }

    /**
     * Sets the value of the labels property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLabels(String value) {
        this.labels = value;
    }

    /**
     * Gets the value of the interval property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setInterval(Integer value) {
        this.interval = value;
    }

    /**
     * Gets the value of the timeUnits property.
     *
     * @return
     *     possible object is
     *     {@link TimeUnitsType }
     *
     */
    public TimeUnitsType getTimeUnits() {
        return timeUnits;
    }

    /**
     * Sets the value of the timeUnits property.
     *
     * @param value
     *     allowed object is
     *     {@link TimeUnitsType }
     *
     */
    public void setTimeUnits(TimeUnitsType value) {
        this.timeUnits = value;
    }

    /**
     * Gets the value of the metricSets property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricSets() {
        return metricSets;
    }

    /**
     * Sets the value of the metricSets property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricSets(String value) {
        this.metricSets = value;
    }

    /**
     * Gets the value of the tenantId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets the value of the tenantId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTenantId(String value) {
        this.tenantId = value;
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
     * Gets the value of the metricLabels property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricLabels() {
        return metricLabels;
    }

    /**
     * Sets the value of the metricLabels property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricLabels(String value) {
        this.metricLabels = value;
    }

}

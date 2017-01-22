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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for platformType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="platformType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="file-stores" type="{urn:org.hawkular.agent:agent:1.0}platformMetricType" minOccurs="0"/>
 *         &lt;element name="memory" type="{urn:org.hawkular.agent:agent:1.0}platformMetricType" minOccurs="0"/>
 *         &lt;element name="processors" type="{urn:org.hawkular.agent:agent:1.0}platformMetricType" minOccurs="0"/>
 *         &lt;element name="power-sources" type="{urn:org.hawkular.agent:agent:1.0}platformMetricType" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="machine-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="interval" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="time-units" type="{urn:org.hawkular.agent:agent:1.0}timeUnitsType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "platformType", propOrder = {

})
public class PlatformType {

    @XmlElement(name = "file-stores")
    protected PlatformMetricType fileStores;
    protected PlatformMetricType memory;
    protected PlatformMetricType processors;
    @XmlElement(name = "power-sources")
    protected PlatformMetricType powerSources;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "machine-id")
    protected String machineId;
    @XmlAttribute(name = "interval")
    protected Integer interval;
    @XmlAttribute(name = "time-units")
    protected TimeUnitsType timeUnits;

    /**
     * Gets the value of the fileStores property.
     *
     * @return
     *     possible object is
     *     {@link PlatformMetricType }
     *
     */
    public PlatformMetricType getFileStores() {
        return fileStores;
    }

    /**
     * Sets the value of the fileStores property.
     *
     * @param value
     *     allowed object is
     *     {@link PlatformMetricType }
     *
     */
    public void setFileStores(PlatformMetricType value) {
        this.fileStores = value;
    }

    /**
     * Gets the value of the memory property.
     *
     * @return
     *     possible object is
     *     {@link PlatformMetricType }
     *
     */
    public PlatformMetricType getMemory() {
        return memory;
    }

    /**
     * Sets the value of the memory property.
     *
     * @param value
     *     allowed object is
     *     {@link PlatformMetricType }
     *
     */
    public void setMemory(PlatformMetricType value) {
        this.memory = value;
    }

    /**
     * Gets the value of the processors property.
     *
     * @return
     *     possible object is
     *     {@link PlatformMetricType }
     *
     */
    public PlatformMetricType getProcessors() {
        return processors;
    }

    /**
     * Sets the value of the processors property.
     *
     * @param value
     *     allowed object is
     *     {@link PlatformMetricType }
     *
     */
    public void setProcessors(PlatformMetricType value) {
        this.processors = value;
    }

    /**
     * Gets the value of the powerSources property.
     *
     * @return
     *     possible object is
     *     {@link PlatformMetricType }
     *
     */
    public PlatformMetricType getPowerSources() {
        return powerSources;
    }

    /**
     * Sets the value of the powerSources property.
     *
     * @param value
     *     allowed object is
     *     {@link PlatformMetricType }
     *
     */
    public void setPowerSources(PlatformMetricType value) {
        this.powerSources = value;
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
     * Gets the value of the machineId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * Sets the value of the machineId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMachineId(String value) {
        this.machineId = value;
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

}

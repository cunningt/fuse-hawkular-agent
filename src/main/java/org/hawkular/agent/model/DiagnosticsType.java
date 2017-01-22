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
 * <p>Java class for diagnosticsType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="diagnosticsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="interval" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="time-units" type="{urn:org.hawkular.agent:agent:1.0}timeUnitsType" />
 *       &lt;attribute name="report-to" type="{urn:org.hawkular.agent:agent:1.0}reportToType" default="LOG" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "diagnosticsType")
public class DiagnosticsType {

    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "interval")
    protected Integer interval;
    @XmlAttribute(name = "time-units")
    protected TimeUnitsType timeUnits;
    @XmlAttribute(name = "report-to")
    protected ReportToType reportTo;

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
     * Gets the value of the reportTo property.
     *
     * @return
     *     possible object is
     *     {@link ReportToType }
     *
     */
    public ReportToType getReportTo() {
        if (reportTo == null) {
            return ReportToType.LOG;
        } else {
            return reportTo;
        }
    }

    /**
     * Sets the value of the reportTo property.
     *
     * @param value
     *     allowed object is
     *     {@link ReportToType }
     *
     */
    public void setReportTo(ReportToType value) {
        this.reportTo = value;
    }

}

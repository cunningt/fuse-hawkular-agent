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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for managedServersType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="managedServersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="remote-dmr" type="{urn:org.hawkular.agent:agent:1.0}remoteDmrType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="local-dmr" type="{urn:org.hawkular.agent:agent:1.0}localDmrType" minOccurs="0"/>
 *         &lt;element name="remote-jmx" type="{urn:org.hawkular.agent:agent:1.0}remoteJmxType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="remote-prometheus" type="{urn:org.hawkular.agent:agent:1.0}remotePrometheusType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "managedServersType", propOrder = {
    "remoteDmr",
    "localDmr",
    "remoteJmx",
    "remotePrometheus"
})
public class ManagedServersType {

    @XmlElement(name = "remote-dmr")
    protected List<RemoteDmrType> remoteDmr;
    @XmlElement(name = "local-dmr")
    protected LocalDmrType localDmr;
    @XmlElement(name = "remote-jmx")
    protected List<RemoteJmxType> remoteJmx;
    @XmlElement(name = "remote-prometheus")
    protected List<RemotePrometheusType> remotePrometheus;

    /**
     * Gets the value of the remoteDmr property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remoteDmr property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoteDmr().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemoteDmrType }
     *
     *
     */
    public List<RemoteDmrType> getRemoteDmr() {
        if (remoteDmr == null) {
            remoteDmr = new ArrayList<RemoteDmrType>();
        }
        return this.remoteDmr;
    }

    /**
     * Gets the value of the localDmr property.
     *
     * @return
     *     possible object is
     *     {@link LocalDmrType }
     *
     */
    public LocalDmrType getLocalDmr() {
        return localDmr;
    }

    /**
     * Sets the value of the localDmr property.
     *
     * @param value
     *     allowed object is
     *     {@link LocalDmrType }
     *
     */
    public void setLocalDmr(LocalDmrType value) {
        this.localDmr = value;
    }

    /**
     * Gets the value of the remoteJmx property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remoteJmx property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoteJmx().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemoteJmxType }
     *
     *
     */
    public List<RemoteJmxType> getRemoteJmx() {
        if (remoteJmx == null) {
            remoteJmx = new ArrayList<RemoteJmxType>();
        }
        return this.remoteJmx;
    }

    /**
     * Gets the value of the remotePrometheus property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remotePrometheus property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemotePrometheus().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemotePrometheusType }
     *
     *
     */
    public List<RemotePrometheusType> getRemotePrometheus() {
        if (remotePrometheus == null) {
            remotePrometheus = new ArrayList<RemotePrometheusType>();
        }
        return this.remotePrometheus;
    }

}

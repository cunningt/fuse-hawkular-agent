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
 * <p>Java class for storageType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="storageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="type" type="{urn:org.hawkular.agent:agent:1.0}adapterType" default="HAWKULAR" />
 *       &lt;attribute name="username" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="password" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tenant-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="feed-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="url" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="use-ssl" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="security-realm" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="keystore-path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="keystore-password" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="server-outbound-socket-binding-ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="inventory-context" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metrics-context" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="feedcomm-context" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="connect-timeout-secs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="read-timeout-secs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "storageType")
public class StorageType {

    @XmlAttribute(name = "type")
    protected AdapterType type;
    @XmlAttribute(name = "username", required = true)
    protected String username;
    @XmlAttribute(name = "password", required = true)
    protected String password;
    @XmlAttribute(name = "tenant-id")
    protected String tenantId;
    @XmlAttribute(name = "feed-id")
    protected String feedId;
    @XmlAttribute(name = "url")
    protected String url;
    @XmlAttribute(name = "use-ssl")
    protected Boolean useSsl;
    @XmlAttribute(name = "security-realm")
    protected String securityRealm;
    @XmlAttribute(name = "keystore-path")
    protected String keystorePath;
    @XmlAttribute(name = "keystore-password")
    protected String keystorePassword;
    @XmlAttribute(name = "server-outbound-socket-binding-ref")
    protected String serverOutboundSocketBindingRef;
    @XmlAttribute(name = "inventory-context")
    protected String inventoryContext;
    @XmlAttribute(name = "metrics-context")
    protected String metricsContext;
    @XmlAttribute(name = "feedcomm-context")
    protected String feedcommContext;
    @XmlAttribute(name = "connect-timeout-secs")
    protected Integer connectTimeoutSecs;
    @XmlAttribute(name = "read-timeout-secs")
    protected Integer readTimeoutSecs;

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link AdapterType }
     *
     */
    public AdapterType getType() {
        if (type == null) {
            return AdapterType.HAWKULAR;
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link AdapterType }
     *
     */
    public void setType(AdapterType value) {
        this.type = value;
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
     * Gets the value of the feedId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFeedId() {
        return feedId;
    }

    /**
     * Sets the value of the feedId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFeedId(String value) {
        this.feedId = value;
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
     * Gets the value of the useSsl property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isUseSsl() {
        return useSsl;
    }

    /**
     * Sets the value of the useSsl property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setUseSsl(Boolean value) {
        this.useSsl = value;
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
     * Gets the value of the keystorePath property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKeystorePath() {
        return keystorePath;
    }

    /**
     * Sets the value of the keystorePath property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKeystorePath(String value) {
        this.keystorePath = value;
    }

    /**
     * Gets the value of the keystorePassword property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * Sets the value of the keystorePassword property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKeystorePassword(String value) {
        this.keystorePassword = value;
    }

    /**
     * Gets the value of the serverOutboundSocketBindingRef property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getServerOutboundSocketBindingRef() {
        return serverOutboundSocketBindingRef;
    }

    /**
     * Sets the value of the serverOutboundSocketBindingRef property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setServerOutboundSocketBindingRef(String value) {
        this.serverOutboundSocketBindingRef = value;
    }

    /**
     * Gets the value of the inventoryContext property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInventoryContext() {
        return inventoryContext;
    }

    /**
     * Sets the value of the inventoryContext property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInventoryContext(String value) {
        this.inventoryContext = value;
    }

    /**
     * Gets the value of the metricsContext property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricsContext() {
        return metricsContext;
    }

    /**
     * Sets the value of the metricsContext property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricsContext(String value) {
        this.metricsContext = value;
    }

    /**
     * Gets the value of the feedcommContext property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFeedcommContext() {
        return feedcommContext;
    }

    /**
     * Sets the value of the feedcommContext property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFeedcommContext(String value) {
        this.feedcommContext = value;
    }

    /**
     * Gets the value of the connectTimeoutSecs property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getConnectTimeoutSecs() {
        return connectTimeoutSecs;
    }

    /**
     * Sets the value of the connectTimeoutSecs property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setConnectTimeoutSecs(Integer value) {
        this.connectTimeoutSecs = value;
    }

    /**
     * Gets the value of the readTimeoutSecs property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getReadTimeoutSecs() {
        return readTimeoutSecs;
    }

    /**
     * Sets the value of the readTimeoutSecs property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setReadTimeoutSecs(Integer value) {
        this.readTimeoutSecs = value;
    }

}

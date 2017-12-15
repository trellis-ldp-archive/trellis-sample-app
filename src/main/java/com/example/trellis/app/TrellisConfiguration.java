/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.trellis.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

/**
 * @author acoburn
 */
public class TrellisConfiguration extends Configuration {

    @NotNull
    private String baseUrl;

    @NotNull
    private String binaryPath;

    @NotNull
    private String namespacesPath;

    private String jwtKey;
    private String basicAuthPath;

    /**
     * Get the base URL for the partition
     * @return the partition baseURL
     */
    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Set the base URL for the partition
     * @param baseUrl the partition baseURL
     */
    @JsonProperty
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Get the binary configuration
     * @return the binary configuration
     */
    @JsonProperty
    public String getBinaryPath() {
        return binaryPath;
    }

    /**
     * Set the binary configuration
     * @param binaryPath the binary configuration
     */
    @JsonProperty
    public void setBinaryPath(final String binaryPath) {
        this.binaryPath = binaryPath;
    }

    /**
     * Set the namespaces path
     * @param namespacesPath the path to the namespaces configuration
     */
    @JsonProperty
    public void setNamespacesPath(final String namespacesPath) {
        this.namespacesPath = namespacesPath;
    }

    /**
     * Get the namespace configuration path
     * @return the namespace configuration path
     */
    @JsonProperty
    public String getNamespacesPath() {
        return namespacesPath;
    }

    /**
     * Set the JWT key
     * @param jwtKey the JWT secret key
     */
    @JsonProperty
    public void setJwtKey(final String jwtKey) {
        this.jwtKey = jwtKey;
    }

    /**
     * Get the JWT key
     * @return the JWT secret key
     */
    @JsonProperty
    public String getJwtKey() {
        return jwtKey;
    }

    /**
     * Set the Basic Auth configuration file path
     * @param basicAuthPath the basic auth configuration path
     */
    @JsonProperty
    public void setBasicAuthPath(final String basicAuthPath) {
        this.basicAuthPath = basicAuthPath;
    }

    /**
     * Get the Basic Auth configuration file path
     * @return the Basic Auth configuration path
     */
    @JsonProperty
    public String getBasicAuthPath() {
        return basicAuthPath;
    }


}

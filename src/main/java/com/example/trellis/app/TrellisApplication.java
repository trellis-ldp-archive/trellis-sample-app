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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import io.dropwizard.Application;
import io.dropwizard.auth.chained.ChainedAuthFilter;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.trellisldp.agent.SimpleAgent;
import org.trellisldp.api.BinaryService;
import org.trellisldp.api.IOService;
import org.trellisldp.api.IdentifierService;
import org.trellisldp.api.NamespaceService;
import org.trellisldp.api.ResourceService;
import org.trellisldp.binary.FileBasedBinaryService;
import org.trellisldp.http.AgentAuthorizationFilter;
import org.trellisldp.http.CacheControlFilter;
import org.trellisldp.http.CrossOriginResourceSharingFilter;
import org.trellisldp.http.LdpResource;
import org.trellisldp.http.WebAcFilter;
import org.trellisldp.id.UUIDGenerator;
import org.trellisldp.io.JenaIOService;
import org.trellisldp.namespaces.NamespacesJsonContext;
import org.trellisldp.webac.WebACService;


/**
 * @author acoburn
 */
public class TrellisApplication extends Application<TrellisConfiguration> {

    /**
     * The main entry point
     * @param args the argument list
     * @throws Exception if something goes horribly awry
     */
    public static void main(final String[] args) throws Exception {
        new TrellisApplication().run(args);
    }

    @Override
    public String getName() {
        return "My Trellis Application";
    }

    @Override
    public void initialize(final Bootstrap<TrellisConfiguration> bootstrap) {
        // Not currently used
    }

    @Override
    public void run(final TrellisConfiguration config,
                    final Environment environment) throws IOException {

        // TODO -- Add Resource Service implementation here
        final ResourceService resourceService = null;



        // Default service implementations -- you may wish to change any/all of these
        final IdentifierService idService = new UUIDGenerator();
        final NamespaceService namespaceService = new NamespacesJsonContext(config.getNamespacesPath());
        final IOService ioService = new JenaIOService(namespaceService);
        final BinaryService binaryService = new FileBasedBinaryService(config.getBinaryPath(),
                idService.getSupplier("file:", 4, 2)); // 4 levels of hierarchy, 2 characters per level



        // Enable Authentication
        final List<AuthFilter> filters = new ArrayList<>();
        // Enable JWT auth
        filters.add(new OAuthCredentialAuthFilter.Builder<Principal>()
                .setAuthenticator(new JwtAuthenticator(config.getJwtKey(), false))
                .setPrefix("Bearer")
                .buildAuthFilter());
        // Enable Basic auth
        filters.add(new BasicCredentialAuthFilter.Builder<Principal>()
                .setAuthenticator(new BasicAuthenticator(config.getBasicAuthPath()))
                .setRealm("Trellis Basic Authentication")
                .buildAuthFilter());
        // Enable Anon auth
        filters.add(new AnonymousAuthFilter.Builder()
            .setAuthenticator(new AnonymousAuthenticator())
            .buildAuthFilter());
        environment.jersey().register(new ChainedAuthFilter<>(filters));



        // Enable Jax-RS Resource matchers
        environment.jersey().register(new LdpResource(resourceService, ioService, binaryService, config.getBaseUrl()));



        // Enable Jax-RS Filters
        environment.jersey().register(new AgentAuthorizationFilter(new SimpleAgent(), emptyList()));
        environment.jersey().register(new CacheControlFilter(86400)); // Cache MaxAge value


        // Enable Authorization
        environment.jersey().register(new WebAcFilter(asList("Authorization"), new WebACService(resourceService)));



        // Enable CORS Support
        environment.jersey().register(new CrossOriginResourceSharingFilter(
                    asList("*"), // allowed origins
                    asList("GET", "POST", "PUT", "PATCH", "HEAD", "OPTIONS", "DELETE"), // allowed methods
                    asList("Content-Type"), // allowed headers
                    asList("Content-Type"), // exposed headers
                    true, // Access-Control-Allow-Credentials
                    180)); // Max-Age header
    }
}

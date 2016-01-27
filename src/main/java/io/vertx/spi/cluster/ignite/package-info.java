/*
 * Copyright (c) 2015 The original author or authors
 * ---------------------------------
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

/**
 * = Apache Ignite Cluster Manager for Vert.x
 *
 * This is a cluster manager implementation for Vert.x that uses http://ignite.apache.org/index.html[Apache Ignite].
 *
 * In Vert.x a cluster manager is used for various functions including:
 *
 * * Discovery and group membership of Vert.x nodes in a cluster
 * * Maintaining cluster wide topic subscriber lists (so we know which nodes are interested in which event bus
 * addresses)
 * * Distributed Map support
 * * Distributed Locks
 * *  Distributed Counters
 *
 * Cluster managers *do not* handle the event bus inter-node transport, this is done directly by Vert.x with TCP
 * connections.
 *
 * Vert.x cluster manager is a pluggable component, so you can pick the one you want, or the one that is the most
 * adapted to your environment. So you can replace default Vert.x cluster manager by this implementation.
 *
 * == Using this cluster manager
 *
 * If the jar is on your classpath then Vert.x will automatically detect this and use it as the cluster manager.
 * Please make sure you don’t have any other cluster managers on your classpath or Vert.x might choose the wrong one.
 *
 * Alternatively, you can configure the following system property to instruct vert.x to use this cluster manager:
 * `-Dvertx.clusterManagerFactory=io.vertx.spi.cluster.ignite.IgniteClusterManager`
 *
 * ### Using Vert.x from command line
 *
 * `vertx-ignite-${maven.version}.jar` should be in the `lib` directory of the Vert.x installation.
 *
 * ### Using Vert.x in Maven or Gradle project
 *
 * Add a dependency to the artifact.
 *
 * * Maven (in your `pom.xml`):
 *
 * [source,xml,subs="+attributes"]
 * ----
 * <dependency>
 *   <groupId>${maven.groupId}</groupId>
 *   <artifactId>${maven.artifactId}</artifactId>
 *   <version>${maven.version}</version>
 * </dependency>
 * ----
 *
 * * Gradle (in your `build.gradle` file):
 *
 * [source,groovy,subs="+attributes"]
 * ----
 * compile '${maven.groupId}:${maven.artifactId}:${maven.version}'
 * ----
 *
 * ### Programmatically specifying cluster manager
 *
 * You can also specify the cluster manager programmatically. In order to do this just specify it on the options
 * when you are creating your Vert.x instance, for example:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#example1()}
 * ----
 *
 * == Configuring cluster manager
 *
 * === Using configuration file
 *
 * The cluster manager is configured by a file `default-ignite.xml` which is packaged inside the jar.
 *
 * If you want to override this configuration you can provide `ignite.xml` file on your classpath and this will be
 * used instead.
 *
 * The xml file is a Ignite configuration file and is described in details in
 * https://apacheignite.readme.io/docs[Apache Ignite documentation].
 *
 * ### Configuring programmatically
 *
 * You can also specify configuration programmatically:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#example2()}
 * ----
 *
 * === Discovery and network transport configuration
 *
 * The default configuration uses `TcpDiscoveryMulticastIpFinder` so you must have multicast enabled on your network.
 * For cases when multicast is disabled `TcpDiscoveryVmIpFinder` should be used with pre-configured list of IP addresses.
 * Please see http://apacheignite.readme.io/docs/cluster-config[Cluster Configuration] section
 * at Apache Ignite documentation for details.
 *
 * == Trouble shooting clustering
 *
 * If the default multicast configuration is not working here are some common causes:
 *
 * === Multicast not enabled on the machine.
 *
 * By default the cluster manager is using `TcpDiscoveryMulticastIpFinder`, so IP multicasting is required,
 * on some systems, multicast route(s) need to be added to the routing table otherwise, the default route will be used.
 *
 * Note that some systems don't consult the routing table for IP multicast routing, only for unicast routing
 *
 * MacOS example:
 *
 * ----
 * # Adds a multicast route for 224.0.0.1-231.255.255.254
 * sudo route add -net 224.0.0.0/5 127.0.0.1
 *
 * # Adds a multicast route for 232.0.0.1-239.255.255.254
 * sudo route add -net 232.0.0.0/5 192.168.1.3
 * ----
 *
 * Please google for more information.
 *
 */
@Document(fileName = "index.ad")
package io.vertx.spi.cluster.ignite;

import io.vertx.docgen.Document;
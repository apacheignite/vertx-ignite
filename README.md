# Apache Ignite Cluster Manager for Vert.x

This is a cluster manager implementation for Vert.x that uses [Apache Ignite](http://ignite.apache.org/index.html).

Vert.x cluster manager is pluggable component. So you can replace default Vert.x cluster manager by this implementation.

## How to use

If the jar is on your classpath then Vert.x will automatically detect this and use it as the cluster manager.
Please make sure you don’t have any other cluster managers on your classpath or Vert.x might choose the wrong one.

### Using Vert.x from command line

`vertx-ignite-${version}.jar` should be in the `lib` directory of the Vert.x installation.

### Using Vert.x in Maven or Gradle project

Add a dependency to the artifact.

**Maven:**

```xml
<dependency>
  <groupId>io.vertx</groupId>
  <artifactId>vertx-ignite</artifactId>
  <version>${version}</version>
</dependency>
```

**Gradle:**

```
io.vertx:vertx-ignite:${version}:jar
```

### Programmatically specifying cluster manager

You can also specify the cluster manager programmatically. In order to do this just specify it on the options
when you are creating your Vert.x instance, for example:

```java
ClusterManager clusterManager = new IgniteClusterManager();

VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

Vertx.clusteredVertx(options, res -> {
  if (res.succeeded()) {
    Vertx vertx = res.result();
  } else {
    // failed!
  }
});
```
## How to build

In order to build this cluster manager from sources just use the following command:

```
mvn clean package -DskipTests
```

If you want to build this cluster manager with specific Apache Ignite version (1.5.0 or later):

```
mvn clean package -Dignite.version=${version} -DskipTests
```



## Configuring cluster manager

### Using configuration file

The cluster manager is configured by a file `default-ignite.xml` which is packaged inside the jar.

If you want to override this configuration you can provide `ignite.xml` file on your classpath and this will be used instead.

The xml file is a Ignite configuration file and is described in details in
[Apache Ignite documentation](https://apacheignite.readme.io/docs).

### Configuring programmatically

You can also specify configuration programmatically:

```java
IgniteConfiguration cfg = new IgniteConfiguration();

// Configuration code (omitted)

ClusterManager clusterManager = new IgniteClusterManager(cfg);

VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

Vertx.clusteredVertx(options, res -> {
  if (res.succeeded()) {
    Vertx vertx = res.result();
  } else {
    // failed!
  }
});
```

### Discovery and network transport configuration

The default configuration uses `TcpDiscoveryMulticastIpFinder` so you must have multicast enabled on your network.
For cases when multicast is disabled `TcpDiscoveryVmIpFinder` should be used with pre-configured list of IP addresses.
Please see [Cluster Configuration](http://apacheignite.readme.io/docs/cluster-config) section
at Apache Ignite documentation for details.

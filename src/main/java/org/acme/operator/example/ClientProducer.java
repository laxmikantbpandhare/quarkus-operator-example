package org.acme.operator.example;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.launcher.shaded.com.google.inject.name.Named;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientProducer {


    // setup a K8S client
    @Produces
    @Singleton
    KubernetesClient makeDefaultKubernetesClient(@Named("namespace") String namespace) {
        return new DefaultKubernetesClient().inNamespace(namespace);
    }

    @Produces
    @Singleton
    @Named("namespace")
    String getCurrentNameSpace() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/var/run/secret/kubernetes.io/serviceaccount/namespace")));
    }
}

package org.acme.operator.example;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

public class PodWatcher {

    @Inject
    KubernetesClient defaultClient;

    void onStartup(@Observes StartupEvent ev){
        List<Pod> list = defaultClient.pods().list().getItems();

        for(Pod resource:list){
            System.out.println("Foudn Resource" + "name = " + resource.getMetadata().getName() +
                    "Version" + resource.getMetadata().getResourceVersion());

            defaultClient.pods().watch(new Watcher<Pod>() {
                @Override
                public void eventReceived(Action action, Pod pod) {
                    System.out.println("Event Received" + action + "event for" + "name = " + resource.getMetadata().getName() +
                            "Version" + resource.getMetadata().getResourceVersion());

                }

                @Override
                public void onClose(KubernetesClientException e) {
                    if(e != null){
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            });
        }
    }
}

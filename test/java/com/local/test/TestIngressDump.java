package com.local.test;

import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1beta1Ingress;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;


public class TestIngressDump {

    @Test
    public void testIngress(){
        V1beta1Ingress ingress = new V1beta1Ingress();
        ingress.setKind("Ingress");
        ingress.setApiVersion("extensions/v1beta1");
        V1ObjectMeta metadata = new V1ObjectMeta();
        metadata.setName("test");
        Map<String, String> labels = new HashMap<>();
        labels.put("choerodon.io/network", "ingress");

        ingress.setMetadata(metadata);

        ingress.getMetadata().setLabels(labels);

        Map<String, String> annotations = new HashMap<>();
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/abc");

        ingress.getMetadata().setAnnotations(annotations);

        Yaml yaml = new Yaml();
        System.out.println(yaml.dump(ingress));
    }
}

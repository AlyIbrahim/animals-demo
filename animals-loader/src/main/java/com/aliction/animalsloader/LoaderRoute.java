package com.aliction.animalsloader;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoaderRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        // TODO Auto-generated method stub
        from("file://inbox?move=.done")
        .id("file-route")
        .convertBodyTo(java.io.InputStream.class, "utf-8")
        .split(body().tokenize("\n")).streaming()
            .to("kafka:{{output.topic}}?brokers={{output.brokers}}")
        .end();
        
    }
    
}

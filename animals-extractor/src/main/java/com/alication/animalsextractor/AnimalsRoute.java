package com.alication.animalsextractor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class AnimalsRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        // from("timer:{ \"name\": \"Aly\" }").to("kafka:{{input.topic}}?brokers={{input.brokers}}");
        from("kafka:{{input.topic}}?brokers={{input.brokers}}")
        .unmarshal().json(JsonLibrary.Jackson)
        .setBody(simple("${body}"))
        .choice()
        .when().jsonpath("$.[?(@.type=='dog')]")
            // .when().simple("${body.[type]} == 'dog'")
                .log("${body}")
                .to("kafka:dogs?brokers={{output.brokers}}")
            .otherwise()
                .log("Otherwise")
        .marshal().json()
        .log("${body}")
        .to("kafka:{{output.topic}}?brokers={{output.brokers}}");
    }
    
}

package com.example.micro1.configuration;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import io.opentelemetry.context.propagation.TextMapSetter;


@Configuration
public class Micro1Config {

//    @Bean
//    public OpenTelemetry openTelemetry() {
//        OtlpGrpcSpanExporter spanExporter = OtlpGrpcSpanExporter.builder()
//                .setEndpoint("http://localhost:9411") // Endpoint for the OTLP collector
//                .build();
//
//        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
//                .addSpanProcessor(BatchSpanProcessor.builder(spanExporter).build())
//                .build();
//
//        return OpenTelemetrySdk.builder()
//                .setTracerProvider(sdkTracerProvider)
//                .buildAndRegisterGlobal();
//    }
    @Bean
    public RestTemplate restTemplate()
    {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            GlobalOpenTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), request.getHeaders(),
                    (TextMapSetter<HttpHeaders>) HttpHeaders::set);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
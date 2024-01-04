package com.example.micro1.configuration;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class OutgoingRequestTracingInterceptor         implements ClientHttpRequestInterceptor {
    private final TextMapPropagator textMapPropagator = GlobalOpenTelemetry.getPropagators().getTextMapPropagator();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException, IOException {
        // Use OpenTelemetry to inject the current context into the request headers
        // ...
        textMapPropagator.inject(Context.current(), request.getHeaders(), (headers, key, value) -> headers.add(key, value));

        return execution.execute(request, body);
    }
}

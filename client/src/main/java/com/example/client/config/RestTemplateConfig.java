package com.example.client.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * @author urey.liu
 * @description
 * @date 2023/12/9 6:09 下午
 */
@Configuration
public class RestTemplateConfig {

    private static final String PASSWORD = "studyTest";

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Bean("httpComponentsClientHttpRequestFactory")
    public ClientHttpRequestFactory httpComponentsClientHttpRequestFactory() throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(new ClassPathResource("client.keystore").getURL(),
                        PASSWORD.toCharArray(), PASSWORD.toCharArray())
                .loadTrustMaterial(new ClassPathResource("client.truststore").getURL(), PASSWORD.toCharArray())
                .build();
        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }
}

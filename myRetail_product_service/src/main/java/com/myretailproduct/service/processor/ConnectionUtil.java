package com.myretailproduct.service.processor;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Util class to get http connection
 */
public class ConnectionUtil {

    private static RestTemplate template;

    /**
     * returns a rest template with fixed connection pool and timeout settings
     *
     * @return
     */
    public static RestTemplate getRestTemplate() {

        if (template == null) {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder
                    .create()
                    .setConnectionManager(new PoolingHttpClientConnectionManager() {{
                        setDefaultMaxPerRoute(5);
                        setMaxTotal(20);
                    }}).build());
            factory.setConnectionRequestTimeout(1000);
            factory.setReadTimeout(2000);
            template = new RestTemplate(factory);
        }


        return template;
    }
}

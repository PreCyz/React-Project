package react.pw.carly.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Configuration
@Slf4j
public class MainConfig {

    @Value(value = "${cors.urls}")
    private String corsUrls;
    @Value(value = "${cors.mappings}")
    private String corsMappings;
    @Autowired
    private WebSecurityConfigurerAdapter webSecurityConfigurerAdapter;

    private static final Map<String, String> envPropertiesMap = System.getenv();

    @PostConstruct
    private void init() {
        log.debug("************** Environment variables **************");
        for (Map.Entry<String, String> entry : envPropertiesMap.entrySet()) {
            log.debug("[{}] : [{}]", entry.getKey(), entry.getValue());
        }
        log.debug("WebSecurityConfigurerAdapter implementation is [{}].", webSecurityConfigurerAdapter.getClass().getSimpleName());
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public HttpClient httpClient(RestTemplate restTemplate) {
        return new HttpService(restTemplate);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        getCorsUrls();
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                final Set<String> mappings = getCorsMapings();
                if (mappings.isEmpty()) {
                    registry.addMapping("/**");
                } else {
                    for (String mapping : mappings) {
                        registry.addMapping(mapping).allowedOrigins(getCorsUrls()).allowedMethods("*");

                    }
                }
            }
        };
    }

    private String[] getCorsUrls() {
        return Optional.ofNullable(corsUrls)
                .map(value -> value.split(","))
                .orElseGet(() -> new String[0]);
    }

    private Set<String> getCorsMapings() {
        return Optional.ofNullable(corsMappings)
                .map(value -> Arrays.stream(value.split(",")))
                .map(stream -> stream.collect(toSet()))
                .orElseGet(HashSet::new);
    }
}

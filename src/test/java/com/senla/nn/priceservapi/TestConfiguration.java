package com.senla.nn.priceservapi;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.senla.nn.priceservapi.security.JwtTokenFilter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfiguration {

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//
//        return builder -> {
//
//            // formatter
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            // deserializers
//            builder.deserializers(new LocalDateDeserializer(dateFormatter));
//            builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
//
//            // serializers
//            builder.serializers(new LocalDateSerializer(dateFormatter));
//            builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
//        };
//    }


//    @Bean
//    public RestTemplate restTemplate() {
//        final RestTemplate restTemplate = new RestTemplate();
//
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
//
//        return restTemplate;
//    }

//    @Bean
//    public RestOperations restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//
//        converter.setSupportedMediaTypes(
//                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
//
//        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
//        return restTemplate;
//    }

//    @Bean
//    public RestTemplate restTemplate() {
//        final RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(jacksonSupportsMoreTypes());
//        return restTemplate;
//    }
//
//
//    private HttpMessageConverter jacksonSupportsMoreTypes() {//eg. Gitlab returns JSON as plain text
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(
//                Arrays.asList(MediaType.parseMediaType("text/plain;charset=utf-8"), MediaType.APPLICATION_OCTET_STREAM));
//        return converter;
//    }
}
package com.example.librarymanagementsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurează ObjectMapper-ul folosit de Spring pentru a gestiona serializarea/deserializarea JSON.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 1. Permite citirea și scrierea tipurilor din Java 8 Date/Time API (e.g., LocalDate, LocalDateTime)
        mapper.registerModule(new JavaTimeModule());

        // 2. Dezactivează scrierea datelor ca timestamp-uri numerice (scrie ca string-uri ISO 8601, mai lizibil)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 3. (Opțional, Recomandat pentru fișiere) Formatează JSON-ul cu indentare pentru a fi citit mai ușor
        // Asta e util în special când salvați datele pe disc.
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }
}

package com.speakapp.postservice.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(com.speakapp.springexceptionhandler.GlobalExceptionHandler.class)
public class ExceptionHandlerConfig {

}

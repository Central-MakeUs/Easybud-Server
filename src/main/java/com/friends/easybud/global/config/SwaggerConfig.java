package com.friends.easybud.global.config;

import static java.util.stream.Collectors.groupingBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.friends.easybud.global.annotation.ApiErrorCodeExample;
import com.friends.easybud.global.response.ExampleHolder;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.global.response.code.Reason;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Easybud API Document")
                .version("v0.0.1")
                .description("""
                        ## Easybud API 명세서입니다. 🌱
                                                
                        ---
                                                
                        ### 🔑 테스트 사용자 인증 토큰
                        **eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzYjk5ZjYwZC1mOWY1LTQ2MmUtODg5NS0yM2E1MmVkZDI0NTEiLCJhdXRoIjoiVVNFUiIsImV4cCI6MTcwNzg0NjgxOX0.ovjnSjn0Dmm1d8AYTlL4Fklh5KhHEDNpmW-3zr3ZRks**
                                                                        
                        """);

        String jwtSchemeName = "JWT";

        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        Server server = new Server().url("/");

        return new OpenAPI()
                .components(components)
                .info(info)
                .addSecurityItem(securityRequirement)
                .addServersItem(server);
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiErrorCodeExample apiErrorCodeExample =
                    handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
            }
            return operation;
        };
    }

    private void generateErrorCodeResponseExample(
            Operation operation, ErrorStatus[] errorStatuses) {
        ApiResponses responses = operation.getResponses();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
                Arrays.stream(errorStatuses)
                        .map(
                                errorStatus -> {
                                    Reason errorReason = errorStatus.getReason();
                                    return ExampleHolder.builder()
                                            .holder(
                                                    getSwaggerExample(
                                                            errorReason.getMessage(),
                                                            errorReason))
                                            .code(errorReason.getCode())
                                            .name(errorReason.getCode().toString())
                                            .build();
                                })
                        .collect(groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(String value, Reason errorReason) {
        ResponseDto<Object> responseDto = new ResponseDto<>(false, errorReason.getCode(), value, null);
        Example example = new Example();
        example.description(value);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonResponseDto = objectMapper.writeValueAsString(responseDto);
            example.setValue(jsonResponseDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return example;
    }

    private void addExamplesToResponses(
            ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();
                    v.forEach(
                            exampleHolder -> mediaType.addExamples(
                                    exampleHolder.getName(), exampleHolder.getHolder()));
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(status.toString(), apiResponse);
                });
    }

}
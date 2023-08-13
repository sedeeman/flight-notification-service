package com.sedeeman.ca.config;

import com.sedeeman.ca.util.Constant;
import com.sedeeman.ca.util.ReadJsonFileToJsonObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@OpenAPIDefinition
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI baseOpenAPI() {

        ReadJsonFileToJsonObject readJsonObject = new ReadJsonFileToJsonObject();

        ApiResponse getAllSuccessAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(Constant.DEFAULT, new Example().value(readJsonObject.read().get("getAllSuccessResponse").toString()))
                )).description("Ok(Success)");

        ApiResponse postSuccessAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(Constant.DEFAULT, new Example().value(readJsonObject.read().get("postSuccessResponse").toString()))
                )).description("Created");

        ApiResponse noContentAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(Constant.DEFAULT, new Example().value(readJsonObject.read().get("noContentResponse").toString()))
                )).description("No Content");

        ApiResponse badRequestAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(Constant.DEFAULT, new Example().value(readJsonObject.read().get("badRequestResponse").toString()))
                )).description("Bad Request");

        ApiResponse internalServerErrorAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(Constant.DEFAULT, new Example().value(readJsonObject.read().get("internalServerErrorResponse").toString()))
                )).description("Internal Server Error");

        Components components = new Components();
        components.addResponses("getAllSuccessAPI", getAllSuccessAPI);
        components.addResponses("postSuccessAPI", postSuccessAPI);
        components.addResponses("noContentAPI", noContentAPI);
        components.addResponses("badRequestAPI", badRequestAPI);
        components.addResponses("internalServerErrorAPI", internalServerErrorAPI);

        return new OpenAPI()
                .components(components)
                .info(new Info().title("Flight Notification Service Doc").version("1.0.0").description("Spring Boot RESTful APIs Implementation for flight service"));
    }

}

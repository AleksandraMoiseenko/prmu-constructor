package ru.prmu.constructor.configuration;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApplicationConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis (RequestHandlerSelectors.any()) // Пакет сканирования Swagger
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData())
            .tags(
                new Tag("course-api", "Api для работы с курсами"),
                new Tag("module-api", "Api для работы с модулями"),
                new Tag("topic-api", "Api для работы с темами"),
                new Tag("file-api", "Api для работы с файлами"),
                new Tag("tutor-api", "Api для работы с преподавателями"));
    }

    private ApiInfo metaData() {
        return new ApiInfo(
            "PRMU LMS Web API",
            "Web API сервиса PRMU LMS",
            "1.0",
            "Terms of service",
            new Contact("PRMU LMS", "https://pimunn.ru/", "11"),
            "Apache License Version 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0",
            Collections.emptyList());
    }

}

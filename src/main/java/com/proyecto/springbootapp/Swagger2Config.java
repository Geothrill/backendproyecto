package com.proyecto.springbootapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**clase que nos permite configurar Swagger
 * @Configuration para configuraciones de spring
 * @EnableSwagger2 para que podamos tener el entorno visual de swagger
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * Especificamos el ambito que va a tener la interfaz de Swagger para trabajar
     * .apis restringe las apis a mostrar, que serán todas las incluidas en el proyecto raiz
     * @return las apis a visualizar en la interfaz gráfica
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.proyecto.springbootapp"))
                .build();
    }
}

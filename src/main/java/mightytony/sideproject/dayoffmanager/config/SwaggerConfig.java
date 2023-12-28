package mightytony.sideproject.dayoffmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // http://localhost:4860/swagger-ui/index.html#/

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("tony의 사원 연차 관리 시스템 REST API Swagger")
                        .description("사원 연차 신청, 관리 기능을 제공 합니다.")
                        .version("1.0.0"));
    }

}

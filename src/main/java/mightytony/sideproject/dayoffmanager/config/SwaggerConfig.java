package mightytony.sideproject.dayoffmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "연차시스템 API 명세서",
        description = "연차 시스템 API 명세서", version = "v1")
)
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

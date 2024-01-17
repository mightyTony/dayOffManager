package mightytony.sideproject.dayoffmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
        // FIXME
        //  참고 https://velog.io/@juhyeon1114/Springdoc%EC%9C%BC%EB%A1%9C-Swagger%EB%AC%B8%EC%84%9C-%EC%83%9D%EC%84%B1%ED%95%98%EA%B8%B0-Spring-boot-3.0-%EC%9D%B4%EC%83%81

        Info info = new Info()
                .version("v1.0.0")
                .title("수원청개구리와 용인 푸바오의 사원 연차 관리 시스템 REST API Swagger")
                .description("사원 연차 신청, 관리 기능을 제공 합니다.");

        // 스웨거에서 인증(Authorize) 버튼 세팅
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt); // 헤더에 토큰 포함
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
        //        return new OpenAPI()
//                .info(new Info()
//                        .title("tony의 사원 연차 관리 시스템 REST API Swagger")
//                        .description("사원 연차 신청, 관리 기능을 제공 합니다.")
//                        .version("1.0.0"));
    }

}

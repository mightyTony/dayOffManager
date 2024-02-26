package mightytony.sideproject.dayoffmanager.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "헬스 체크", description = "서버 헬스 체크 용")
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthcheck () {
        return ResponseEntity.ok("hello");
    }
}

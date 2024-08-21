package mightytony.sideproject.dayoffmanager.user.teamleader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/teamleader")
@Tag(name = "팀장", description = "팀장 관련 api / 로그인 필요")
@PreAuthorize("hasAnyRole('TEAM_LEADER')")
public class TeamLeaderController {
}

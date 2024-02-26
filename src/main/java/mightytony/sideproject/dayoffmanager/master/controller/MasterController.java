package mightytony.sideproject.dayoffmanager.master.controller;

import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.master.service.MasterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/app/v1/master")
@RestController
@PreAuthorize("hasAuthority('MASTER')")
public class MasterController {

    private final MasterService masterService;

    //Todo
    /**
     * 기업 어드민 등록 요청 허가/거절
     */

    //Todo
    /**
     * 기업 어드민 등록 요청 목록 조회(페이징)
     */

}

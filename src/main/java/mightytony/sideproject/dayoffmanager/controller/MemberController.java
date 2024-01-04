package mightytony.sideproject.dayoffmanager.controller;

import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
}

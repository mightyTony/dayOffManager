package mightytony.sideproject.dayoffmanager.master.service;

import mightytony.sideproject.dayoffmanager.master.domain.dto.request.MasterInviteAdminRequestDto;
import org.springframework.http.ResponseEntity;

public interface MasterService {

    String inviteAdminToCompany(MasterInviteAdminRequestDto dto);

    // deleteCompany(String );
}

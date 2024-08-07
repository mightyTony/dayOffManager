package mightytony.sideproject.dayoffmanager.user.master.service;

import mightytony.sideproject.dayoffmanager.user.master.domain.dto.request.MasterInviteAdminRequestDto;

public interface MasterService {

    String inviteAdminToCompany(MasterInviteAdminRequestDto dto);

    // deleteCompany(String );
}

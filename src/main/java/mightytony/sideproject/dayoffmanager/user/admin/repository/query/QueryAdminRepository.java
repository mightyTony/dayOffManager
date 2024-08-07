package mightytony.sideproject.dayoffmanager.user.admin.repository.query;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;

public interface QueryAdminRepository {

    Member findMemberByUserIdAndCompanyId(String userId, Long companyId);
}

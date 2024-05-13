package mightytony.sideproject.dayoffmanager.admin.repository.query;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;

public interface QueryAdminRepository {

    Member findMemberByUserIdAndCompanyId(String userId, Long companyId);
}

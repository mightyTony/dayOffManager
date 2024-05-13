package mightytony.sideproject.dayoffmanager.dayoff.repository;

import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {

}

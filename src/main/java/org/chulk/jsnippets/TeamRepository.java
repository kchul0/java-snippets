package org.chulk.jsnippets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {

	@Query("SELECT COUNT(t) FROM Team t")
	long getTeamCount();
	
}

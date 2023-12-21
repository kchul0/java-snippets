package org.chulk.jsnippets;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest // Use this annotation for JPA repository tests
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Cleans the context between test methods
public class PersistenceTest {

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	@Transactional
	public void testDBA() throws InterruptedException {
		//List<Team> teams = teamRepo.findAll();
		//assert teams.isEmpty();

		Long time0 = System.nanoTime();

		teamRepo.saveAll(getTeams());
		
		Long time1 = System.nanoTime();
		System.err.println(((time1 - time0)/ 1_000_000));		
	}
	
	@Test
	@Transactional
	public void testDBB() {
		List<Team> teams = teamRepo.findAll();
		assert teams.isEmpty();

		Long time0 = System.nanoTime();
		int n = 0;
		
		for (Team t : getTeams()) {
			teamRepo.save(t);
			
			//if (n % 1000 == 0) {
			//	teamRepo.flush();
			//}
			
			n++;
		}
		
		Long time1 = System.nanoTime();

		System.err.println(((time1 - time0)/ 1_000_000));		
	}
	

	@Test
	@Transactional
	public void testDBBatchFetch() {
		Team team = new Team("Hello");
		
		team.getMembers().addAll(getMembers(team));
		teamRepo.save(team);
		
		System.out.println(team.getMembers());
		
		System.out.println(memberRepo.count());
		
		System.out.println(memberRepo.count());

		System.out.println("end");		
	}

	private List<Team> getTeams() {
		   return IntStream.rangeClosed(1, 1_000_000)
		   		.mapToObj(i -> new Team(UUID.randomUUID().toString()))
		   			.collect(Collectors.toList());
	}
	
	private List<Member> getMembers(final Team team) {
		   return IntStream.rangeClosed(1, 1000)
		   		.mapToObj(i -> new Member(UUID.randomUUID().toString(), team))
		   			.collect(Collectors.toList());
	}

}

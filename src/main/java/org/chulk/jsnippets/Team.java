package org.chulk.jsnippets;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@org.hibernate.annotations.BatchSize(size = 10)
public class Team {

	public Team(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Member> members = new ArrayList<>();

	public List<Member> getMembers() {
		return members;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

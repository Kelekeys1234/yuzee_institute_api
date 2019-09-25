package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "help_answer")
public class HelpAnswer implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 6922844940897956622L;
	private BigInteger id;
	private UserInfo user;
	private SeekaHelp seekaHelp;
	private String answer;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@Column(name = "answer")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "help_id")
	public SeekaHelp getSeekaHelp() {
		return seekaHelp;
	}

	public void setSeekaHelp(SeekaHelp seekaHelp) {
		this.seekaHelp = seekaHelp;
	}

}

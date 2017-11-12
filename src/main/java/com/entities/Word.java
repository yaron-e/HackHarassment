package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Word")
public class Word {
	
	public Word() {
		
	}
	
	public Word(String username, String word){
		this.username = username;
		this.word = word;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="wordID")
	private Integer wordID;
	
	@Column(name="username")
	private String username;
	
	@Column(name="word")
	private String word;
	
	@Column(name="platform")
	private String platform;
	
	public Integer getWordID() {
		return wordID;
	}

	public void setWordID(Integer wordID) {
		this.wordID = wordID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}	
}

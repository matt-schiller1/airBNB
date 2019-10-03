package com.banjo.transmittr.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="hashtags")
public class HashTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String hashtag;
	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(
		name = "transmits_hashtags",
		joinColumns = @JoinColumn(name = "hashtag_id"),
		inverseJoinColumns = @JoinColumn(name = "transmit_id")
	)
	private List<Transmit> transmits;
	public HashTag () {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public List<Transmit> getTransmits() {
		return transmits;
	}
	public void setTransmits(List<Transmit> transmits) {
		this.transmits = transmits;
	}
}

package com.banjo.transmittr.models;

import java.util.Date;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="transmits")
public class Transmit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Size(max = 140, message="Transmits cannot exceed 140 characters")
	private String content;
	private Integer upvote = 0;
	private Integer downvote = 0;
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "voted_users", 
            joinColumns = @JoinColumn(name = "transmit_id"), 
            inverseJoinColumns = @JoinColumn(name = "user_id")
        )
	private List<User> users;
	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(
		name = "transmits_hashtags",
		joinColumns = @JoinColumn(name = "transmit_id"),
		inverseJoinColumns = @JoinColumn(name = "hashtag_id")
	)
	private List<HashTag> hashtags;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
    private Date createdAt;
    private Date updatedAt;
    
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    public Transmit() {
    	
    }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUpvote() {
		return upvote;
	}
	public void setUpvote(Integer upvote) {
		this.upvote = upvote;
	}
	public Integer getDownvote() {
		return downvote;
	}
	public void setDownvote(Integer downvote) {
		this.downvote = downvote;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<HashTag> getHashtags() {
		return hashtags;
	}
	public void setHashtags(List<HashTag> hashtags) {
		this.hashtags = hashtags;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	
}

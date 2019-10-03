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
import javax.persistence.OneToMany;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min = 3, max = 50)
	private String name;
	@Size(min = 3, max = 15)
	private String username;
	private String bio;
	private String pic = "/img/img1.jpg";
	@Email(message="Email must be valid")
	private String email;
	@Size(min = 5)
	private String password;
	@Transient
	private String passwordConfirmation;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "voted_users", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "transmit_id")
    )
    private List<Transmit> transmit;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "follows",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name = "following_id")
	)
	private List<User> followings;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "follows",
		joinColumns = @JoinColumn(name="following_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> followers;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Transmit> transmits;
    private Date createdAt;
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    
    public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    public User() {
    	
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public List<Transmit> getTransmits() {
		return transmits;
	}
	public void setTransmits(List<Transmit> transmits) {
		this.transmits = transmits;
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
	public List<User> getFollowings() {
		return followings;
	}
	
	public void setFollowings(List<User> followings) {
		this.followings = followings;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public List<Transmit> getTransmit() {
		return transmit;
	}
	public void setTransmit(List<Transmit> transmit) {
		this.transmit = transmit;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}



}
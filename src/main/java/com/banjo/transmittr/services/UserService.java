package com.banjo.transmittr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banjo.transmittr.models.User;
import com.banjo.transmittr.repositories.RoleRepository;
import com.banjo.transmittr.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository uRepo;
	private RoleRepository rRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepository uRepo, RoleRepository rRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.uRepo = uRepo;
		this.rRepo = rRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	// Find All Users
	public List<User> allUsers(){
		return uRepo.findAll();
	}
	
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(rRepo.findByName("ROLE_USER"));
        uRepo.save(user);
    }
    
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(rRepo.findByName("ROLE_ADMIN"));
        uRepo.save(user);
    }    
    
    public void updateUser(User user){
        uRepo.save(user);
}
    public User findByUsername(String username) {
        return uRepo.findByUsername(username);
    }
    
    public User findUser(Long id) {
        Optional<User> optionalUser = uRepo.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }
    
    public List<User> getFollowing(String username) {
    	User user = uRepo.findByUsername(username);
    	return user.getFollowings();
    }
    
    public void followUser(User logged_user, User followed_user, boolean follow) {
    	List<User> addUsr = new ArrayList<>();
    	List<User> addFlwing = new ArrayList<>(); 
    	addUsr.add(logged_user);
    	addFlwing.add(followed_user);
    	if(follow) {
    		logged_user.getFollowings().add(followed_user);
    	} else {
    		logged_user.getFollowings().remove(followed_user);
    	}
    	
    }
}

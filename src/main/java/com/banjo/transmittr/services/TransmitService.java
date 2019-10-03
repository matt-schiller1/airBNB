package com.banjo.transmittr.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banjo.transmittr.models.Transmit;
import com.banjo.transmittr.models.User;
import com.banjo.transmittr.repositories.TransmitRepository;


@Service
public class TransmitService {
	private final TransmitRepository tRepo;

	
	public TransmitService(TransmitRepository tRepo) {
		this.tRepo = tRepo;

	}
	
	public List<Transmit> allTransmits(){
		return tRepo.findAll();
	}
	
    // retrieves a transmit
    public Transmit findTransmit(Long id) {
        Optional<Transmit> optionalTransmit = tRepo.findById(id);
        if(optionalTransmit.isPresent()) {
            return optionalTransmit.get();
        } else {
            return null;
        }
    }
    
	public Transmit createTransmit(Transmit t) {
		return tRepo.save(t);	
		
	}
	
	public List<Transmit> allPosts(Long id){
		return tRepo.joinUsersPosts(id);
	}
	
	public void addVote(Transmit transmit, User user, String voting) {	
		if (voting.equals("upvote")) {
		int BeforeVote = transmit.getUpvote();
			BeforeVote = BeforeVote + 1;
			transmit.setUpvote(BeforeVote);
			transmit.getUsers().add(user);
		} else if(voting.equals("downvote")){
			int Vote = transmit.getDownvote();
			int newVote = Vote -= 1;
			transmit.setDownvote(newVote);
			transmit.getUsers().add(user);
		} else if(voting.equals("undo")){
			int Vote = transmit.getUpvote();
			Vote = Vote - 1;
			transmit.setUpvote(Vote);
			transmit.getUsers().remove(user);
		} else if(voting.equals("undod")) {
			int Vote = transmit.getUpvote();
			Vote = Vote + 1;
			transmit.setDownvote(Vote);
			transmit.getUsers().remove(user);
		}
		tRepo.save(transmit);
	}
	

}
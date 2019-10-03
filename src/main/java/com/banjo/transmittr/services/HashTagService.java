package com.banjo.transmittr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banjo.transmittr.models.HashTag;
import com.banjo.transmittr.repositories.HashTagRepository;


@Service
public class HashTagService {
	private final HashTagRepository hRepo;

	
	public HashTagService(HashTagRepository hRepo) {
		this.hRepo = hRepo;

	}
	
	public void saveTag(HashTag t) {
		hRepo.save(t);
	}
	

	public HashTag findByHashtag(String hashtag) {
		return hRepo.findByHashtagEquals(hashtag);
}
	
	public HashTag findOrCreate(String s) {
		HashTag newTag = findByHashtag(s);
		if (newTag == null) {
			newTag = new HashTag();
			newTag.setHashtag(s);
			saveTag(newTag);
			
		}
		return newTag;	
	}
	
	public void addUniqueTag(List<HashTag> tags, HashTag tagToAdd) {
		for (HashTag tag: tags) {
			if (tag.getHashtag().equals(tagToAdd.getHashtag())) {
				return;
			}
		}
		tags.add(tagToAdd);
	}
}

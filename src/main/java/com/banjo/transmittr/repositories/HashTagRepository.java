package com.banjo.transmittr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banjo.transmittr.models.HashTag;
import com.banjo.transmittr.models.Transmit;

@Repository
public interface HashTagRepository extends CrudRepository<HashTag, Long>{
	List<HashTag> findAll();
	HashTag findByHashtagEquals(String hashtag);
	
}

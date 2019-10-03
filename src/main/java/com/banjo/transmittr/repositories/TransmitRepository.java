package com.banjo.transmittr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banjo.transmittr.models.Transmit;

@Repository
public interface TransmitRepository extends CrudRepository<Transmit, Long>{
	List<Transmit> findAll();	

	
@Query(value="SELECT distinct transmits.* FROM transmits LEFT JOIN follows ON transmits.user_id = follows.following_id WHERE (follows.user_id = ?1 OR transmits.user_id = ?1) ORDER BY transmits.created_at DESC", nativeQuery=true)
	List<Transmit> joinUsersPosts(Long id);


	
}

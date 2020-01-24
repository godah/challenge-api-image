package com.luciano.challenge.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.luciano.challenge.domain.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {
	public List<Image> findByFileNameContains(String keyword);
}

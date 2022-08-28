package com.example.hotelElla.repository;

import com.example.hotelElla.model.registration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface registerRepo extends MongoRepository<registration, String> {
}

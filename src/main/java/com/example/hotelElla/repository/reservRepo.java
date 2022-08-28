package com.example.hotelElla.repository;

import com.example.hotelElla.model.reservations;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface reservRepo extends MongoRepository<reservations, String> {
      Optional<reservations> findByRoomName(String roomName);
      Optional<reservations> findByCheckInDate(String checkInDate);
}


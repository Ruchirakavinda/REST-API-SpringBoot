package com.example.hotelElla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString

@Document(collection = "roomReservations")
public class reservations {
    private String fullName;
    @Id
    private String email;
    private String checkInDate;
    private int noOfDays;
    private String roomName;
    private Date reservedAt;
    private Date updatedAt;
}

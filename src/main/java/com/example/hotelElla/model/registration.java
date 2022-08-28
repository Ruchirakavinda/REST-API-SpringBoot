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

@Document(collection = "userRegistration")
public class registration {
    private String fullName;
    @Id
    private String email;
    private int mobNo;
    private String password;
    private String nic;
    private String address;
    private Date registeredAt;
    private Date updatedAt;

}

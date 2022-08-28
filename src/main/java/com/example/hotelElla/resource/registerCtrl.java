package com.example.hotelElla.resource;

import com.example.hotelElla.model.registration;
import com.example.hotelElla.repository.registerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class registerCtrl {

    @Autowired
    private registerRepo repository;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody registration adduser){
        Optional<registration> getUserOptional = repository.findById(adduser.getEmail());
        if(!getUserOptional.isPresent()) {
            try {
                adduser.setRegisteredAt(new Date(System.currentTimeMillis()));
                repository.save(adduser);
                return new ResponseEntity<>("User registered with email : " + adduser.getEmail(), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("User not registered", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("User already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<?> getAllUsers(){
        List<registration> allUsers = repository.findAll();
        if(allUsers.size()>0){
            return new ResponseEntity<List<registration>>(allUsers,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No users available",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAllUsers/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        Optional<registration> getUserOptional = repository.findById(email);
        if(getUserOptional.isPresent()){
            return new ResponseEntity<>(getUserOptional.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User Not Found with email : "+email,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody registration updateuser){
        Optional<registration> updateUserOptional = repository.findById(email);
        if(updateUserOptional.isPresent()){
            registration detailsToUpdate = updateUserOptional.get();
            detailsToUpdate.setFullName(updateuser.getFullName() != null ? updateuser.getFullName() : detailsToUpdate.getFullName());
            detailsToUpdate.setEmail(updateuser.getEmail() != null ? updateuser.getEmail() : detailsToUpdate.getEmail());
            detailsToUpdate.setMobNo(updateuser.getMobNo() != 0 ? updateuser.getMobNo() : detailsToUpdate.getMobNo());
            detailsToUpdate.setPassword(updateuser.getPassword() != null ? updateuser.getPassword() : detailsToUpdate.getPassword());
            detailsToUpdate.setNic(updateuser.getNic() != null ? updateuser.getNic() : detailsToUpdate.getNic());
            detailsToUpdate.setAddress(updateuser.getAddress() != null ? updateuser.getAddress() : detailsToUpdate.getAddress());
            detailsToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            repository.save(detailsToUpdate);
            return new ResponseEntity<>("Update Successfull of "+detailsToUpdate,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User not found with email : "+email,HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        try {
            repository.deleteById(email);
            return new ResponseEntity<>("Successfully deleted User with email : "+email,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("User not found with email : "+email,HttpStatus.NOT_FOUND);
        }
    }

}

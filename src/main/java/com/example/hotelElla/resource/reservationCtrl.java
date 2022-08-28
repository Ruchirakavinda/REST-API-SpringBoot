package com.example.hotelElla.resource;

import com.example.hotelElla.model.reservations;
import com.example.hotelElla.model.registration;
import com.example.hotelElla.repository.registerRepo;
import com.example.hotelElla.repository.reservRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class reservationCtrl {
    @Autowired
    private reservRepo repositoryReserve;
    @Autowired
    private registerRepo repository;

    @PostMapping("/makeReservation")
    public ResponseEntity<?> makeReservation(@RequestBody reservations makereservation){
        Optional<registration> getRegistrationOptional = repository.findById(makereservation.getEmail());
        if(getRegistrationOptional.isPresent()) {
            Optional<reservations> getCheckInDateOptional = repositoryReserve.findByCheckInDate(makereservation.getCheckInDate());
            Optional<reservations> getRoomNameOptional = repositoryReserve.findByRoomName(makereservation.getRoomName());
            if(!getCheckInDateOptional.isPresent() || !getRoomNameOptional.isPresent() ) {
                try {
                    makereservation.setReservedAt(new Date(System.currentTimeMillis()));
                    repositoryReserve.save(makereservation);
                    return new ResponseEntity<>("Reservation successful for user : " + makereservation.getEmail(), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>("Reservation unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                return new ResponseEntity<>("Fully Booked, Please choose Another Date or Room", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("Please Sign Up before make reservation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @GetMapping("/findAllReservations")
    public ResponseEntity<?> getAllReservations(){
        List<reservations> allReservations = repositoryReserve.findAll();
        if(allReservations.size()>0){
            return new ResponseEntity<List<reservations>>(allReservations,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Reservations available",HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/findAllReservations/{email}")
    public ResponseEntity<?> getReservation(@PathVariable String email){
        Optional<reservations> getReservationOptional = repositoryReserve.findById(email);
        if(getReservationOptional.isPresent()){
            return new ResponseEntity<>(getReservationOptional.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Reservations Not Found with email : "+email,HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/updateReservation/{email}")
    public ResponseEntity<?> updateReservation(@PathVariable String email, @RequestBody reservations updatereservation){
        Optional<reservations> updateReservationOptional = repositoryReserve.findById(email);
        if(updateReservationOptional.isPresent()){
            reservations detailsToUpdate = updateReservationOptional.get();
            detailsToUpdate.setFullName(updatereservation.getFullName() != null ? updatereservation.getFullName() : detailsToUpdate.getFullName());
            detailsToUpdate.setEmail(updatereservation.getEmail() != null ? updatereservation.getEmail() : detailsToUpdate.getEmail());
            detailsToUpdate.setCheckInDate(updatereservation.getCheckInDate() != null ? updatereservation.getCheckInDate() : detailsToUpdate.getCheckInDate());
            detailsToUpdate.setNoOfDays(updatereservation.getNoOfDays() != 0 ? updatereservation.getNoOfDays() : detailsToUpdate.getNoOfDays());
            detailsToUpdate.setRoomName(updatereservation.getRoomName() != null ? updatereservation.getRoomName() : detailsToUpdate.getRoomName());
            detailsToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            repositoryReserve.save(detailsToUpdate);
            return new ResponseEntity<>("Update Successfull of "+detailsToUpdate,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Reservation not found with email : "+email,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteReservation/{email}")
    public ResponseEntity<?> deleteReservation(@PathVariable String email){
        try {
            repositoryReserve.deleteById(email);
            return new ResponseEntity<>("Successfully deleted Reservation with email : "+email,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Reservation not found with email : "+email,HttpStatus.NOT_FOUND);
        }
    }

}

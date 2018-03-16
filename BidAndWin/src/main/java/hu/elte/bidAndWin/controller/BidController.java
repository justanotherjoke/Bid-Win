package hu.elte.bidAndWin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

import hu.elte.bidAndWin.service.BidService;

@RestController
@RequestMapping(value = "/bid")
public class BidController {
	
	@Autowired
    private BidService bidService;

/*	@Role({ADMIN, USER})
    @GetMapping(value = "/bids/all")
    public ResponseEntity<List<Reservation>> getAllReservation() {
        return ResponseEntity.ok(reservationService.getAllReservation(userService.getLoggedInUser()));

    }*/
}

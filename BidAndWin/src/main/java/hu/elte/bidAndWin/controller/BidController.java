package hu.elte.bidAndWin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

import java.util.List;

import hu.elte.bidAndWin.service.BidService;
import hu.elte.bidAndWin.service.UserService;
import hu.elte.bidAndWin.annotation.Role;
import hu.elte.bidAndWin.domain.Bid;

@RestController
@RequestMapping(value = "/bids")
public class BidController {
	
	@Autowired
    private BidService bidService;
	
	@Autowired
	private UserService userService;


	@Role({ADMIN, USER})
    @GetMapping(value = "/all")
    public ResponseEntity<List<Bid>> getAllBids() {
        return ResponseEntity.ok(bidService.getAllBids());
    }
	
	@Role({ADMIN, USER})
    @GetMapping(value = "/myBids")
    public ResponseEntity<List<Bid>> getMyBids() {
        return ResponseEntity.ok(bidService.getMyBids(userService.getLoggedInUser()));
    }
}

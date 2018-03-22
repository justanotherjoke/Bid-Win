package hu.elte.bidAndWin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

import java.util.List;

import hu.elte.bidAndWin.service.BidService;
import hu.elte.bidAndWin.service.UserNotValidException;
import hu.elte.bidAndWin.service.UserService;
import hu.elte.bidAndWin.annotation.Role;
import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;

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
		if(userService.getLoggedInUser() != null) {
        return ResponseEntity.ok(bidService.getMyBids(userService.getLoggedInUser()));
		}
		else {
			System.out.println("nem bejelentkezett felhasznalo");
            return ResponseEntity.badRequest().build();
        }
    }
	
	@Role({ADMIN, USER})
    @GetMapping(value = "/{id}")
    public ResponseEntity<Bid> getBid(@PathVariable(value = "id") long id) {
		return ResponseEntity.ok(bidService.getBid(id, userService.getLoggedInUser()));
    }
	
	@Role({ADMIN, USER} )
    @PutMapping("/{id}")
    private ResponseEntity<Bid> updateBid(@PathVariable long id, @RequestBody Bid bid) {
        Bid updated;
        try {
            updated = bidService.updateBid(id, bid, userService.getLoggedInUser());
            return ResponseEntity.ok(updated);
        } catch (UserNotValidException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
	
	
}

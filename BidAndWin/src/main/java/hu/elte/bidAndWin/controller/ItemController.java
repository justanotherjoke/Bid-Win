package hu.elte.bidAndWin.controller;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.annotation.Role;
import hu.elte.bidAndWin.service.ItemService;
import hu.elte.bidAndWin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;
    
    
	@Role({ADMIN, USER})
    @PostMapping("/createItem")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.createItem(item, userService.getLoggedInUser()));
    }
	
	@Role({ADMIN, USER})
    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> getBid(@PathVariable(value = "id") long id) {
		return ResponseEntity.ok(itemService.getItem(id, userService.getLoggedInUser()));
    }
	
	
	
    
}

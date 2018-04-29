package hu.elte.bidAndWin.controller;

import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.bidAndWin.annotation.Role;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.service.ItemNotValidException;
import hu.elte.bidAndWin.service.ItemService;
import hu.elte.bidAndWin.service.UserNotValidException;
import hu.elte.bidAndWin.service.UserService;

@RestController
@RequestMapping(value = "/api/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private UserService userService;

	@Role({ADMIN, USER})
	@PostMapping("/createItem")
	public ResponseEntity<List<Item>> createItem(@RequestBody Item item) {
		try {
			return ResponseEntity.ok(itemService.createItem(item, userService.getLoggedInUser()));
		} catch (ItemNotValidException | UserNotValidException | NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Role({ADMIN, USER})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Item> getItem(@PathVariable(value = "id") long id) {
		try {
			return ResponseEntity.ok(itemService.getItem(id, userService.getLoggedInUser()));
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Role({ADMIN, USER})
	@GetMapping("/all")
	public ResponseEntity<List<Item>> getAllItems() {
		return ResponseEntity.ok(itemService.getAllItems());
	}

	@Role({ADMIN, USER})
	@GetMapping("/myitems")
	public ResponseEntity<List<Item>> getMyItems() {
		try {
			return ResponseEntity.ok(itemService.getMyItems(userService.getLoggedInUser()));
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Role({ADMIN, USER})
	@PutMapping("/{id}")
	private ResponseEntity<Item> updateItem(@PathVariable long id, @RequestBody Item item) {
		try {

			return ResponseEntity.ok(itemService.updateItem(id, item, userService.getLoggedInUser()));

		} catch (ItemNotValidException | UserNotValidException | NullPointerException e) {

			return ResponseEntity.badRequest().build();
		}
		//System.out.println("itemnotvalid"); // frontenden akarjuk külön kezelni a hiábkat?
		//System.out.println("usernatvalid"); // frontenden akarjuk külön kezelni a hiábkat?

		//System.out.println("itemnotvalid"); // frontenden akarjuk külön kezelni a hiábkat?
		//System.out.println("usernatvalid"); // frontenden akarjuk külön kezelni a hiábkat?
	}
	/*       Item updated;
        try {
            updated = itemService.updateItem(id, item, userService.getLoggedInUser());
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }*/

}

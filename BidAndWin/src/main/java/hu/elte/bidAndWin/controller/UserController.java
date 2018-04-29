package hu.elte.bidAndWin.controller;

import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.service.UserNotValidException;
import hu.elte.bidAndWin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("")
	public ResponseEntity<User> user() {
		if (userService.isLoggedIn()) {
			return ResponseEntity.ok(
				userService.getLoggedInUser());
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		try {
			return ResponseEntity.ok(userService.register(user));
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		try {
			return ResponseEntity.ok(userService.login(user));
		} catch (UserNotValidException | NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<User> logout(@RequestBody User user) {
		return ResponseEntity.ok(userService.logout());
	}
}

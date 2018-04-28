package hu.elte.bidAndWin.controller;

import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hu.elte.bidAndWin.annotation.Role;
import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.service.CategoryNotValidException;
import hu.elte.bidAndWin.service.CategoryService;
import hu.elte.bidAndWin.service.ImageService;
import hu.elte.bidAndWin.service.ItemNotValidException;
import hu.elte.bidAndWin.service.ItemService;
import hu.elte.bidAndWin.service.UserNotValidException;
import hu.elte.bidAndWin.service.UserService;

import static hu.elte.bidAndWin.domain.User.Role.ADMIN;
import static hu.elte.bidAndWin.domain.User.Role.USER;

@RestController
@RequestMapping(value = "/api/image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserService userService;

	@Role({ ADMIN, USER })
	@PostMapping("/uploadimage")
	public ResponseEntity<Image> createImage(@RequestParam(value = "itemId") long id,
			@RequestParam(value = "file") MultipartFile file) {
		try {
			return ResponseEntity.ok(imageService.uploadImage(file, id, userService.getLoggedInUser()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Role({ ADMIN, USER })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Image> getImageByItemId(@PathVariable(value = "id") long id) {
		try {
			return ResponseEntity.ok(imageService.getImageByItemId(id, userService.getLoggedInUser()));
		} catch (UserNotValidException e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@Role({ ADMIN, USER })
	@GetMapping("/all")
	public ResponseEntity<List<Image>> getAllImages() {
		return ResponseEntity.ok(imageService.getAllImages());
	}

}

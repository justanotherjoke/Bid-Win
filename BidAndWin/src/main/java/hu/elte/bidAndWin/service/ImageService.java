package hu.elte.bidAndWin.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ImageRepository;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;

@Service
public class ImageService {

	private ImageRepository imageRepository;

	private UserRepository userRepository;

	private ItemRepository itemRepository;

	@Autowired
	public ImageService(ImageRepository imageRepository, UserRepository userRepository, ItemRepository itemRepository) {
		this.imageRepository = imageRepository;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}

	public List<Image> getImagesByItemId(long id, User loggedInUser) {
		return imageRepository.findAllByItemId(id);
	}
	
	public List<Image> getAllImages() {
		return imageRepository.findAll();
	}

//	public Image uploadImage(MultipartFile file, long id, User user)
//			throws IOException, UserNotValidException, ItemNotValidException {
//		Item it = itemRepository.findById(id);
//		if (it == null) {
//			System.out.println("nincs ilyen item");
//			throw new ItemNotValidException();
//		}
//		System.out.println(user.getId() + "hey");
//		if (it.getUser().getId() != user.getId()) {
//			System.out.println("a bejelentkezett userhez nem tartozik ilyen item");
//			throw new UserNotValidException();
//		}
//		Image im = new Image();
//		im.setPic(file.getBytes());
//		im.setItem(itemRepository.findById(id));
//
//		return imageRepository.save(im);
//	}
	
	public Image uploadImage(MultipartFile file, long id, User user)
			throws IOException, UserNotValidException, ItemNotValidException {
		Image im = imageRepository.findById(id);
		if (im == null) {
			System.out.println("nincs ilyen image");
			throw new ItemNotValidException();
		}
		System.out.println(user.getId() + "hey");
		Item it = itemRepository.findByImageId(id);
		if (it.getUser().getId() != user.getId()) {
			System.out.println("a bejelentkezett userhez nem tartozik ilyen item");
			throw new UserNotValidException();
		}

		im.setPic(file.getBytes());
		//im.setItem(itemRepository.findById(id));

		return imageRepository.save(im);
	}

}

package hu.elte.bidAndWin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
	
	public List<Image> getImagesByItemId(long id, User loggedInUser){
		return imageRepository.findAllByItemId(id);
	}

/*	public Image uploadImage(Image image, User user) throws IOException, UserNotValidException {
		if(image.getItem().getId() != user.getId()) {
			throw new UserNotValidException();
		}
		System.out.println(image.getPath());
		System.out.println(image.getItem());
		ClassPathResource backImgFile = new ClassPathResource(image.getPath());
		//ClassPathResource backImgFile = new ClassPathResource("C:/Users/x230/Pictures/images.jpg");
		byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
		backImgFile.getInputStream().read(arrayPic);
		Image blackImage = new Image(arrayPic, image.getPath(), image.getItem());
		
		return imageRepository.save(blackImage);
	}*/
	
	/*
	public Image uploadImage() throws IOException {
		ClassPathResource backImgFile = new ClassPathResource("images/Cat.jpg");
		//ClassPathResource backImgFile = new ClassPathResource("C:/Users/x230/Pictures/images.jpg");
		byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
		backImgFile.getInputStream().read(arrayPic);
		Image blackImage = new Image(arrayPic);
		
		return imageRepository.save(blackImage);
	}*/
	
	public Image uploadImage(MultipartFile file, long id, User user) throws IOException, UserNotValidException, ItemNotValidException {
		Item it = itemRepository.findById(id);
		if(it == null) {
			System.out.println("nincs ilyen item");
			throw new ItemNotValidException();
		}
		System.out.println(user.getId() + "hey");
	    if(it.getUser().getId() != user.getId() ){
	    	System.out.println("a bejelentkezett userhez nem tartozik ilyen item");
			throw new UserNotValidException();
		}
		Image im= new Image();
		im.setPic(file.getBytes()); 
		im.setItem(itemRepository.findById(id));
		
		return imageRepository.save(im);
	}
	
	
}

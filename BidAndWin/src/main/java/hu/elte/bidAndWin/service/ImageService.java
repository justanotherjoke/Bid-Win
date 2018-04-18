package hu.elte.bidAndWin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ImageRepository;
import hu.elte.bidAndWin.repository.UserRepository;

@Service
public class ImageService {
	
	private ImageRepository imageRepository;
	
	private UserRepository userRepository;

	@Autowired
	public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
		this.imageRepository = imageRepository;
		this.userRepository = userRepository;
	}
	
	public List<Image> getImagesByItemId(long id, User loggedInUser){
		return imageRepository.findAllByItemId(id);
	}

	public Image uploadImage(Image image, User user) throws IOException, UserNotValidException {
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
	}
	
	/*
	public Image uploadImage() throws IOException {
		ClassPathResource backImgFile = new ClassPathResource("images/Cat.jpg");
		//ClassPathResource backImgFile = new ClassPathResource("C:/Users/x230/Pictures/images.jpg");
		byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
		backImgFile.getInputStream().read(arrayPic);
		Image blackImage = new Image(arrayPic);
		
		return imageRepository.save(blackImage);
	}*/

	
	
}

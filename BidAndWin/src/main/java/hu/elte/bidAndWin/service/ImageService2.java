package hu.elte.bidAndWin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Image2;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ImageRepository2;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor = @__(
	@Autowired))
public class ImageService2 {

	private ImageRepository2 imageRepository;
	private UserRepository userRepository;
	private ItemRepository itemRepository;

	public Image2 getImageByItemId(long id, @NonNull User loggedInUser) throws UserNotValidException {

		@NonNull
		Image2 im = imageRepository.findByItemId(id);

		if (im.getItem().getUser().getId() == loggedInUser.getId()) {
			return imageRepository.findByItemId(id);
		} else {
			throw new UserNotValidException();
		}
	}

	public List<Image2> getAllImages() {
		return imageRepository.findAll();
	}

	public Image2 uploadImage(@NonNull Image2 image, @NonNull User user)
		throws IOException, UserNotValidException {
		

		@NonNull
		Item it = itemRepository.findById(image.getItem().getId());

		if (it.getUser().getId() != user.getId()) {
			//System.out.println("a bejelentkezett userhez nem tartozik ilyen item");
			throw new UserNotValidException();
		}

		try {
			@NonNull
			Image2 image2 = imageRepository.findByItemId(image.getItem().getId());

			image2.setPic(image.getPic());
			return imageRepository.save(image);

		} catch (NullPointerException e) {
			Image2 im = new Image2();
			im.setPic(image.getPic());
			im.setItem(itemRepository.findById(image.getItem().getId()));
			return imageRepository.save(im);
		}
	}
}

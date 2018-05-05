package hu.elte.bidAndWin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import hu.elte.bidAndWin.repository.ImageRepository;

@Service
@AllArgsConstructor(onConstructor = @__(
	@Autowired))
public class ImageService {

	private ImageRepository imageRepository;
	private UserRepository userRepository;
	private ItemRepository itemRepository;

	public Image getImageByItemId(long id, @NonNull User loggedInUser) throws UserNotValidException {

		@NonNull
		Image im = imageRepository.findByItemId(id);

		if (im.getItem().getUser().getId() == loggedInUser.getId()) {
			return imageRepository.findByItemId(id);
		} else {
			throw new UserNotValidException();
		}
	}

	public List<Image> getAllImages() {
		return imageRepository.findAll();
	}

	public Image uploadImage(@NonNull Image image, @NonNull User user)
		throws IOException, UserNotValidException {
		

		@NonNull
		Item it = itemRepository.findById(image.getItem().getId());

		if (it.getUser().getId() != user.getId()) {
			//System.out.println("a bejelentkezett userhez nem tartozik ilyen item");
			throw new UserNotValidException();
		}

		try {
			@NonNull
			Image image2 = imageRepository.findByItemId(image.getItem().getId());

			image2.setPic(image.getPic());
			return imageRepository.save(image);

		} catch (NullPointerException e) {
			Image im = new Image();
			im.setPic(image.getPic());
			im.setItem(itemRepository.findById(image.getItem().getId()));
			return imageRepository.save(im);
		}
	}
}

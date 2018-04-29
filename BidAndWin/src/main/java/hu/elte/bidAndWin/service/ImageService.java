package hu.elte.bidAndWin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ImageRepository;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

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

	public Image uploadImage(@NonNull MultipartFile file, long id, @NonNull User user)
		throws IOException, UserNotValidException {

		@NonNull
		Item it = itemRepository.findById(id);

		//System.out.println(user.getId() + "hey");
		if (it.getUser().getId() != user.getId()) {
			//System.out.println("a bejelentkezett userhez nem tartozik ilyen item");
			throw new UserNotValidException();
		}

		try {
			@NonNull
			Image image = imageRepository.findByItemId(id);

			image.setPic(file.getBytes());
			return imageRepository.save(image);

		} catch (NullPointerException e) {
			Image im = new Image();
			im.setPic(file.getBytes());
			im.setItem(itemRepository.findById(id));
			return imageRepository.save(im);
		}
	}
}

package hu.elte.bidAndWin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.CategoryRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(
	@Autowired))
public class CategoryService {

	private CategoryRepository categoryRepository;
	private UserRepository userRepository;

	public List<Category> findAll() {
		List<Category> allCategory = (List<Category>) categoryRepository.findAll();
		return allCategory;
	}

	public List<Category> createCategory(Category category, User user) throws UserNotValidException, CategoryNotValidException {
		if (!user.getRole().toString().equals("ADMIN")) {
			throw new UserNotValidException();
		}

		List<Category> allCategory = findAll();
		for (Category cat : allCategory) {
			if (cat.getName().equals(category.getName())) {
				throw new CategoryNotValidException();
			}
		}

		categoryRepository.save(category);

		return findAll();
	}

}

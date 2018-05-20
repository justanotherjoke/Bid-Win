package hu.elte.bidAndWin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.bidAndWin.domain.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}

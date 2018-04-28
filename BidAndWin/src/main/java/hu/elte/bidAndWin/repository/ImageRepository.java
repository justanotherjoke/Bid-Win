package hu.elte.bidAndWin.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.elte.bidAndWin.domain.Image;
 
 
public interface ImageRepository extends JpaRepository<Image, Long>{
	List<Image> findAllByItemId(long id);
	
	Image findById(long id);
}
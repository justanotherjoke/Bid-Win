package hu.elte.bidAndWin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.elte.bidAndWin.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByItemId(long id);
}

package hu.elte.bidAndWin.service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ImageRepository;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import java.sql.Timestamp;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestImageService {
    
//
//	@Test(expected = UserNotValidException.class)
//	public void testgetImageByItemId_UserNotValidExceptionThrown() throws UserNotValidException {
//		doReturn(imageNotEmpty).when(imageRepository).findByItemId(1);
//		imageService.getImageByItemId(1, userAdminSpy);
//	}
//
//	@Test
//	public void testgetImageByItemId_ReturnImage() throws UserNotValidException {
//		doReturn(imageNotEmpty).when(imageRepository).findByItemId(1);
//		assertEquals(imageService.getImageByItemId(1, userNotEmptySpy), imageNotEmpty);
//	}
//
//	@Test
//	public void testGetAllImages_ReturnImageList() {
//		doReturn(listImageEmpty).when(imageRepository).findAll();
//		assertEquals(imageService.getAllImages(), listImageEmpty);
//	}
}
//to do: test nulls
//to do: cleanup unnecesary shit
//todo: image.getitembyimage?
//todo: test imagesrevice.uploadimage

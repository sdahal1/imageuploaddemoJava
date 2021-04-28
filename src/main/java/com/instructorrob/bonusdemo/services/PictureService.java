package com.instructorrob.bonusdemo.services;

import org.springframework.stereotype.Service;

import com.instructorrob.bonusdemo.models.Picture;
import com.instructorrob.bonusdemo.models.User;
import com.instructorrob.bonusdemo.repositories.PictureRepository;


@Service
public class PictureService {
	private final PictureRepository pRepo;
	
	public PictureService(PictureRepository pRepo) {
		this.pRepo= pRepo;
	}
	
	//add a new picture to the db
	public void uploadPic(User user, String url, String desc ) {
		Picture newPic = new Picture(url, desc, user);
		this.pRepo.save(newPic);
	}
	
	
	
	

}

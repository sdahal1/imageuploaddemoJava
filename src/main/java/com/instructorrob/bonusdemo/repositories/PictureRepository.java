package com.instructorrob.bonusdemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.instructorrob.bonusdemo.models.Picture;


@Repository
public interface PictureRepository extends CrudRepository<Picture, Long> {

}

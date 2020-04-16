package com.lustermaniacs.companion.repository;

import com.lustermaniacs.companion.models.Picture;
import com.lustermaniacs.companion.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    void deleteByNameAndProfile(String name, Profile profile);
}

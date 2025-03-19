package com.example.jwt.infra.repositories;

import com.example.jwt.entities.ImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImagesEntity, Integer> {
    @Query("SELECT MAX(i.position) from images i")
    Byte getMaxPosition();
}

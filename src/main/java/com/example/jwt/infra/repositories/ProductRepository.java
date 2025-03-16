package com.example.jwt.infra.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jwt.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Short> {
    @Query(value = "select * from products where category_id = " +
            "(select id from categories where slug = :slug)", nativeQuery = true)
    Page<ProductEntity> findByCategory(@Param("slug") String slug, Pageable pageable);

}

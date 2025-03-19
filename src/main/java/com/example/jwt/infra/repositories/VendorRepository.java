package com.example.jwt.infra.repositories;

import com.example.jwt.entities.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Byte> {
}

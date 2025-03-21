package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.entity.AdmissionForm;

@Repository
public interface AdmissionFormRepository extends JpaRepository<AdmissionForm, String> {
//	Optional<AdmissionForm> findById(@Param("id") String id);
}


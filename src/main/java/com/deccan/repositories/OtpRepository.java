package com.deccan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deccan.entity.OtpDetails;

@Repository
public interface OtpRepository extends JpaRepository<OtpDetails, Integer> {
	public OtpDetails findTopByUserIdOrderByGeneratedOnDesc(int userId);
}

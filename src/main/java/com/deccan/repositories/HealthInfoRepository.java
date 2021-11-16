package com.deccan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deccan.entity.HealthInfo;
@Repository
public interface HealthInfoRepository extends JpaRepository<HealthInfo, Integer> {

	public HealthInfo findByUserId(int userId);

}

package com.deccan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deccan.entity.Address;
import com.deccan.entity.HealthInfo;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Optional<Address>> findByUserId(int userId);
}

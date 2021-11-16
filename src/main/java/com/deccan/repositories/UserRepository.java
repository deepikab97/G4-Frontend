package com.deccan.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.deccan.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>, JpaRepository<User, Integer> {
	public User findByEmail(String email);

	public Page<User> findAllByAccountNonLocked(boolean accountStatus, Pageable paging);

	public List<User> findByRole(Enum role);

}

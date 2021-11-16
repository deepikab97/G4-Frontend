package com.deccan.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccan.dto.CommentsDto;
import com.deccan.entity.Plan;
import com.deccan.entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

	public Reviews findByPlanIdAndUserId(int planId, int userId);

	@Query(value = "select new com.deccan.dto.CommentsDto(u.firstName, "
			+ "u.lastName,r.isCommented, r.comment,r.commentedOn) from Reviews r inner join com.deccan.entity.User u "
			+ "on u.id=r.user where r.plan=?1", nativeQuery = false)
	public List<CommentsDto> getCommentsByPlanId(Plan plan);

	public Reviews findByPlanIdAndUserIdAndIsCommented(int planId, int userId, boolean isCommented);
}
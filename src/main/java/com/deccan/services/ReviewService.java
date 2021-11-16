package com.deccan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deccan.dto.CommentsDto;
import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.PlanDto;
import com.deccan.dto.ReviewDto;
import com.deccan.entity.Plan;
import com.deccan.entity.Reviews;
import com.deccan.enums.StatusType;
import com.deccan.repositories.EnrollmentRepository;
import com.deccan.repositories.PlanRepository;
import com.deccan.repositories.ReviewsRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.utils.ModelMapperConfig;

@Service
public class ReviewService implements IReviewService {

	@Autowired
	ReviewsRepository reviewRepository;

	@Autowired
	PlanRepository planRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Override
	public String comment(ReviewDto reviewDto, int userId) {
		List<Reviews> reviews = reviewRepository.findAll();
		Plan plan = planRepository.getOne(reviewDto.getPlanId());
		if (reviews.stream().anyMatch(
				r -> r.getPlan().getId() == reviewDto.getPlanId() && r.getUser().getId() == userId && r.isCommented()))
			return "Already commented";

		Reviews review = reviews.stream().filter(r->r.getPlan().getId()==reviewDto.getPlanId() && r.getUser().getId() == userId).findAny().get();
		BeanUtils.copyProperties(reviewDto, review);
		plan.setCommentsCounter(plan.getCommentsCounter() + 1);
		review.setPlan(plan);
		review.setUser(userRepository.getOne(userId));
		review.setCommentedOn(LocalDateTime.now());
		review.setCommented(true);
		reviewRepository.save(review);
		planRepository.save(plan);
		return "comment added.";
	}

	@Override
	public PlanDto like(ReviewDto reviewDto, int userId) {
		Reviews review = reviewRepository.findByPlanIdAndUserId(reviewDto.getPlanId(), userId);
		Plan plan = planRepository.getOne(reviewDto.getPlanId());
		PlanDto planDto = new PlanDto();
		if (review == null) {
			Reviews reviewPlan = new Reviews();
			reviewPlan.setPlan(plan);
			reviewPlan.setUser(userRepository.getOne(userId));
			reviewPlan.setLiked(true);
			plan.setLikesCounter(plan.getLikesCounter() + 1);
			reviewRepository.save(reviewPlan);

			BeanUtils.copyProperties(planRepository.save(plan), planDto);
			planDto.setLikesCount(plan.getLikesCounter());
			return planDto;
		}

		if (!review.isLiked()) {
			review.setLiked(true);
			plan.setLikesCounter(plan.getLikesCounter() + 1);
		} else {
			review.setLiked(false);
			plan.setLikesCounter(plan.getLikesCounter() - 1);
		}
		reviewRepository.save(review);
		BeanUtils.copyProperties(planRepository.save(plan), planDto);
		planDto.setLikesCount(plan.getLikesCounter());
		return planDto;
	}

	@Override
	public List<CommentsDto> getAllComments(int planId) {

		return reviewRepository.getCommentsByPlanId(planRepository.getOne(planId)).stream().filter(r -> r.isCommented())
				.collect(Collectors.toList());
	}

	@Override
	public List<EnrollmentDto> getCompletedPlans(int userId) {
		return enrollmentRepository.getByStatusAndUserId(StatusType.COMPLETED, userRepository.getOne(userId));

	}

	@Override
	public CommentsDto getMemberCommentByPlan(ReviewDto reviewDto, int userId) {
		CommentsDto commentDto = new CommentsDto();
		Reviews review = reviewRepository.findByPlanIdAndUserIdAndIsCommented(reviewDto.getPlanId(), userId, true);
		commentDto.setComment(review.getComment());
		commentDto.setCommentedOn(review.getCommentedOn());
		commentDto.setFirstName(review.getUser().getFirstName());
		commentDto.setLastName(review.getUser().getLastName());
		return commentDto;
	}


}

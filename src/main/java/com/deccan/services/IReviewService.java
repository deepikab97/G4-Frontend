package com.deccan.services;

import java.util.List;

import com.deccan.dto.CommentsDto;
import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.PlanDto;
import com.deccan.dto.ReviewDto;

public interface IReviewService {
	public String comment(ReviewDto reviewDto, int userId);

	public PlanDto like(ReviewDto reviewDto, int userId);

	public List<CommentsDto> getAllComments(int planId);

	public List<EnrollmentDto> getCompletedPlans(int userId);

	public CommentsDto getMemberCommentByPlan(ReviewDto reviewDto, int userId);

}
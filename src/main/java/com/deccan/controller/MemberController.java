package com.deccan.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deccan.dto.CommentsDto;
import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.PlanDto;
import com.deccan.dto.ReviewDto;
import com.deccan.dto.ViewReceiptDto;
import com.deccan.entity.Batch;
import com.deccan.exceptions.CustomException;
import com.deccan.services.IBatchService;
import com.deccan.services.IEnrollmentService;
import com.deccan.services.IPlanService;
import com.deccan.services.IReviewService;
import com.deccan.services.IUserService;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private IPlanService planService;

	@Autowired
	private IReviewService reviewService;

	@Autowired
	private IEnrollmentService enrollmentService;

	public static final Logger logger = LogManager.getLogger(MemberController.class.getName());

	// pagination implementation
	@GetMapping("/plan/{id}") // params = { "page", "limit","id" }
	public ResponseEntity<Page<PlanDto>> getPlansForMember(@PathVariable int id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		if (planService.getPlansForMember(id, pageNo, pageSize).hasContent()) {
			logger.info("Displayed plans for which Member having ID: " + id + " is not enrolled");
			return new ResponseEntity<Page<PlanDto>>(planService.getPlansForMember(id, pageNo, pageSize),
					HttpStatus.OK);
		}

		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	// Enrollment Activity
	@GetMapping("/enrollment/{id}")
	ResponseEntity<Page<EnrollmentDto>> memberEnrollments(@PathVariable int id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize)
			throws CustomException {

		if (enrollmentService.getMemberEnrollment(id, pageNo, pageSize).hasContent())
			return new ResponseEntity<Page<EnrollmentDto>>(enrollmentService.getMemberEnrollment(id, pageNo, pageSize),
					HttpStatus.OK);
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/enroll-user")
	public String enrollUser(@RequestBody EnrollmentDto enrollmentDto) {
		return enrollmentService.enrollUser(enrollmentDto);
	}

	@GetMapping("/find-enrollment/{id}")
	public ResponseEntity<EnrollmentDto> findEnrollment(@PathVariable int id) throws CustomException {
		return new ResponseEntity<>(enrollmentService.findEnrollment(id), HttpStatus.OK);
	}

	@PutMapping("/update-enrollment")
	public ResponseEntity<String> updateEnrollment(@RequestBody EnrollmentDto enrollmentDto) throws CustomException {
		return new ResponseEntity<>(enrollmentService.updateEnrollment(enrollmentDto), HttpStatus.OK);
	}

	@GetMapping("/view-receipt/{id}")
	public ResponseEntity<ViewReceiptDto> viewReceipt(@PathVariable int id) throws CustomException {
		return new ResponseEntity<>(enrollmentService.viewReceipt(id), HttpStatus.OK);
	}

	@GetMapping("/feedbacks/{userId}")
	public ResponseEntity<List<EnrollmentDto>> getCompletedPlans(@PathVariable int userId) {
		return new ResponseEntity<>(reviewService.getCompletedPlans(userId), HttpStatus.OK);
	}

	@GetMapping("/comments/{planId}")
	public ResponseEntity<List<CommentsDto>> getComments(@PathVariable int planId) {
		return new ResponseEntity<>(reviewService.getAllComments(planId), HttpStatus.OK);
	}

	@GetMapping("/view-batches/{id}")
	public List<Batch> viewBatches(@PathVariable int id) {
		return enrollmentService.viewBatches(id);
	}

	@PostMapping("/comment/{userId}")
	public ResponseEntity<String> comment(@RequestBody ReviewDto reviewDto, @PathVariable int userId) {
		return new ResponseEntity<>(reviewService.comment(reviewDto, userId), HttpStatus.OK);
	}

	@PostMapping("/like/{userId}")
	public ResponseEntity<PlanDto> like(@RequestBody ReviewDto reviewDto, @PathVariable int userId) {
		return new ResponseEntity<>(reviewService.like(reviewDto, userId), HttpStatus.OK);
	}

	@PostMapping("get-comment/{userId}")
	public ResponseEntity<CommentsDto> getMemberCommentByPlan(@PathVariable int userId,
			@RequestBody ReviewDto reviewDto) {
		return new ResponseEntity<>(reviewService.getMemberCommentByPlan(reviewDto, userId), HttpStatus.OK);
	}

}

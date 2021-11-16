package com.deccan.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.EnrollmentReportDto;
import com.deccan.dto.ViewReceiptDto;
import com.deccan.entity.Batch;
import com.deccan.entity.Enrollment;
import com.deccan.entity.Plan;
import com.deccan.entity.Reviews;
import com.deccan.entity.Sport;
import com.deccan.entity.User;
import com.deccan.enums.StatusType;
import com.deccan.exceptions.CustomException;
import com.deccan.exceptions.RecordNotFoundException;
import com.deccan.repositories.BatchRepository;
import com.deccan.repositories.EnrollmentRepository;
import com.deccan.repositories.PlanRepository;
import com.deccan.repositories.ReviewsRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.utils.ModelMapperConfig;

@Service
public class EnrollmentService implements IEnrollmentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private ReviewsRepository reviewRepository;

	public String addEnrollment(EnrollmentReportDto enrollmentDto) {
		Enrollment enrollment = new Enrollment();

		BeanUtils.copyProperties(enrollmentDto, enrollment);
		enrollment.setPlan(planRepository.findById(enrollmentDto.getPlanId()).get());
		enrollment.setBatch(batchRepository.findById(enrollmentDto.getBatchId()).get());
		enrollment.setUser(userRepository.findById(enrollmentDto.getUserId()).get());
		enrollment.setManager(userRepository.findById(enrollmentDto.getManagerId()).get());

		enrollmentRepository.save(enrollment);
		return "Success";
	}

	// to get all enrollment requests for manager
	@Override
	public List<EnrollmentDto> getAllEnrollmentDto() {
		List<Enrollment> enrollment = enrollmentRepository.findAll();

		List<Enrollment> filteredList = enrollment.stream()
				.filter(e -> e.getStatus().equals(StatusType.PENDING)
						| e.getStatus().equals(StatusType.WAITING) & e.getBatch().getAvailability() > 0)
				.collect(Collectors.toList());
		return ModelMapperConfig.mapList(filteredList, EnrollmentDto.class);
	}

	// update enrollment status automatically if the start date is tomorrow
	// corn expression to schedule method once a day at 12 AM
	@Scheduled(cron = "0 0 12 * * ?")
	@Override
	public void automaticUpdateStatus() {
		// list of users having enrollment request pending/waiting and start date is
		// tomorrow
		List<Enrollment> memberList = enrollmentRepository.findAll().stream()
				.filter(e -> (e.getStatus().equals(StatusType.PENDING) | e.getStatus().equals(StatusType.WAITING))
						&& e.getStartDate().isEqual(LocalDate.now().plusDays(1)))
				.collect(Collectors.toList());

		// applying business logic
		memberList.stream().forEach(e -> {
			// batch has available seats and enrollment request is still pending/waiting
			if (e.getBatch().getAvailability() > 0
					& (e.getStatus().equals(StatusType.PENDING) | e.getStatus().equals(StatusType.WAITING))) {
				// the enrollment request should be approved automatically
				e.setStatus(StatusType.APPROVED);
				// and respective batch size gets decreased by 1
				e.getBatch().setAvailability(e.getBatch().getAvailability() - 1);
				// if batch has no available seats and its users first request
			} else if (e.getBatch().getAvailability() == 0 & e.getStatus().equals(StatusType.PENDING)) {
				// move user to waiting queue
				e.setStatus(StatusType.WAITING);
				// if batch has no seats available and status is still waiting (this is users
				// second request after changing batch)
			} else if (e.getBatch().getAvailability() == 0 & e.getStatus().equals(StatusType.WAITING)) {
				// make that request rejected
				e.setStatus(StatusType.REJECTED);
			}
		});

		enrollmentRepository.saveAll(memberList);
	}

	// for every approved request check if the end date is today and make is completed
	@Scheduled(cron = "0 0 12 * * ?")
	@Override
	public void completeEnrollment() {
		List<Enrollment> enrollmentList = enrollmentRepository.findAll().stream()
				.filter(e->e.getStatus().equals(StatusType.APPROVED)&&e.getEndDate().isBefore(LocalDate.now()))
				.collect(Collectors.toList());
		
		//Update Logic
		enrollmentList.stream().forEach(e->e.getBatch().setAvailability(e.getBatch().getAvailability()+1));
        enrollmentList.stream().forEach(e->e.setStatus(StatusType.COMPLETED));
        
        enrollmentRepository.saveAll(enrollmentList);
	}
	// update enrollment status(manager will manually update this)
	@Override
	public String updateEnrollmentStatus(int enrollmentId, EnrollmentDto enrollmentUpdated) throws CustomException {

		// get enrollment to be updated
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

		if (optionalEnrollment.isEmpty()) {
			throw new RecordNotFoundException("No such enrollment record found! ");
		} else if (enrollmentUpdated == null) {
			throw new RecordNotFoundException("Your Request is empty!");

		} else {
			Enrollment enrollment = optionalEnrollment.get();

			// if batch is active and it has available seats
			if (enrollment.getBatch().getAvailability() > 0) {

				// then set the updated status
				enrollment.setStatus(enrollmentUpdated.getStatus());
				// if updated status is "APPROVED"

				if (enrollment.getStatus().toString().equals("APPROVED")) {
					// then decrement available seats by one

					enrollment.getBatch().setAvailability(enrollment.getBatch().getAvailability() - 1);
					// set manager who approved the request
					enrollment.setManager(userRepository.getOne(enrollmentUpdated.getManagerId()));
				}

				enrollmentRepository.save(enrollment);

				return "Enrollment " + enrollment.getStatus() + " successfully ";

			} else if (enrollment.getBatch().getAvailability() == 0) {
				enrollment.setStatus(enrollmentUpdated.getStatus());
				// set manager who processed the request
				enrollment.setManager(userRepository.getOne(enrollmentUpdated.getManagerId()));
				enrollmentRepository.save(enrollment);
				return "Enrollment " + enrollment.getStatus() + " successfully ";
			} else {
				return "could not update the request check batch avilibility and size ";
			}

		}

	}

	public List<Enrollment> enrollmentByUserId(int userId) {

		return enrollmentRepository.findByUserId(userId);
	}

	@Override

	public Page<EnrollmentDto> getMemberEnrollment(int id, Integer pageNo, Integer pageSize) {

		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			Pageable paging = PageRequest.of(pageNo, pageSize);
			Page<Enrollment> enrollmentList = enrollmentRepository.findByUserOrderByStatus(userRepository.findById(id),
					paging);
			return ModelMapperConfig.mapEntityPageIntoDtoPage(enrollmentList, EnrollmentDto.class);
		} else
			throw new RecordNotFoundException("User doesn't exist.");

	}

	@Override
	public ViewReceiptDto viewReceipt(int id) throws CustomException {
		Optional<Enrollment> enrollment = Optional.ofNullable(enrollmentRepository.findById(id)).get();

		if (enrollment.isEmpty()) {
			throw new RecordNotFoundException("No such enrollment record found! ");
		}
		ViewReceiptDto receipt = new ViewReceiptDto();
		BeanUtils.copyProperties(enrollment.get(), receipt);
		receipt.setPlanName(enrollment.get().getPlan().getPlanName());
		receipt.setUserFirstName(enrollment.get().getUser().getFirstName());
		receipt.setUserLastName(enrollment.get().getUser().getLastName());
		return receipt;
	}

	@Override
	public List<Batch> viewBatches(int id) {
		Plan plan = planRepository.findById(id).get();
		Sport sport = plan.getSport();
		List<Batch> batchList = batchRepository.findAllBySportAndIsActive(sport, true);
		return batchList;
	}

	@Override
	public String enrollUser(EnrollmentDto enrollmentDto) {

		if (checkBatch(enrollmentDto)) {

			return "select another batch";
		} else {
			Plan plan = planRepository.findById(enrollmentDto.getPlanId()).get();
			Enrollment enrollment = new Enrollment();
			BeanUtils.copyProperties(enrollmentDto, enrollment);
			enrollment.setStatus(StatusType.PENDING);
			enrollment.setBatch(batchRepository.findById(enrollmentDto.getBatchId()).get());
			enrollment.setPlan(planRepository.findById(enrollmentDto.getPlanId()).get());
			enrollment.setUser(userRepository.findById(enrollmentDto.getUserId()).get());
			LocalDate endDate = enrollment.getStartDate().plusDays(plan.getDuration());
			enrollment.setEndDate(endDate);
			enrollmentRepository.save(enrollment);

			Reviews review = new Reviews();
			review.setPlan(enrollment.getPlan());
			review.setUser(enrollment.getUser());
			reviewRepository.save(review);

			return "User Enrolled successfully";

		}

	}

	@Override
	public Page<EnrollmentReportDto> enrollmentReportForManager(int id, Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return enrollmentRepository.enrollmentReportForManagers(userRepository.getOne(id), pageSize, paging);

	}

	public boolean checkBatch(EnrollmentDto enrollmentDto) {
		ArrayList<Enrollment> enrollmentList = (ArrayList<Enrollment>) enrollmentRepository
				.findByUserId(enrollmentDto.getUserId());
		ArrayList<EnrollmentDto> enrollmentListDto = (ArrayList<EnrollmentDto>) ModelMapperConfig
				.mapList(enrollmentList, EnrollmentDto.class);

		return enrollmentListDto.stream()
				.filter(e -> e.getStatus().equals(StatusType.APPROVED) || e.getStatus().equals(StatusType.WAITING)
						|| e.getStatus().equals(StatusType.PENDING))
				.anyMatch(e -> (e.getStartTime() == batchRepository.getOne(enrollmentDto.getBatchId()).getStartTime()));

	}

	@Override
	public EnrollmentDto findEnrollment(int id) throws CustomException {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
		if (optionalEnrollment.isPresent()) {
			Enrollment enrollment = optionalEnrollment.get();
			EnrollmentDto enrollmentDto = new EnrollmentDto();
			BeanUtils.copyProperties(enrollment, enrollmentDto);
			enrollmentDto.setPlanId(enrollment.getPlan().getId());
			enrollmentDto.setBatchId(enrollment.getBatch().getId());
			enrollmentDto.setPlanName(enrollment.getPlan().getPlanName());
			enrollmentDto.setStartTime(enrollment.getBatch().getStartTime());
			enrollmentDto.setEndTime(enrollment.getBatch().getEndTime());
			enrollmentDto.setPlanSportName(enrollment.getPlan().getSport().getSportName());
			return enrollmentDto;
		} else {
			throw new RecordNotFoundException("Enrollment Not Found");
		}
	}

	@Override
	public String updateEnrollment(EnrollmentDto enrollmentDto) throws CustomException {

		if (checkBatch(enrollmentDto)) {
			return "Select another batch";
		} else {
			Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentDto.getId());
			if (optionalEnrollment.isPresent()) {
				Enrollment enrollment = optionalEnrollment.get();
				BeanUtils.copyProperties(enrollmentDto, enrollment);
				enrollment.setStatus(StatusType.PENDING);
				enrollmentRepository.save(enrollment);
				return "Enrollment Updated successfully";
			} else
				throw new RecordNotFoundException("Enrollment Not found");
		}

	}

}

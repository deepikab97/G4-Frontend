package com.deccan.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deccan.dto.ManagerPlanReportDto;
import com.deccan.dto.PlanDto;
import com.deccan.dto.PlanReportDto;
import com.deccan.entity.Enrollment;
import com.deccan.entity.Plan;
import com.deccan.enums.StatusType;
import com.deccan.exceptions.CustomException;
import com.deccan.exceptions.RecordNotFoundException;
import com.deccan.repositories.BatchRepository;
import com.deccan.repositories.EnrollmentRepository;
import com.deccan.repositories.PlanRepository;
import com.deccan.repositories.SportRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.utils.ModelMapperConfig;

@Service
public class PlanService implements IPlanService {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SportRepository sportRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private IEnrollmentService enrollmentService;

	public static final Logger logger = LogManager.getLogger(PlanService.class.getName());

	@Override
	public Optional<Plan> getPlanById(int id) throws CustomException {
		Optional<Plan> plan = planRepository.findById(id);
		if (plan.isEmpty()) {
			logger.error("No such plan record found  of  " + id);
			throw new RecordNotFoundException("No such plan record found  of " + id);
		}
		logger.info("Plan found which having  " + id);
		return plan;
	}

	@Override
	public Page<PlanDto> getPlans(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("isActive").descending());
		Page<Plan> planList = planRepository.findAll(paging);
		Page<PlanDto> planDtoList = ModelMapperConfig.mapEntityPageIntoDtoPage(planList, PlanDto.class);
		logger.info("Displaying plan list");
		return planDtoList;

	}

	@Override
	public Page<PlanDto> getPlansForMember(int id, Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);

		// enrollments for particular user
		List<Enrollment> enrollmentList = enrollmentRepository.findByUserId(id);

		// checking if user has enrolled for plans not having completed or rejected
		// status
		if (!enrollmentList.isEmpty()) {

			// extracting plan list by getting plan id from enrollment list(having status
			// Approved,pending or waiting)
			List<Plan> userEnrolledPlans = planRepository.findAllById(enrollmentList.stream()
					.filter(e -> e.getStatus().equals(StatusType.APPROVED) || e.getStatus().equals(StatusType.WAITING)
							|| e.getStatus().equals(StatusType.PENDING))
					.map(e -> e.getPlan().getId()).collect(Collectors.toList()));

			List<Plan> plans = planRepository.findAll();

			// removing list of plans which user has already enrolled
			plans.removeAll(userEnrolledPlans);
			Page<Plan> pagePlan = new PageImpl<>(plans);
			logger.info("Plan List displayed for which Member having ID: " + id + " is not enrolled. ");
			return ModelMapperConfig.mapEntityPageIntoDtoPage(pagePlan, PlanDto.class);

		}

		// if user has no enrollments, return full plan list
		Page<Plan> planList = planRepository.findAll(paging);
		return ModelMapperConfig.mapEntityPageIntoDtoPage(planList, PlanDto.class);

	}

	@Override
	public String addPlan(PlanDto plan) {
		Plan planEnt = planRepository.findByPlanName(plan.getPlanName());
		if (planEnt != null) {
			if (planEnt.isActive()) {
				return "Plan already exist";
			} else {
				planEnt.setActive(true);
				return "Plan activated";
			}
		} else {
			Plan planEntity = new Plan();
			BeanUtils.copyProperties(plan, planEntity);
			planEntity.setManager(userRepository.findById(plan.getManagerId()).get());
			planEntity.setSport(sportRepository.findById(plan.getSportId()).get());

			planRepository.save(planEntity);
			logger.info("Plan added successfully");
			return "Successfully added plan";
		}

	}

	@Override
	public String deletePlanById(int id) {
		Optional<Plan> plan = planRepository.findById(id);
		if (plan != null) {

			Plan planEntity = plan.get();
			planEntity.setActive(false);
			planRepository.save(planEntity);
			return "deleted";
		}
		return "Plan not found";

	}

	@Override
	public String updatePlan(PlanDto planDto, int id) throws CustomException {
		Optional<Plan> optionalPlan = planRepository.findById(id);
		if (optionalPlan.isPresent()) {
			Plan plan = optionalPlan.get();
			plan.setPlanName(planDto.getPlanName());
			plan.setAmount(planDto.getAmount());
			plan.setDuration(planDto.getDuration());
			planRepository.save(plan);
			logger.info("Plan updated succesfully for Id: " + id);
			return "Plan updated succesfully";
		} else
			logger.error("No such plan found ");
		throw new RecordNotFoundException("No such a Plan Found");

	}

	@Override
	public String disablePlan(int id) throws CustomException {
		Optional<Plan> optionalPlan = planRepository.findById(id);
		if (optionalPlan.isPresent()) {
			Plan plan = optionalPlan.get();
			boolean result = enrollmentRepository.findAll().stream()
					.filter(e -> e.getStatus().equals(StatusType.APPROVED)).anyMatch(e -> e.getPlan().equals(plan));
			if (result) {
				logger.info("Plan is in use, can't be disabled" + id);
				return "Plan is in use, can't be disabled";
			} else {

				plan.setActive(false);
				planRepository.save(plan);
				logger.info("Plan is deactivated " + id);
				return "Plan disabled";
			}
		} else
			logger.error("No such a plan found ");
		throw new RecordNotFoundException("No such a Plan Found");
	}

	@Override
	public String enablePlan(int id) throws CustomException {
		Optional<Plan> optionalPlan = planRepository.findById(id);
		if (optionalPlan.isPresent()) {
			Plan plan = optionalPlan.get();
			plan.setActive(true);
			planRepository.save(plan);
			logger.info("Plan  activated " + id);
			return "Plan Enabled ";
		} else
			logger.info("No such a Plan Found " + id);
		throw new RecordNotFoundException("No such a Plan Found");

	}

	@Override
	public String removePlan(int id) {
		planRepository.delete(planRepository.findById(id).get());
		return "Plan deleted";
	}

	@Override
	public Page<PlanReportDto> getPlanReport(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return planRepository.getBatchReport(paging);
	}

	@Override
	public Page<ManagerPlanReportDto> planReportForManager(int id, Integer pageNo, Integer pageSize) {

		Pageable paging = PageRequest.of(pageNo, pageSize);

		return planRepository.planReportForManagers(userRepository.getOne(id), pageSize, paging);

	}

}

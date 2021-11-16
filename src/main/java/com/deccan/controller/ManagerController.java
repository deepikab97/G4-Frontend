package com.deccan.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deccan.dto.BatchDto;
import com.deccan.dto.BatchReportDto;
import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.EnrollmentReportDto;
import com.deccan.dto.ManagerPlanReportDto;
import com.deccan.dto.PlanDto;
import com.deccan.entity.Plan;
import com.deccan.exceptions.CustomException;
import com.deccan.repositories.EnrollmentRepository;
import com.deccan.services.IBatchService;
import com.deccan.services.IEnrollmentService;
import com.deccan.services.IPlanService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private IPlanService planService;

	@Autowired
	private IBatchService batchService;

	@Autowired
	private IEnrollmentService enrollmentService;

	@Autowired
	EnrollmentRepository enrollmentRepo;

	public static final Logger logger = LogManager.getLogger(ManagerController.class.getName());

	@GetMapping("/batch")
	public ResponseEntity<Page<BatchDto>> getBatch(@RequestParam(defaultValue = "0") Integer pageNo,

			@RequestParam(defaultValue = "100") Integer pageSize) {
		if (batchService.getBatch(pageNo, pageSize).hasContent()) {
			logger.info("in batch list");
			return new ResponseEntity<Page<BatchDto>>(batchService.getBatch(pageNo, pageSize), HttpStatus.OK);
		} else {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/batch")
	public ResponseEntity<String> addBatch(@RequestBody BatchDto batchDto) throws CustomException {
		return new ResponseEntity<String>(batchService.addBatch(batchDto), HttpStatus.OK);
	}

	@GetMapping("/disable-batch/{id}")
	public ResponseEntity<String> disableBatch(@PathVariable int id) throws CustomException {
		return new ResponseEntity<String>(batchService.disableBatch(id), HttpStatus.OK);
	}

	@GetMapping("/enable-batch/{id}")
	public ResponseEntity<String> enableBatch1(@PathVariable int id) throws CustomException { // changed bcoz it is
																								// showing duplicate
																								// function
		return new ResponseEntity<String>(batchService.enableBatch(id), HttpStatus.OK);
	}

	@PutMapping("/batch/{id}")
	public ResponseEntity<String> updateBatch(@PathVariable int id, @RequestBody BatchDto batchDto)
			throws CustomException {
		return new ResponseEntity<String>(batchService.updateBatch(id, batchDto), HttpStatus.OK);
	}

	@GetMapping("/find-batch/{id}")
	public ResponseEntity<BatchDto> findBatch(@PathVariable int id) throws CustomException {
		return new ResponseEntity<>(batchService.findBatch(id), HttpStatus.OK);
	}

	@DeleteMapping("/batch/{id}")
	public String removeBatch(@PathVariable int id) {
		return batchService.removeBatch(id);
	}

	// enrollment list

	@GetMapping("/enrollment")
	public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {

		return new ResponseEntity<>(enrollmentService.getAllEnrollmentDto(), HttpStatus.OK);
	}

	// update enrollment status
	@PutMapping("/enrollment/{id}")
	public ResponseEntity<String> updateEnrollmentStatus(@PathVariable int id, @RequestBody EnrollmentDto enrollment)
			throws CustomException {
		return new ResponseEntity<>(enrollmentService.updateEnrollmentStatus(id, enrollment), HttpStatus.OK);

	}

	// adding plans
	@PostMapping(value = "/plan")
	public ResponseEntity<String> addPlan(@RequestBody PlanDto planDto) {
		logger.info("Plan added successfully");
		return new ResponseEntity<String>(planService.addPlan(planDto), HttpStatus.OK);
	}

	// Plan list in pagination
	// url:http://localhost:8129/manager/plan?pageNo=2&pageSize=5
	@GetMapping("/plan")
	public ResponseEntity<Page<PlanDto>> getPlan(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		if (planService.getPlans(pageNo, pageSize).hasContent()) {
			logger.info("Plan list displayed");
			return new ResponseEntity<Page<PlanDto>>(planService.getPlans(pageNo, pageSize), HttpStatus.OK);
		} else {
			logger.error("No plans found");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	// Get plan By Id
	@GetMapping("/plan/{id}")
	public ResponseEntity<Optional<Plan>> getPlanById(@PathVariable int id) throws CustomException {
		logger.info("Displaying plan for Id: " + id);
		return ResponseEntity.status(HttpStatus.OK).body(planService.getPlanById(id));

	}

	// update plan by id
	@PutMapping("/plan/{id}")
	public ResponseEntity<String> updatePlan(@RequestBody PlanDto planDto, @PathVariable int id)
			throws CustomException {
		logger.info("Updating plan for Id: " + id);
		return ResponseEntity.status(HttpStatus.OK).body(planService.updatePlan(planDto, id));

	}

	// disable plan
	@GetMapping("/disableplan/{id}")
	public ResponseEntity<String> disablePlan(@PathVariable int id) throws CustomException {
		logger.info("Disabling plan for Id: " + id);
		return new ResponseEntity<String>(planService.disablePlan(id), HttpStatus.OK);
	}

	// Enable plan
	@GetMapping("/enableplan/{id}")
	public ResponseEntity<String> enablePlan(@PathVariable int id) throws CustomException {
		logger.info("Enabling plan for Id: " + id);
		return new ResponseEntity<String>(planService.enablePlan(id), HttpStatus.OK);
	}

	// add enrollment
	@PostMapping(value = "/enrollment")
	public String addEnrollment(@RequestBody EnrollmentReportDto enrollment) {
		enrollmentService.addEnrollment(enrollment);
		return "Successfully added enrollment" + enrollment;

	}

	@GetMapping("/plan-report-manager/{id}")
	public ResponseEntity<Page<ManagerPlanReportDto>> planReportForManager(@PathVariable int id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "6") Integer pageSize) {

		if (planService.planReportForManager(id, pageNo, pageSize).hasContent()) {
			logger.info("Plan report for manager is displayed");
			return new ResponseEntity<>(planService.planReportForManager(id, pageNo, pageSize), HttpStatus.OK);
		} else {
			logger.error("Error in Plan report for manager ");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/enrollment-report-manager/{id}")
	public ResponseEntity<Page<EnrollmentReportDto>> enrollmentReportForManager(@PathVariable int id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "6") Integer pageSize) {

		if (enrollmentService.enrollmentReportForManager(id, pageNo, pageSize).hasContent()) {
			logger.info("Enrollment report for manager is displayed");
			return new ResponseEntity<>(enrollmentService.enrollmentReportForManager(id, pageNo, pageSize),
					HttpStatus.OK);
		} else {
			logger.error("Error in enrollemnt report for manager ");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/batch-report-manager/{id}")
	public ResponseEntity<Page<BatchReportDto>> batchReportForManager(@PathVariable int id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "6") Integer pageSize) {
		if (batchService.batchReportForManager(id, pageNo, pageSize).hasContent()) {
			logger.info("Plan report for manager is displayed");
			return new ResponseEntity<>(batchService.batchReportForManager(id, pageNo, pageSize), HttpStatus.OK);
		} else {
			logger.error("Error in Plan report for manager ");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}

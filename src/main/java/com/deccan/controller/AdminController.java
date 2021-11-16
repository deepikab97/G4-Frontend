package com.deccan.controller;

import java.util.List;

import javax.mail.MessagingException;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deccan.dto.BatchReportDto;
import com.deccan.dto.EnrollmentReportDto;
import com.deccan.dto.PlanReportDto;
import com.deccan.dto.SportDto;
import com.deccan.dto.SportReportDto;
import com.deccan.entity.Sport;
import com.deccan.entity.User;
import com.deccan.exceptions.CustomException;
import com.deccan.services.IBatchService;
import com.deccan.services.IEnrollmentService;
import com.deccan.services.IPlanService;
import com.deccan.services.IReviewService;
import com.deccan.services.ISportService;
import com.deccan.services.IUserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ISportService sportService;

	@Autowired
	private IPlanService planService;

	@Autowired
	private IBatchService batchService;

	@Autowired
	private IEnrollmentService enrollmentService;

	@Autowired
	private IUserService userService;

	public static final Logger logger = LogManager.getLogger(AdminController.class.getName());

	@GetMapping("/unlock-request")
	public ResponseEntity<Page<User>> getUnlockRequest(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "20") Integer pageSize) {
		if (userService.getUnlcokRequest(pageNo, pageSize).hasContent())
			return new ResponseEntity<Page<User>>(userService.getUnlcokRequest(pageNo, pageSize), HttpStatus.OK);
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/unlock-account/{id}")
	public ResponseEntity<String> unlockAccount(@PathVariable int id) throws CustomException {
		return new ResponseEntity<String>(userService.unlockAccount(id), HttpStatus.OK);
	}


	@PostMapping("/manager")
	public String register(@RequestBody User user) throws MessagingException {
		logger.info("Manager registered");
		return userService.register(user);
	}


	@GetMapping("/manager")
	public ResponseEntity<List<User>> getManagers() {
		logger.info("Getting list of manager");
		return new ResponseEntity<List<User>>(userService.getAllManager(), HttpStatus.OK);

	}

	@GetMapping("/activate-manager/{id}")
	public ResponseEntity<String> activateManager(@PathVariable int id) throws CustomException {
		logger.info("Manager having ID: " + id + " is activated now");
		return new ResponseEntity<String>(userService.activateManager(id), HttpStatus.OK);

	}

	@DeleteMapping("/manager/{id}")
	public ResponseEntity<String> deactivateManager(@PathVariable int id) throws CustomException {
		logger.info("Manager having ID: " + id + " is deactivated now");
		return new ResponseEntity<String>(userService.deleteManager(id), HttpStatus.OK);

	}

	// Get All the Sports
	@GetMapping("/getallsport")

	public ResponseEntity<Page<Sport>> getAllSports(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		if (sportService.getAllSports(pageNo, pageSize).hasContent()) {
			logger.info("sports list ");
			return new ResponseEntity<Page<Sport>>(sportService.getAllSports(pageNo, pageSize), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// Add sport
	@PostMapping("/addsport")
	public ResponseEntity<String> addSport(@RequestBody SportDto sportDto) {

		return new ResponseEntity<>(sportService.addSport(sportDto), HttpStatus.OK);

	}

	// delete sport
	@GetMapping("/deletesport/{id}")
	public ResponseEntity<String> deleteSport(@PathVariable int id) {
		return new ResponseEntity<>(sportService.deleteSport(id), HttpStatus.OK);
	}

	@GetMapping("/updatesport/{id}")
	public ResponseEntity<String> enableSport(@PathVariable int id) {

		return new ResponseEntity<>(sportService.enableSport(id), HttpStatus.OK);

	}

	// Sport Report
	@GetMapping("/sport-report")
	public ResponseEntity<Page<SportReportDto>> getSportsReport(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		if (sportService.getSportsReport(pageNo, pageSize).hasContent())
			return new ResponseEntity<Page<SportReportDto>>(sportService.getSportsReport(pageNo, pageSize),
					HttpStatus.OK);
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// Batch Report
	@GetMapping("/batch-report")
	public ResponseEntity<Page<BatchReportDto>> getBatchReport(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		if (batchService.getBatchReport(pageNo, pageSize).hasContent())
			return new ResponseEntity<Page<BatchReportDto>>(batchService.getBatchReport(pageNo, pageSize),
					HttpStatus.OK);
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// Plan Report
	@GetMapping("/plan-report")
	public ResponseEntity<Page<PlanReportDto>> getPlanReportDto(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		if (planService.getPlanReport(pageNo, pageSize).hasContent())
			return new ResponseEntity<Page<PlanReportDto>>(planService.getPlanReport(pageNo, pageSize), HttpStatus.OK);
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/enrollment-report-manager/{id}")
	public Page<EnrollmentReportDto> enrollmentReportForManager(@PathVariable int id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "6") Integer pageSize) {
		return enrollmentService.enrollmentReportForManager(id, pageNo, pageSize);
	}

}

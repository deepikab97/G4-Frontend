package com.deccan.controller;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deccan.dto.ChangePasswordDto;
import com.deccan.dto.ForgotPasswordInputDto;
import com.deccan.dto.ResetPasswordDto;
import com.deccan.dto.UserDetailsDto;
import com.deccan.entity.Address;
import com.deccan.entity.HealthInfo;

import com.deccan.entity.Plan;

import com.deccan.entity.Sport;
import com.deccan.entity.User;
import com.deccan.exceptions.CustomException;
import com.deccan.services.IPlanService;
import com.deccan.services.ISportService;
import com.deccan.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private ISportService sportService;

	@Autowired
	private IPlanService planService;

	public static final Logger logger = LogManager.getLogger(UserController.class.getName());

	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) throws MessagingException {

		return new ResponseEntity<>(userService.register(user), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<User> getUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("unlock-request")
	public ResponseEntity<String> unlockWithOtpRequest(@RequestBody ForgotPasswordInputDto unlockAccountRequest) throws MessagingException{
		return new ResponseEntity<>(userService.unlockWithOtpRequest(unlockAccountRequest), HttpStatus.OK);
	}
	
	@PostMapping("unlock-account")
	public ResponseEntity<String> unlockWithOtp(@RequestBody ForgotPasswordInputDto unlockAccountRequest) throws MessagingException{
		return new ResponseEntity<>(userService.unlockAccountWithOtp(unlockAccountRequest), HttpStatus.OK);
	}
	
	// Get plan By Id
	@PreAuthorize("hasAnyRole('MEMBER','MANAGER')")
	@GetMapping("/plan/{id}")
	public ResponseEntity<Optional<Plan>> getPlanById(@PathVariable int id) throws CustomException {
		return ResponseEntity.status(HttpStatus.OK).body(planService.getPlanById(id));

	}

	// view profile
	@PreAuthorize("hasAnyRole('MEMBER','MANAGER','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) throws CustomException {

		logger.info("Displaying User for Id: " + id);
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}

	// update profile
	@PreAuthorize("hasAnyRole('MEMBER','MANAGER','ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDetailsDto userDetailsDto)
			throws CustomException {
		logger.info("Updating user for Id: " + id);
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDetailsDto, id));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotpassword(@RequestBody ForgotPasswordInputDto email)
			throws MessagingException, CustomException {
		return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);

	}

	@PostMapping("/validate-otp")
	public ResponseEntity<String> validateOtp(@RequestBody ForgotPasswordInputDto validateOtp) throws CustomException {
		return new ResponseEntity<>(userService.validateOtp(validateOtp), HttpStatus.OK);
	}

	// Update password

	@PostMapping("/changepassword/{id}")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable int id)
			throws CustomException {
		logger.info("Changing password of user having Id: " + id);
		return new ResponseEntity<>(userService.updatePassword(changePasswordDto, id), HttpStatus.OK);
	}

	// reset password
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws CustomException {
		return new ResponseEntity<>(userService.resetpassword(resetPasswordDto), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/get-active-sport")
	public ResponseEntity<List<Sport>> getActiveSports() {
		return new ResponseEntity<>(sportService.getActiveSports(), HttpStatus.OK);
	}

	// adding HealthInfo of MEMBER/MANAGER
	@PreAuthorize("hasAnyRole('MEMBER','MANAGER','ADMIN')")
	@PostMapping("/healthInfo/{userId}")
	public ResponseEntity<String> addHealthInfo(@RequestBody HealthInfo healthInfo, @PathVariable int userId)
			throws CustomException {
		logger.info("Add Health-Infomation for ID: " + userId);
		return ResponseEntity.status(HttpStatus.OK).body(userService.addHealthInfo(healthInfo, userId));

	}

	// view HealthInfo of MEMBER/MANAGER
	@PreAuthorize("hasAnyRole('MEMBER','MANAGER','ADMIN')")
	@GetMapping("/healthInfo/{userId}")
	public ResponseEntity<HealthInfo> getHealthInfo(@PathVariable int userId) throws CustomException {
		logger.info("Show Health-Infomation for ID: " + userId);
		return ResponseEntity.status(HttpStatus.OK).body(userService.getHealthInfo(userId));

	}

	// updating HealthInfo of MEMBER/MANAGER
	@PreAuthorize("hasAnyRole('MEMBER','MANAGER','ADMIN')")
	@PutMapping("/healthInfo/{userId}")
	public ResponseEntity<String> updatHealthInfo(@RequestBody HealthInfo healthInfo, @PathVariable int userId)
			throws CustomException {
    logger.info("Update Health-Infomation for ID: "+ userId);
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateHealthInfo(healthInfo, userId));
	}

	// Add Address details
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','MEMBER')")
	@PostMapping("/address/{id}")
	public ResponseEntity<String> addAddress(@RequestBody Address address, @PathVariable int id)
			throws CustomException {
		logger.info("Adding address for  Id: " + id);
		return new ResponseEntity<String>(userService.addAddressDetail(address, id), HttpStatus.OK);

	}

	// Get-address details
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','MEMBER')")
	@GetMapping("/address/{id}")
	public ResponseEntity<List<Optional<Address>>> getAddress(@PathVariable int id) {
		logger.info("Displaying address for Id: " + id);
		return ResponseEntity.status(HttpStatus.FOUND).body(userService.getAddress(id));
	}

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','MEMBER')")
	@PutMapping("/address/{userId}/{id}")
	public ResponseEntity<String> updateAddress(@RequestBody Address address, @PathVariable int userId,
			@PathVariable int id) throws CustomException {
		logger.info("Updating address for Id: " + id);
		return new ResponseEntity<String>(userService.updateAddressDetail(address, userId, id), HttpStatus.OK);
	}
}

package com.deccan.services;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.deccan.dto.ChangePasswordDto;
import com.deccan.dto.ForgotPasswordInputDto;
import com.deccan.dto.ResetPasswordDto;
import com.deccan.dto.UserDetailsDto;
import com.deccan.dto.UserDto;
import com.deccan.entity.Address;
import com.deccan.entity.HealthInfo;
import com.deccan.entity.User;
import com.deccan.exceptions.CustomException;

public interface IUserService extends UserDetailsService{


	public String register(User user) throws MessagingException;

	public List<User> getAllManager();

	public List<User> getAllUsers();

	public User getUserById(int id) throws CustomException;

	public String unlockAccount(int userId);

	public User getManagerById(int id) throws CustomException;

	public String deleteManager(int id) throws CustomException;

	public String addHealthInfo(HealthInfo healthInfo, int userId) throws CustomException;

	public Page<User> getUnlcokRequest(Integer pageNo, Integer pageSize);

	public HealthInfo getHealthInfo(int userId) throws CustomException;

	public String updateHealthInfo(HealthInfo healthInfo, int userId) throws CustomException;

	public String updatePassword(ChangePasswordDto changePassworddto, int id) throws CustomException;

	public String forgotPassword(ForgotPasswordInputDto email) throws MessagingException, CustomException;

	public String validateOtp(ForgotPasswordInputDto validateOtp) throws CustomException;

	public String updateUser(UserDetailsDto userDetailsDto, int id) throws CustomException;

	public String addAddressDetail(Address address, int id) throws CustomException;

	public List<Optional<Address>> getAddress(int id)  ;

	public String updateAddressDetail(Address address, int userId, int id);

	public String activateManager(int id) throws CustomException;

	public String resetpassword(ResetPasswordDto resetPasswordDto);

	public void incrementFailedAttempts(String email);

	public void resetFailedAttempts(UserDto userDto);

	public String unlockWithOtpRequest(ForgotPasswordInputDto unlockAccountRequest) throws MessagingException;
	
	public String unlockAccountWithOtp(ForgotPasswordInputDto unlockAccountRequest) throws MessagingException;

	public String generateOtp(User user);
	
}

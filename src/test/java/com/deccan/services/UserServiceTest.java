package com.deccan.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.deccan.dto.ForgotPasswordInputDto;
import com.deccan.entity.HealthInfo;
import com.deccan.entity.OtpDetails;
import com.deccan.entity.User;
import com.deccan.enums.GenderType;
import com.deccan.enums.RoleType;
import com.deccan.exceptions.CustomException;
import com.deccan.repositories.HealthInfoRepository;
import com.deccan.repositories.OtpRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.utils.EmailUtil;


class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private OtpRepository otpRepository;
	
	@Mock
	private EmailUtil email;
	
	@Mock
	private HealthInfoRepository healthInfoRepository;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@InjectMocks
	private UserService userService;
	
	User user;
	OtpDetails otpDetails;
	HealthInfo healthInfo;

	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		otpDetails=new OtpDetails();
		user=new User();
		user.setId(25);
		user.setFirstName("Rajesh");
		user.setLastName("Zambre");
		user.setEmail("rajeshz@mail.com");
		user.setPassword("test");
		user.setBirthDate(LocalDate.of(1995, 10, 10));
		user.setContacts(Stream.of("7854123069","9510236847").collect(Collectors.toSet()));
		user.setGender(GenderType.MALE);
		user.setRole(RoleType.MEMBER);
		
		healthInfo=new HealthInfo(1, 170, 60, 0, "O+");
	}

	
	@Test
	void testGetHealthInfo() throws CustomException
	{
		Optional<User> user = userRepository.findById(anyInt());
		if(user.isPresent())
		{
			 User userEntity = user.get();
			int userId=userEntity.getId();
			when(healthInfoRepository.findByUserId(userId)).thenReturn(healthInfo);
			healthInfo=userService.getHealthInfo(userId);
			assertEquals("O+", healthInfo.getBloodGroup());
		}
		 
	}
	
	@Test
	void testAddHealthInfo() throws CustomException
	{
		Optional<User> user = userRepository.findById(anyInt());
		if(user.isPresent())
		{
             User userEntity = user.get();
			int userId=userEntity.getId();
			//calculating BMI-->Body Mass Index
			double BMI = healthInfo.getWeight() / ((healthInfo.getHeight()/100) * (healthInfo.getHeight()/100));
			double roundOffBMI = Math.round(BMI * 100.0) / 100.0;
			healthInfo.setBmi(roundOffBMI);
			 healthInfo.setUser(userEntity);
			 when(healthInfoRepository.save(healthInfo)).thenReturn(healthInfo);
			 String message =userService.addHealthInfo(healthInfo, userId);
				
				assertEquals("Health Info added successfully", message);
		}
	}
	
	@Test
	void testUpdateHealthInfo() throws CustomException
	{
		Optional<User> user = userRepository.findById(anyInt());
		if(user.isPresent())
		{
			User userEntity = user.get();
			int userId=userEntity.getId();
			HealthInfo healthInfoEntity=userEntity.getHealthInfo();
			healthInfoEntity.setHeight(healthInfo.getHeight());
			healthInfoEntity.setWeight(healthInfo.getWeight());
			healthInfoEntity.setBloodGroup(healthInfo.getBloodGroup());
			//calculating BMI-->Body Mass Index
			double BMI = healthInfo.getWeight() / ((healthInfo.getHeight()/100) * (healthInfo.getHeight()/100));
			double roundOffBMI = Math.round(BMI * 100.0) / 100.0;
			healthInfoEntity.setBmi(roundOffBMI);
			healthInfoEntity.setUser(userEntity);
			 when(healthInfoRepository.save(healthInfo)).thenReturn(healthInfo);
			 String message =userService.updateHealthInfo(healthInfo, userId);
				
				assertEquals("HealthInfo Updated Successfully", message);
		}
	}
	
	
	@Test
	void testGetAllManager()
	{
		Set<String> contacts = new HashSet<String>();
		contacts.add("7811155489");
		contacts.add("7477747456");
		List<User> managerList = new ArrayList<User>();
		managerList.add(new User(22,  "Tejas", "Nimkar", "tejas@deccan.com", "12345", LocalDate.now(), GenderType.MALE,contacts, RoleType.MANAGER, false, true, 3, LocalDateTime.now(), LocalDateTime.now(), null,null));
		managerList.add(new User(23,  "Tejas", "Nimkar", "tejas@deccan.com", "12345", LocalDate.now(),GenderType.MALE,contacts, RoleType.MANAGER, false, true, 3, LocalDateTime.now(), LocalDateTime.now(), null,null));
		managerList.add(new User(24,  "Tejas", "Nimkar", "tejas@deccan.com", "12345", LocalDate.now(), GenderType.MALE,contacts, RoleType.MANAGER, false, true, 3, LocalDateTime.now(), LocalDateTime.now(), null,null));
	   
		Mockito.when(userRepository.findByRole(RoleType.MANAGER)).thenReturn(managerList);
		List<User> list=userService.getAllManager();
		assertEquals(3, list.size());
	
	}
	
	@Test
	void testGetUserById() throws CustomException {

		Set<String> contacts = new HashSet<String>();
		contacts.add("7811155489");
		contacts.add("7477747456");
		User userEntity= new User(22, "Tejas", "Nimkar", "tejas@deccan.com", "12345", LocalDate.now(), GenderType.MALE,contacts, RoleType.MANAGER, false, true, 3, LocalDateTime.now(), LocalDateTime.now(), null,null);
		
    Mockito.when (userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));	
		when(userRepository.findById(anyInt())).thenReturn( Optional.of(user));
		Mockito.when (userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));		
		when(userRepository.findById(anyInt())).thenReturn( Optional.of(user));				
		User user1=userService.getUserById(22);
		
		assertEquals(0,user1.getId());

		User result=userService.getUserById(25);
		assertNotNull(result);
		assertEquals(25,result.getId());
	
	}

	
	@Test
	void testRegisterUser() throws MessagingException {
		when(userRepository.save(user)).thenReturn(user);
		when(email.sendMail(anyString(), anyString(), anyString())).thenReturn("success");
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("qwerrtyyuu123sdf");
		String message =userService.register(user);
		
		assertEquals("success", message);
	}
	
	
	

	@Test
	void testForgetPassword() throws MessagingException, CustomException {
		when(userRepository.findByEmail(anyString())).thenReturn(user);
//		when(Utility.generateOtp(anyInt())).thenReturn("aD23Ed");
		
		otpDetails.setGeneratedOn(LocalDateTime.now());
		otpDetails.setOtp("dse3Ed");
		otpDetails.setUser(user);
		otpDetails.setValidTill(LocalDateTime.now().plusMinutes(20));
		when(otpRepository.save(otpDetails)).thenReturn(otpDetails);
		when(email.sendMail(anyString(), anyString(), anyString())).thenReturn("Otp sent to email id");
		ForgotPasswordInputDto forgotPassword = new ForgotPasswordInputDto();
		forgotPassword.setEmail(user.getEmail());
		String message = userService.forgotPassword(forgotPassword);
		
		
		assertEquals("Otp sent to email id", message);
	}


}

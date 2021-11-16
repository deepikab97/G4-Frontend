package com.deccan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deccan.dto.ChangePasswordDto;
import com.deccan.dto.ForgotPasswordInputDto;
import com.deccan.dto.ResetPasswordDto;
import com.deccan.dto.UserDetailsDto;
import com.deccan.dto.UserDto;
import com.deccan.entity.Address;
import com.deccan.entity.HealthInfo;
import com.deccan.entity.OtpDetails;
import com.deccan.entity.User;
import com.deccan.enums.RoleType;
import com.deccan.exceptions.AccountLockedException;
import com.deccan.exceptions.CustomException;
import com.deccan.exceptions.RecordNotFoundException;
import com.deccan.repositories.AddressRepository;
import com.deccan.repositories.HealthInfoRepository;
import com.deccan.repositories.OtpRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.utils.EmailUtil;
import com.deccan.utils.Utility;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HealthInfoRepository healthInfoRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	EmailUtil emailUtil;

	String msg = "User Not Found for ID:";

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static final Logger logger = LogManager.getLogger(UserService.class.getName());

	private static final int VALIDTILL = 20;

	private static final int OTPLENGTH = 6;

	private String notFound = "No user found with email : ";

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException(notFound + email);
//		else if(!user.isAccountNonLocked()) {
//			throw new 
//		}
		return new UserDto(user);
	}

	// reset failed attempts

	@Override
	public void resetFailedAttempts(UserDto userDto) {
		User user = userRepository.findByEmail(userDto.getUsername());
		user.setFailedAttempts(0);
		userRepository.save(user);

	}

	// incrementing failed attempts
	@Override
	public void incrementFailedAttempts(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			if (user.getFailedAttempts() < 3) {
				user.setFailedAttempts(user.getFailedAttempts() + 1);
				if (user.getFailedAttempts() == 3) {
					user.setLockDate(LocalDateTime.now());
					user.setAccountNonLocked(false);
					userRepository.save(user);
					throw new AccountLockedException("Account is locked");
				}
			}
			userRepository.save(user);
		}
		throw new RecordNotFoundException("no user found with " + email);

	}

	// registering the new USER---(MEMBER and MANAGER)
	@Override
	public String register(User user) throws MessagingException {
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		String mailResponse = "";

		if (userRepository.findByEmail(user.getEmail()) != null)
			return "This email id is already registered!";

		if (!("MEMBER".equals(user.getRole().toString()))) {
			String subject = "Credentials for Deccan Sports club";
			String body = "Hello " + user.getFirstName() + ",\n" +"\n"+ "email : " + user.getEmail() + "\n" + "password : "
					+ password+  "\n" + "\n"
							+ "This is an auto generated mail please do not revert back on this mail.";
			mailResponse = emailUtil.sendMail(user.getEmail(), subject, body);
		} else {
			String subject = "Registration on Deccan Sports Club";
			String body = "Hello " + user.getFirstName() + ",\n"+"\n"+"Thanks for registering with deccan sports club. Login and checkout exiting sprots plans. "
					 + "\n" + "\n"
						+ "This is an auto generated mail please do not revert back on this mail.";;
			mailResponse = emailUtil.sendMail(user.getEmail(), subject, body);
		}

		if ("success".equals(mailResponse)) {
			logger.info("Uses registered !");
			userRepository.save(user);

		}
		return mailResponse;
	}

	@Override
	public Page<User> getUnlcokRequest(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return userRepository.findAllByAccountNonLocked(false, paging);

	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override

	public User getUserById(int id) throws CustomException {

		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			logger.info("Displaying User having Id: " + id);
			return user;
		}
		logger.error("No such user record found  of " + id);
		throw new RecordNotFoundException("No such user record found  of " + id);

	}

	@Override
	public String updateUser(UserDetailsDto userDetailsDto, int id) throws CustomException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setFirstName(userDetailsDto.getFirstName());
			user.setLastName(userDetailsDto.getLastName());
			user.setBirthDate(userDetailsDto.getBirthDate());
			user.setGender(userDetailsDto.getGender());
			user.setContacts(userDetailsDto.getContacts());

			userRepository.save(user);
			logger.info("user details updated succesfully " + id);
			return "User details updated succesfully";
		} else
			logger.error("No such  user Found");
		throw new RecordNotFoundException("No such  user Found");
	}

	@Override
	public String unlockAccount(int userId) {
		User user = userRepository.findById(userId).get();
		user.setAccountNonLocked(true);
		user.setFailedAttempts(0);
		user.setLockDate(null);
		userRepository.save(user);
		return "Account Unlocked";

	}

	// getting all managers
	@Override
	public List<User> getAllManager() {
		logger.info("Displaying User having Role: " + RoleType.MANAGER);
		return userRepository.findByRole(RoleType.MANAGER);

	}

	// getting Manager details by ID
	@Override
	public User getManagerById(int id) throws CustomException {

		Optional<User> optionalManager = userRepository.findById(id);
		if (optionalManager.isPresent()) {

			User user = optionalManager.get();
			if (user.getRole() == RoleType.MANAGER) {
				logger.info("Displaying Manager having ID: " + id);
				return user;
			} else {
				logger.error("User with role=MANAGER for id= " + id + "  Not Found.");
				throw new RecordNotFoundException("User with role=MANAGER for id= " + id + "  Not Found.");
			}
		}

		else {
			logger.error(msg + id);
			throw new RecordNotFoundException(msg + id);
		}
	}

	// deleting manager(setting active status to false)--->Soft delete
	@Override
	public String deleteManager(int id) throws CustomException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			User userEntity = user.get();
			if (userEntity.getRole() == RoleType.MANAGER) {
				userEntity.setActive(false);
				userRepository.save(userEntity);
				logger.info("Manager having ID: " + id + " is deactivated now");
				return "Manager having ID " + id + " is DEACTIVATED now";
			} else {
				logger.error("User with role=MANAGER for id= " + id + "  Not Found.");
				throw new RecordNotFoundException("User with role=MANAGER for id= " + id + "  Not Found.");
			}
		} else {
			logger.error(msg + id);
			throw new RecordNotFoundException(msg + id);
		}

	}

	// activate manager by setting active =true
	@Override
	public String activateManager(int id) throws CustomException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			User userEntity = user.get();
			if (userEntity.getRole() == RoleType.MANAGER) {
				userEntity.setActive(true);
				userRepository.save(userEntity);
				logger.info("Manager having ID: " + id + " is activated now");
				return "Manager having ID " + id + " is ACTIVATED now";
			} else {
				logger.error("User with role=MANAGER for id= " + id + "  Not Found.");
				throw new RecordNotFoundException("User with role=MANAGER for id= " + id + "  Not Found.");
			}

		} else {
			logger.error(msg + id);
			throw new RecordNotFoundException(msg + id);
		}
	}

	// adding healthInfo for user
	@Override
	public String addHealthInfo(HealthInfo healthInfo, int userId) throws CustomException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User userEntity = user.get();

			// calculating BMI-->Body Mass Index
			double BMI = healthInfo.getWeight() / ((healthInfo.getHeight() / 100) * (healthInfo.getHeight() / 100));
			double roundOffBMI = Math.round(BMI * 100.0) / 100.0;
			healthInfo.setBmi(roundOffBMI);
			healthInfo.setUser(userEntity);
			healthInfoRepository.save(healthInfo);
			logger.info("Health-Information added for ID: " + userId);
			return "Health Info added successfully";
		} else {
			logger.error(msg + userId);
			throw new RecordNotFoundException(msg + userId);
		}
	}

	@Override
	public HealthInfo getHealthInfo(int userId) throws CustomException {

		HealthInfo healthInfo = healthInfoRepository.findByUserId(userId);
		if (healthInfo != null) {
			logger.info("Health-Information displayed for ID: " + userId);
			return healthInfo;
		} else {
			logger.error("HealthInfo Not Found for ID:" + userId);
			throw new RecordNotFoundException("HealthInfo Not Found for ID:" + userId);
		}
	}

	@Override
	public String updateHealthInfo(HealthInfo healthInfo, int userId) throws CustomException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User userEntity = user.get();
			HealthInfo healthInfoEntity = userEntity.getHealthInfo();
			healthInfoEntity.setHeight(healthInfo.getHeight());
			healthInfoEntity.setWeight(healthInfo.getWeight());
			healthInfoEntity.setBloodGroup(healthInfo.getBloodGroup());
			// calculating BMI-->Body Mass Index
			double BMI = healthInfo.getWeight() / ((healthInfo.getHeight() / 100) * (healthInfo.getHeight() / 100));
			double roundOffBMI = Math.round(BMI * 100.0) / 100.0;
			healthInfoEntity.setBmi(roundOffBMI);
			healthInfoEntity.setUser(userEntity);
			healthInfoRepository.save(healthInfoEntity);
			logger.info("Health-Information updated for ID: " + userId);
			return "HealthInfo Updated Successfully";
		} else {
			logger.error("HealthInfo Not Found for ID:" + userId);
			throw new RecordNotFoundException("HealthInfo Not Found for ID:" + userId);
		}
	}

	@Override
	public String updatePassword(ChangePasswordDto changePasswordDto, int userId) throws CustomException {

		User user = userRepository.findById(userId).get();
		if (user != null) {
			if (bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {

				user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
				userRepository.save(user);
				logger.info("User password updated succesfully ID: " + userId);
				return "User password updated succesfully";
			}
			logger.error("Old Password did not match ");
			return "Old Password did not match";
		}
		logger.error("No such user found with is :" + userId);
		throw new RecordNotFoundException("No such user found with is :" + userId);
	}

	@Override
	public String generateOtp(User user) {
		String otp = Utility.generateOtp(OTPLENGTH);

		OtpDetails otpDetails = new OtpDetails();
		otpDetails.setGeneratedOn(LocalDateTime.now());
		otpDetails.setValidTill(otpDetails.getGeneratedOn().plusMinutes(VALIDTILL));
		otpDetails.setOtp(otp);
		otpDetails.setUser(user);

		otpRepository.save(otpDetails);
		return otp;
	}

	@Override
	public String forgotPassword(ForgotPasswordInputDto email) throws MessagingException, CustomException {

		User user = userRepository.findByEmail(email.getEmail());

		if (user != null) {
			String otp = generateOtp(user);
			String subject = "OTP to reset password";
			String body = "Hello " + user.getFirstName() + ",\n"+"\n" + "Your otp is : " + otp + "\n"
					+ "Your otp is valid for next 20 minutes, do not share your otp with anyone."
					+ "\n" + "\n"
					+ "This is an auto generated mail please do not revert back on this mail.";
			emailUtil.sendMail(email.getEmail(), subject, body);

			logger.info("Otp sent to " + user.getEmail());
			return "Otp sent to email id";
		}

		logger.warn(notFound + email);
		throw new RecordNotFoundException("No such user found with email :" + email);

	}

	@Override
	public String validateOtp(ForgotPasswordInputDto validateOtp) {
		User user = userRepository.findByEmail(validateOtp.getEmail());

		if (user != null) {
			OtpDetails otpDetail = otpRepository.findTopByUserIdOrderByGeneratedOnDesc(user.getId());
			if (otpDetail.getValidTill().isAfter(LocalDateTime.now())) {
				if (validateOtp.getOtp().equals(otpDetail.getOtp())) {
					return "Otp is verified";
				} else {
					logger.info("Otp validation failed for " + user.getEmail());
					return "Invalid otp";
				}

			} else {
				logger.warn("otp expired for user : " + user.getEmail());
				return "Otp is expired";
			}
		}
		logger.warn(notFound + validateOtp.getEmail());
		throw new RecordNotFoundException("No such user found with email :" + validateOtp.getEmail());
	}

	@Override
	public String addAddressDetail(Address address, int id) throws CustomException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			User userEntity = user.get();
			address.setUser(userEntity);
			addressRepository.save(address);
			return "Address added successfully";
		}
		throw new RecordNotFoundException("No such user found" + id);

	}

	@Override
	public List<Optional<Address>> getAddress(int id) {

		return addressRepository.findByUserId(id);
	}

	@Override
	public String updateAddressDetail(Address address, int userId, int id) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User userEntity = user.get();
			Optional<Address> addressOptional = addressRepository.findById(id);
			if (addressOptional.isPresent()) {
				Address addressEntity = addressOptional.get();
				address.setFlatNo(address.getFlatNo());
				address.setStreet(address.getStreet());
				address.setLandmark(address.getLandmark());
				address.setCity(address.getCity());
				address.setState(address.getState());
				address.setCountry(address.getCountry());
				address.setPincode(address.getPincode());
				address.setAddressType(address.getAddressType());
				address.setUser(userEntity);
				addressRepository.save(address);
				return "Address updated successfully";
			} else
				return "Address not found";
		}
		return "User not found";

	}

	@Override
	public String resetpassword(ResetPasswordDto resetPasswordDto) {
		User user = userRepository.findByEmail(resetPasswordDto.getEmail());
		if (user != null) {
			user.setPassword(bCryptPasswordEncoder.encode(resetPasswordDto.getPassword()));
			userRepository.save(user);
			logger.info(user.getEmail() + " password reset");
			return "Password changed successfully";
		}
		logger.warn(notFound + resetPasswordDto.getEmail());
		throw new RecordNotFoundException("No such user found with email : " + resetPasswordDto.getEmail());

	}

	@Override
	public String unlockWithOtpRequest(ForgotPasswordInputDto unlockAccountRequest) throws MessagingException {
		User user = userRepository.findByEmail(unlockAccountRequest.getEmail());
		if (user != null) {
			String otp = generateOtp(user);
			String subject = "OTP to unlock your Account ";
			String body = "Hello " + user.getFirstName() + ",\n"+"\n" + "Your otp is : " + otp + "\n"
					+ "Your otp is valid for next 20 minutes, do not share your otp with anyone. " + "\n" + "\n"
					+ "This is an auto generated mail please do not revert back on this mail.";
			emailUtil.sendMail(unlockAccountRequest.getEmail(), subject, body);

			logger.info("Otp sent to for account unlock request to : " + user.getEmail());
			return "Otp sent to email id";
		}

		throw new RecordNotFoundException(notFound + unlockAccountRequest.getEmail());
	}

	@Override
	public String unlockAccountWithOtp(ForgotPasswordInputDto unlockAccountRequest) throws MessagingException {
		User user = userRepository.findByEmail(unlockAccountRequest.getEmail());
		if (user != null) {
			String result = validateOtp(unlockAccountRequest);
			if ("Otp is verified".equals(result)) {
				unlockAccount(user.getId());
				String subject = "Unlock account request ";
				String body = "Hello " + user.getFirstName() + ", \n"+"\n"
						+ "Your account is unlocked. You can now login into deccan sports club and subscribe for various exciting plans. "
						+"\n"+"\n"+ "This is an auto generated mail please do not revert back on this mail.";
				emailUtil.sendMail(unlockAccountRequest.getEmail(), subject, body);
				return "Account unlocked";
			} else {
				return "Invalid otp";
			}
		}
		throw new RecordNotFoundException(notFound + unlockAccountRequest.getEmail());
	}

}

package com.deccan.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.deccan.dto.BatchDto;
import com.deccan.dto.PlanDto;
import com.deccan.exceptions.CustomException;
import com.deccan.repositories.BatchRepository;
import com.deccan.repositories.PlanRepository;
import com.deccan.repositories.SportRepository;
import com.deccan.repositories.UserRepository;

class PlanServiceTest {

	
	@InjectMocks
	private PlanService planService;
	
	@Mock
	private PlanRepository planRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SportRepository sportRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	
	/*
	 * // @Test // void testAddPlan()throws CustomException { // PlanDto planDto =
	 * new PlanDto(); // }
	 */
}

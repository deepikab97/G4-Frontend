
package com.deccan.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.deccan.dto.BatchDto;
import com.deccan.dto.SportDto;
import com.deccan.entity.Batch;
import com.deccan.entity.Sport;
import com.deccan.exceptions.CustomException;
import com.deccan.exceptions.RecordNotFoundException;
import com.deccan.repositories.SportRepository;

public class SportServiceTest {
	@InjectMocks
	private SportService sportService;
	
	@Mock
	private SportRepository sportRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void addSportTest() throws RecordNotFoundException{
		
		SportDto sportDto=new SportDto("Basketminton");
		Sport sport=new Sport();
		BeanUtils.copyProperties(sportDto, sport);
		Mockito.when(sportRepository.save(new Sport())).thenReturn(sport);
		String realValue=sportService.addSport(sportDto);
		assertEquals("sport added successfully", realValue);
		
	}

	
	@Test
	void getAllSportsTest() {
		Pageable page=PageRequest.of(0, 2);
		List<Sport> sports=new ArrayList<>();
		sports.add(new Sport("polo"));
		sports.add(new Sport("Hockey"));
		Page<Sport> sport=new PageImpl<>(sports,page,sports.size());

		Mockito.when(sportRepository.findAll(page)).thenReturn(sport);
		int sportList=sportService.getAllSports(0,2).getSize();
		System.out.println(sportService.getAllSports(0,2));
		assertEquals(2,sportList);
		
	}
	
//	BatchDto batchDto = new BatchDto(1, LocalTime.of(10, 00, 00), LocalTime.of(12, 00, 00), 25, 7, 1, false);
//	Batch batch = new Batch();
//	BeanUtils.copyProperties(batchDto, batch);
//	when(batchRepository.save(new Batch())).thenReturn(batch);
//	when(batchRepository.findById(anyInt())).thenReturn(Optional.of(batch));
//	String actual = batchService.disableBatch(103);
//	assertEquals("Batch disabled", actual);
//
//	
	@Test
	void updateSportTest() {
		Sport sport=new Sport(91,"CrazyBall",false);
		when(sportRepository.save(new Sport())).thenReturn(sport);
		when(sportRepository.findById(anyInt())).thenReturn(Optional.of(sport));
		String message=sportService.enableSport(91);
		assertEquals("Sport enabled", message);
		
		
		
	}
	@Test
	void deleteSportTest()  {

		Sport sport=new Sport(92,"CrazyBallss",false);
		when(sportRepository.save(new Sport())).thenReturn(sport);
		when(sportRepository.findById(anyInt())).thenReturn(Optional.of(sport));
		String actual =sportService.deleteSport(92);
		assertEquals("sport deleted successfully", actual);

	}
	
}
































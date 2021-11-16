package com.deccan.services;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.deccan.dto.BatchDto;
import com.deccan.entity.Batch;
import com.deccan.entity.Sport;
import com.deccan.entity.User;
import com.deccan.exceptions.CustomException;
import com.deccan.repositories.BatchRepository;
import com.deccan.repositories.EnrollmentRepository;
import com.deccan.repositories.SportRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.services.BatchService;

public class BatchServiceTest {

	@InjectMocks
	private BatchService batchService;

	@Mock
	private BatchRepository batchRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SportRepository sportRepository;
	
	@Mock
	private EnrollmentRepository enrollmtRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getBatchTest() {
		Pageable paging = PageRequest.of(0, 3, Sort.by("isActive").descending());
		List<Batch> batchList = new ArrayList<Batch>();
		batchList.add(new Batch(1, LocalTime.of(10, 00, 00), LocalTime.of(12, 00, 00), 25));
		batchList.add(new Batch(2, LocalTime.of(13, 00, 00), LocalTime.of(15, 00, 00), 28));
		batchList.add(new Batch(3, LocalTime.of(15, 00, 00), LocalTime.of(17, 00, 00), 30));
		Page<Batch> batchPage = new PageImpl<>(batchList, paging, batchList.size());
		Mockito.when(batchRepository.findAll(paging)).thenReturn(batchPage);

		Page<BatchDto> batchDtoPage = batchService.getBatch(0, 3);
		List<BatchDto> batchDtoList = batchDtoPage.getContent();
		assertEquals(25, batchDtoList.get(0).getSize());
		assertEquals(28, batchDtoList.get(1).getSize());
		assertEquals(30, batchDtoList.get(2).getSize());
	}

	@Test
	void addBatchTest() throws CustomException {

		BatchDto batchDto = new BatchDto(1, LocalTime.of(10, 00, 00), LocalTime.of(12, 00, 00), 25, 7, 1);
		Batch batch = new Batch();
		BeanUtils.copyProperties(batchDto, batch);

		Mockito.when(batchRepository.save(new Batch())).thenReturn(batch);

		String actual = batchService.addBatch(batchDto);
		assertEquals("Batch added succefully.", actual);

	}

	@Test
	void enableBatchTest() throws CustomException {

		BatchDto batchDto = new BatchDto(1, LocalTime.of(10, 00, 00), LocalTime.of(12, 00, 00), 25, 7, 1, false);
		Batch batch = new Batch();
		BeanUtils.copyProperties(batchDto, batch);
		when(batchRepository.save(new Batch())).thenReturn(batch);
		when(batchRepository.findById(anyInt())).thenReturn(Optional.of(batch));
		String actual = batchService.enableBatch(103);
		assertEquals("Batch Enabled", actual);

	}
	
	@Test
	void diableBatchTest() throws CustomException {

		BatchDto batchDto = new BatchDto(1, LocalTime.of(10, 00, 00), LocalTime.of(12, 00, 00), 25, 7, 1, false);
		Batch batch = new Batch();
		BeanUtils.copyProperties(batchDto, batch);
		when(batchRepository.save(new Batch())).thenReturn(batch);
		when(batchRepository.findById(anyInt())).thenReturn(Optional.of(batch));
		String actual = batchService.disableBatch(103);
		assertEquals("Batch disabled", actual);

	}
}

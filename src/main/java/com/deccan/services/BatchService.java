package com.deccan.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deccan.dto.BatchDto;
import com.deccan.dto.BatchReportDto;
import com.deccan.entity.Batch;
import com.deccan.entity.Sport;
import com.deccan.entity.User;
import com.deccan.enums.StatusType;
import com.deccan.exceptions.CustomException;
import com.deccan.exceptions.RecordNotFoundException;
import com.deccan.repositories.BatchRepository;
import com.deccan.repositories.EnrollmentRepository;
import com.deccan.repositories.SportRepository;
import com.deccan.repositories.UserRepository;
import com.deccan.utils.ModelMapperConfig;

@Service
public class BatchService implements IBatchService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SportRepository sportRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Override
	public Page<BatchDto> getBatch(Integer pageNo, Integer pageSize) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("isActive").descending());
		Page<Batch> batchList = batchRepository.findAll(paging);
		Page<BatchDto> batchDtoList = ModelMapperConfig.mapEntityPageIntoDtoPage(batchList, BatchDto.class);
		return batchDtoList;

	}

	@Override
	public String addBatch(BatchDto batch) throws CustomException {
		batch.setUniqueField(this.forUniqueField(batch));
		Boolean result = batchRepository.findAll().stream()
				.anyMatch(b -> b.getUniqueField().equals(batch.getUniqueField()));
		Batch checkBatch = new Batch();

		if (result) {
			checkBatch = batchRepository.findAll().stream()
					.filter(b -> b.getUniqueField().equals(batch.getUniqueField())).findFirst().get();
			if (checkBatch.isActive())
				return "Batch already exist.";
			else {
				checkBatch.setActive(true);
				batchRepository.save(checkBatch);
				return "Batch Activated";
			}
		} else {

			BeanUtils.copyProperties(batch, checkBatch);
			checkBatch.setAvailability(batch.getSize());
			Optional<User> manager = userRepository.findById(batch.getManagerId());
			if (manager.isPresent())
				checkBatch.setManager(manager.get());
			Optional<Sport> sport = sportRepository.findById(batch.getSportId());
			if (sport.isPresent())
				checkBatch.setSport(sport.get());
			batchRepository.save(checkBatch);
			return "Batch added succefully.";
		}
	}

	@Override
	public String updateBatch(int id, BatchDto batchDto) throws CustomException {
		Optional<Batch> batch = batchRepository.findById(id);

		if (batch.isEmpty())
			throw new RecordNotFoundException("Batch Not Found.");
		else {
			Batch batch1 = batch.get();
			batch1.setAvailability(batch1.getAvailability() + (batchDto.getSize() - batch1.getSize()));
			batch1.setSize(batchDto.getSize());
			String unique = batch1.getStartTime().toString().concat(batch1.getEndTime().toString())
					.concat(Integer.toString(batchDto.getSize())).concat(Integer.toString(batch1.getSport().getId()));
			batch1.setUniqueField(unique);
			batchRepository.save(batch1);
		}

		return "Batch updated successfully.";
	}

	@Override
	public String disableBatch(int id) throws CustomException {

		Optional<Batch> optionalBatch = batchRepository.findById(id);
		if (optionalBatch.isPresent()) {
			Batch batch = optionalBatch.get();
			boolean result = enrollmentRepository.findAll().stream()
					.filter(e -> e.getStatus().equals(StatusType.APPROVED)).anyMatch(e -> e.getBatch().equals(batch));
			if (result)
				return "Batch in use, can't be disabled";
			else {
				batch.setActive(false);
				batchRepository.save(batch);
				return "Batch disabled";
			}
		} else
			throw new RecordNotFoundException("Batch Not Found.");

	}

	@Override
	public String enableBatch(int id) throws CustomException {
		Optional<Batch> optionalBatch = batchRepository.findById(id);
		if (optionalBatch.isPresent()) {
			Batch batch = optionalBatch.get();
			batch.setActive(true);
			batchRepository.save(batch);
			return "Batch Enabled";
		} else
			throw new RecordNotFoundException("Batch Not Found.");

	}

	@Override
	public String removeBatch(int id) {
		batchRepository.delete(batchRepository.findById(id).get());
		return "Batch deleted";
	}

	@Override
	public Page<BatchReportDto> getBatchReport(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return batchRepository.getBatchReport(paging);
	}

	@Override
	public BatchDto findBatch(int id) throws CustomException {

		Optional<Batch> optionalBatch = batchRepository.findById(id);
		if (optionalBatch.isPresent()) {
			Batch batch = optionalBatch.get();
			BatchDto batchDto = new BatchDto();
			BeanUtils.copyProperties(batch, batchDto);
			batchDto.setSportName(batch.getSport().getSportName());
			return batchDto;
		} else
			throw new RecordNotFoundException("Batch Not Found");
	}

	// Utility Methods
	public String forUniqueField(BatchDto batch) {
		String unique = batch.getStartTime().toString().concat(batch.getEndTime().toString())
				.concat(Integer.toString(batch.getSize())).concat(Integer.toString(batch.getSportId()));
		return unique;

	}

	// Batch report for manager
	@Override
	public Page<BatchReportDto> batchReportForManager(int id, Integer pageNo, Integer pageSize) {

		Pageable paging = PageRequest.of(pageNo, pageSize);

		return batchRepository.batchReportForManagers(userRepository.getOne(id), pageSize, paging);

	}

}

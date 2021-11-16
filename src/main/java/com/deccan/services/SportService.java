package com.deccan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccan.dto.SportDto;
import com.deccan.dto.SportReportDto;
import com.deccan.entity.Sport;
import com.deccan.exceptions.RecordNotFoundException;
import com.deccan.repositories.PlanRepository;
import com.deccan.repositories.SportRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class SportService implements ISportService {
	@Autowired
	private SportRepository sportRepository;
	public static final Logger logger = LogManager.getLogger(SportService.class.getName());
	@Autowired
	private PlanRepository planRepository;

	// Function to set the status of sport to false
	@Override
	public String deleteSport(int id) throws RecordNotFoundException {
	
    Sport sport = sportRepository.findById(id).get();

		if (sport == null) {
			logger.error("sport deletion failed");

			throw new RecordNotFoundException("No Record to delete");
		}
		Boolean result = planRepository.findAll().stream()
				.anyMatch(s -> s.getSport().getSportName().equals(sport.getSportName()));
		if (result) {
			return "sport already active in plan,can't be deleted";
		} else {
			sport.setActive(false);
			logger.info("sport deletion success");
			sportRepository.save(sport);
			return "sport deleted successfully";
		}

	}
  
	// Function to set the status of sport to true

	@Override
	public String enableSport(int id) throws RecordNotFoundException {

		Optional<Sport> sport = sportRepository.findById(id);
		if (sport.isPresent()) {
			Sport enableSport = sport.get();
			enableSport.setActive(true);
			sportRepository.save(enableSport);
			logger.info("sport enabled successfully");
			return "Sport enabled";

		} else{
      logger.error("sport enabled failed");
			throw new RecordNotFoundException("No sport found you want to update");
		}
	}

//Function to get all the sports from repository
	@Override
	public Page<Sport> getAllSports(Integer pageNo, Integer pageSize) throws RecordNotFoundException {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		if (sportRepository.findAll(paging) == null)
		{	logger.error("No sport found");
			throw new RecordNotFoundException("No Record is Present for sports");
		}{
			logger.info(" sports are found");
		return sportRepository.findAll(paging);
	}}


	@Override
	public String addSport(SportDto sportDto) throws RecordNotFoundException {
		Sport sport = new Sport();
		if (sportDto.getSportName() == null) {
        logger.error(" sports not found");
			throw new RecordNotFoundException("sport not found");
		}
  
		Boolean result = sportRepository.findAll().stream()
				.anyMatch(s -> s.getSportName().equals(sportDto.getSportName()));

		if (result) {
			sport = sportRepository.findAll().stream().filter(b -> b.getSportName().equals(sportDto.getSportName()))
					.findFirst().get();

			if (sport.isActive()) {
				System.out.println("exist");
				return "already exist";

			}

			else {
				sport.setActive(true);
				System.out.println("again activate");
				sportRepository.save(sport);
				logger.info(" sports activated successfully");
				return "sport is activated again";

			}
		} else {
			BeanUtils.copyProperties(sportDto, sport);
			sport.setSportName(sportDto.getSportName());
			sport.setActive(true);
			sportRepository.save(sport);
			logger.info(" sports added successfully");
			return "sport added successfully";
		}

	}

	@Override
	public Page<SportReportDto> getSportsReport(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return sportRepository.getSportsReport(paging);
	}

	@Override
	public List<Sport> getActiveSports() {

		return sportRepository.findByIsActive(true);
	}

}

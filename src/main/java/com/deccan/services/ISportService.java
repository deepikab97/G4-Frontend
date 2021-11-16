package com.deccan.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deccan.dto.SportDto;
import com.deccan.dto.SportReportDto;
import com.deccan.entity.Sport;
import com.deccan.exceptions.RecordNotFoundException;

public interface ISportService {
	

	public String addSport(SportDto sportDto);

	public String deleteSport(int id);

	public Page<SportReportDto> getSportsReport(Integer pageNo, Integer pageSize);

	public List<Sport> getActiveSports();

	public String enableSport(int id) throws RecordNotFoundException;


	public Page<Sport> getAllSports(Integer pageNo, Integer pageSize) throws RecordNotFoundException;

}

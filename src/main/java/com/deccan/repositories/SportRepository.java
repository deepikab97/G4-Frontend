package com.deccan.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccan.dto.SportReportDto;
import com.deccan.entity.Batch;
import com.deccan.entity.Sport;

@Repository
public interface SportRepository extends JpaRepository<Sport, Integer> {
	@Query("SELECT new com.deccan.dto.SportReportDto" + "(s.sportName, count(distinct p.planName),"
			+ "count(distinct b.id),count(distinct e.id),s.isActive) "
			+ "FROM Sport s inner join Plan p on s.id = p.sport" + " inner join Batch b on b.sport = s.id "
			+ "inner join Enrollment e on p.sport = s.id  GROUP BY s.id")
	public Page<SportReportDto> getSportsReport(Pageable paging);

	public List<Sport> findByIsActive(boolean b);

	public Page<Sport> findAll(Pageable paging);
	

}

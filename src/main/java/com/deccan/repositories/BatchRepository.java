package com.deccan.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccan.dto.BatchReportDto;
import com.deccan.entity.Batch;
import com.deccan.entity.Sport;
import com.deccan.entity.User;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

	Page<Batch> findAllByIsActiveTrue(Pageable paging);

	@Query(value = "select new com.deccan.dto.BatchReportDto(b.startTime, b.endTime, s.sportName, b.size, b.availability, count(e.id)) from Batch b inner join Sport s on b.sport=s.id left join Enrollment e on e.batch=b.id group by b.id", nativeQuery = false)
	Page<BatchReportDto> getBatchReport(Pageable paging);

	Page<Batch> findAll(Pageable paging);

	List<Batch> findAllBySportAndIsActive(Sport sport,boolean isActive);
	
	@Query(value = "select new com.deccan.dto.BatchReportDto(b.startTime,"
			+ " b.endTime, s.sportName, b.size, b.availability, count(e.id)) "
			+ "from Batch b inner join Sport s on b.sport=s.id inner join Enrollment"
			+ " e on e.batch=b.id group by b.id", nativeQuery = false)
	
	Page<BatchReportDto> batchReportForManagers(User id,Integer pageSize,Pageable paging);

}

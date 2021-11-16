package com.deccan.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccan.dto.ManagerPlanReportDto;
import com.deccan.dto.PlanReportDto;
import com.deccan.entity.Plan;
import com.deccan.entity.User;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

	public Plan findByPlanName(String planName);

	@Query(value = "select new com.deccan.dto.PlanReportDto(p.planName, s.sportName, count(e.id)) from Plan p inner join Sport s on p.sport=s.id left join Enrollment e on e.plan=p.id group by p.id", nativeQuery = false)
	public Page<PlanReportDto> getBatchReport(Pageable paging);

	@Query(value = "select new com.deccan.dto.ManagerPlanReportDto(p.planName, "
			+ "s.sportName, count(e.id),p.isActive) from Plan p inner join Sport s "
			+ "on p.sport=s.id left join Enrollment e on e.plan=p.id "
			+ " where p.manager=?1 group by p.id", nativeQuery = false)

	public Page<ManagerPlanReportDto> planReportForManagers(User id, Integer pageSize, Pageable paging);

}

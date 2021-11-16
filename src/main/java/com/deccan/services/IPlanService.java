package com.deccan.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deccan.dto.ManagerPlanReportDto;
import com.deccan.dto.PlanDto;
import com.deccan.dto.PlanReportDto;
import com.deccan.entity.Plan;
import com.deccan.exceptions.CustomException;

public interface IPlanService {

	public Page<PlanDto> getPlans(Integer pageNo, Integer pageSize);

	public String addPlan(PlanDto plan);

	public Optional<Plan> getPlanById(int id) throws CustomException;

	public String deletePlanById(int id);

	public Page<PlanDto> getPlansForMember(int id, Integer pageNo, Integer pageSize);

	public String updatePlan(PlanDto planDto, int id) throws CustomException;

	public String disablePlan(int id) throws CustomException;

	public String enablePlan(int id) throws CustomException;

	public String removePlan(int id);

	public Page<PlanReportDto> getPlanReport(Integer pageNo, Integer pageSize);

	public Page<ManagerPlanReportDto> planReportForManager(int id, Integer pageNo, Integer pageSize);

}

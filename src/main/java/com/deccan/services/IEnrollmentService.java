package com.deccan.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deccan.dto.EnrollmentReportDto;
import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.ViewReceiptDto;
import com.deccan.entity.Batch;
import com.deccan.exceptions.CustomException;

public interface IEnrollmentService {

	public void automaticUpdateStatus();

	public void completeEnrollment();
	
	public List<EnrollmentDto> getAllEnrollmentDto();

	public String addEnrollment(EnrollmentReportDto enrollmentDto);

	public String updateEnrollmentStatus(int enrollmentId, EnrollmentDto enroll) throws CustomException;

	public Object enrollmentByUserId(int id);

	public Page<EnrollmentDto> getMemberEnrollment(int id, Integer pageNo, Integer pageSize);

	public String enrollUser(EnrollmentDto enrollmentListDto);

	public ViewReceiptDto viewReceipt(int id) throws CustomException;

	public Page<EnrollmentReportDto> enrollmentReportForManager(int id, Integer pageNo, Integer pageSize);

	public List<Batch> viewBatches(int id);

	public EnrollmentDto findEnrollment(int id) throws CustomException;

	public String updateEnrollment(EnrollmentDto enrollmentDto) throws CustomException;

}

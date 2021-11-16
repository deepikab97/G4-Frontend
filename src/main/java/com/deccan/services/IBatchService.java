package com.deccan.services;

import org.springframework.data.domain.Page;

import com.deccan.dto.BatchDto;
import com.deccan.dto.BatchReportDto;
import com.deccan.exceptions.CustomException;

public interface IBatchService {

	public Page<BatchDto> getBatch(Integer pageNo, Integer pageSize);

	public String addBatch(BatchDto batch) throws CustomException;

	public String updateBatch(int id, BatchDto batchDto) throws CustomException;

	public String disableBatch(int id) throws CustomException;

	public String enableBatch(int id) throws CustomException;

	public BatchDto findBatch(int id) throws CustomException;

	public String removeBatch(int id);

	public Page<BatchReportDto> getBatchReport(Integer pageNo, Integer pageSize);

	public Page<BatchReportDto> batchReportForManager(int id, Integer pageNo, Integer pageSize);

}

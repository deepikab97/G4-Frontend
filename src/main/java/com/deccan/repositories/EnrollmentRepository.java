package com.deccan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccan.dto.EnrollmentDto;
import com.deccan.dto.EnrollmentReportDto;
import com.deccan.entity.Enrollment;
import com.deccan.entity.User;
import com.deccan.enums.StatusType;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

	public List<Enrollment> findByUser(Optional<User> findById);

	public List<Enrollment> findByUserId(int userId);

	public Page<Enrollment> findByUserOrderByStatus(Optional<User> findById, Pageable pageable);

	// Query for getting enrolled users approved by particular manager
	@Query(value = "select new com.deccan.dto.EnrollmentReportDto(p.planName,"
			+ "s.sportName, e.startDate,e.endDate,e.status,u.firstName) from Enrollment e inner join Plan p "
			+ "on e.plan=p.id "
			+ "inner join Sport s on p.sport=s.id inner join com.deccan.entity.User u on u.id=e.user "
			+ "where e.manager=?1 ", nativeQuery = false)

	public Page<EnrollmentReportDto> enrollmentReportForManagers(User id, Integer pageSize, Pageable paging);

	@Query(value = "select new com.deccan.dto.EnrollmentDto(e.status,e.startDate,p.planName,"
			+ "s.sportName,p.id, r.isCommented) from Enrollment e inner join Plan p " + "on e.plan=p.id "
			+ "inner join Sport s on p.sport=s.id inner join Reviews r on e.plan =r.plan where e.status =?1 and e.user=?2", nativeQuery = false)
	public List<EnrollmentDto> getByStatusAndUserId(StatusType status, User user);

	public List<Enrollment> findAllByOrderByStartDate();
}

package com.SocietyManagementSystem.SocietyManagementSystem_Backend.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MaintenancePaymentRepository extends JpaRepository<MaintenancePayment, Long> {

    List<MaintenancePayment> findByFlat_FlatId(Long flatId);

    List<MaintenancePayment> findByStatus(PaymentStatus status);

    List<MaintenancePayment> findByMonthAndYear(Integer month, Integer year);

    List<MaintenancePayment> findByFlat_FlatIdAndStatus(Long flatId, PaymentStatus status);

    Optional<MaintenancePayment> findByFlat_FlatIdAndMonthAndYear(Long flatId, Integer month, Integer year);

    // For duplicate check during update — exclude current record
    @Query("SELECT m FROM MaintenancePayment m WHERE m.flat.flatId = :flatId " +
            "AND m.month = :month AND m.year = :year AND m.paymentId <> :excludeId")
    Optional<MaintenancePayment> findDuplicateExcluding(
            @Param("flatId") Long flatId,
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("excludeId") Long excludeId);
}
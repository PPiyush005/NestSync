package com.SocietyManagementSystem.SocietyManagementSystem_Backend.maintenance;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.FlatRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenancePaymentService {

    private final MaintenancePaymentRepository paymentRepository;
    private final FlatRepository flatRepository;
    private final AuthHelper authHelper;

    public MaintenancePaymentService(MaintenancePaymentRepository paymentRepository,
                                     FlatRepository flatRepository,
                                     AuthHelper authHelper) {
        this.paymentRepository = paymentRepository;
        this.flatRepository = flatRepository;
        this.authHelper = authHelper;
    }

    // Add Payment (generate monthly bill for a flat)
    public MaintenancePaymentResponse addPayment(MaintenancePaymentRequest request) {
        Flats flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));

        // Duplicate check — one bill per flat per month per year
        paymentRepository.findByFlat_FlatIdAndMonthAndYear(
                        request.getFlatId(), request.getMonth(), request.getYear())
                .ifPresent(p -> {
                    throw new RuntimeException("Maintenance bill already exists for flat id: "
                            + request.getFlatId() + " for "
                            + request.getMonth() + "/" + request.getYear());
                });

        MaintenancePayment payment = new MaintenancePayment();
        payment.setAmount(request.getAmount());
        payment.setMonth(request.getMonth());
        payment.setYear(request.getYear());
        payment.setDueDate(request.getDueDate());
        payment.setPaidDate(null);
        payment.setStatus(PaymentStatus.UNPAID); // always starts as UNPAID
        payment.setRemarks(request.getRemarks());
        payment.setFlat(flat);

        return toResponse(paymentRepository.save(payment));
    }

    // RESIDENT — view only own flat's bills
    public List<MaintenancePaymentResponse> getMyBills() {
        Long flatId = authHelper.getCurrentUserFlatId();
        return paymentRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Id
    public MaintenancePaymentResponse getPaymentById(Long id) {
        MaintenancePayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return toResponse(payment);
    }

    // Get All
    public List<MaintenancePaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Flat
    public List<MaintenancePaymentResponse> getPaymentsByFlat(Long flatId) {
        flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + flatId));
        return paymentRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Status
    public List<MaintenancePaymentResponse> getPaymentsByStatus(String status) {
        PaymentStatus paymentStatus = parseStatus(status);
        return paymentRepository.findByStatus(paymentStatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Month + Year
    public List<MaintenancePaymentResponse> getPaymentsByMonthAndYear(Integer month, Integer year) {
        return paymentRepository.findByMonthAndYear(month, year)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Flat + Status
    public List<MaintenancePaymentResponse> getPaymentsByFlatAndStatus(Long flatId, String status) {
        flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + flatId));
        PaymentStatus paymentStatus = parseStatus(status);
        return paymentRepository.findByFlat_FlatIdAndStatus(flatId, paymentStatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Mark Payment as PAID
    public MaintenancePaymentResponse markAsPaid(Long id) {
        MaintenancePayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        if (payment.getStatus() == PaymentStatus.PAID)
            throw new RuntimeException("Payment is already marked as PAID");
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidDate(LocalDate.now());
        return toResponse(paymentRepository.save(payment));
    }

    // Mark Payment as OVERDUE
    public MaintenancePaymentResponse markAsOverdue(Long id) {
        MaintenancePayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        if (payment.getStatus() == PaymentStatus.PAID)
            throw new RuntimeException("Cannot mark a PAID payment as OVERDUE");
        payment.setStatus(PaymentStatus.OVERDUE);
        return toResponse(paymentRepository.save(payment));
    }

    // Update Payment Details
    public MaintenancePaymentResponse updatePayment(Long id, MaintenancePaymentRequest request) {
        MaintenancePayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        Flats flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));

        // Duplicate check — exclude current record
        paymentRepository.findDuplicateExcluding(
                        request.getFlatId(), request.getMonth(), request.getYear(), id)
                .ifPresent(p -> {
                    throw new RuntimeException("Maintenance bill already exists for flat id: "
                            + request.getFlatId() + " for "
                            + request.getMonth() + "/" + request.getYear());
                });

        payment.setAmount(request.getAmount());
        payment.setMonth(request.getMonth());
        payment.setYear(request.getYear());
        payment.setDueDate(request.getDueDate());
        payment.setRemarks(request.getRemarks());
        payment.setFlat(flat);

        return toResponse(paymentRepository.save(payment));
    }

    // Delete Payment
    public String deletePayment(Long id) {
        MaintenancePayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
        return "Payment deleted successfully with id: " + id;
    }

    // Helper: Parse PaymentStatus
    private PaymentStatus parseStatus(String status) {
        try {
            return PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status
                    + ". Valid values: PAID, UNPAID, OVERDUE");
        }
    }

    // Helper: Entity -> Response
    private MaintenancePaymentResponse toResponse(MaintenancePayment payment) {
        return new MaintenancePaymentResponse(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getMonth(),
                payment.getYear(),
                payment.getDueDate(),
                payment.getPaidDate(),
                payment.getStatus(),
                payment.getRemarks(),
                payment.getFlat().getFlatId(),
                payment.getFlat().getFlatAddress()
        );
    }
}
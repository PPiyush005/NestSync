package com.SocietyManagementSystem.SocietyManagementSystem_Backend.amenityBooking;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmenityBookingService {

    private final AmenityBookingRepository bookingRepository;
    private final AuthHelper authHelper;

    public AmenityBookingService(AmenityBookingRepository bookingRepository,
                                 AuthHelper authHelper) {
        this.bookingRepository = bookingRepository;
        this.authHelper = authHelper;
    }

    // Add Booking
    public AmenityBookingResponse addBooking(AmenityBookingRequest request) {
        AmenityType amenityType = parseAmenityType(request.getAmenityType());

        // Conflict detection
        List<AmenityBooking> conflicts = bookingRepository.findConflictingBookings(
                amenityType,
                request.getBookingDate(),
                request.getStartTime(),
                request.getEndTime()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException(amenityType + " is already booked on "
                    + request.getBookingDate() + " between "
                    + request.getStartTime() + " and " + request.getEndTime()
                    + ". Please choose a different time slot.");
        }

        AmenityBooking booking = new AmenityBooking();
        booking.setBookedByName(request.getBookedByName());
        booking.setBookedByMobile(request.getBookedByMobile());
        booking.setAmenityType(amenityType);
        booking.setBookingDate(request.getBookingDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setPurpose(request.getPurpose());
        booking.setStatus(BookingStatus.PENDING); // always starts as PENDING
        booking.setBookedByUsername(authHelper.getCurrentUser().getUsername());

        return toResponse(bookingRepository.save(booking));
    }

    // RESIDENT — view only own bookings
    public List<AmenityBookingResponse> getMyBookings() {
        String username = authHelper.getCurrentUser().getUsername();
        return bookingRepository.findByBookedByUsername(username)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // GetById
    public AmenityBookingResponse getBookingById(Long id) {
        AmenityBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return toResponse(booking);
    }

    // Get All
    public List<AmenityBookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Amenity Type
    public List<AmenityBookingResponse> getBookingsByAmenityType(String type) {
        AmenityType amenityType = parseAmenityType(type);
        return bookingRepository.findByAmenityType(amenityType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Status
    public List<AmenityBookingResponse> getBookingsByStatus(String status) {
        BookingStatus bookingStatus = parseBookingStatus(status);
        return bookingRepository.findByStatus(bookingStatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Date
    public List<AmenityBookingResponse> getBookingsByDate(LocalDate date) {
        return bookingRepository.findByBookingDate(date)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Amenity Type + Date
    public List<AmenityBookingResponse> getBookingsByAmenityTypeAndDate(String type, LocalDate date) {
        AmenityType amenityType = parseAmenityType(type);
        return bookingRepository.findByAmenityTypeAndBookingDate(amenityType, date)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Update Booking Status
    public AmenityBookingResponse updateBookingStatus(Long id, String status) {
        AmenityBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        booking.setStatus(parseBookingStatus(status));
        return toResponse(bookingRepository.save(booking));
    }

    // Update Booking Details
    public AmenityBookingResponse updateBooking(Long id, AmenityBookingRequest request) {
        AmenityBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        AmenityType amenityType = parseAmenityType(request.getAmenityType());

        // Conflict detection — exclude current booking from conflict check
        List<AmenityBooking> conflicts = bookingRepository.findConflictingBookings(
                        amenityType,
                        request.getBookingDate(),
                        request.getStartTime(),
                        request.getEndTime()
                ).stream()
                .filter(b -> !b.getBookingId().equals(id))
                .collect(Collectors.toList());

        if (!conflicts.isEmpty()) {
            throw new RuntimeException(amenityType + " is already booked on "
                    + request.getBookingDate() + " between "
                    + request.getStartTime() + " and " + request.getEndTime()
                    + ". Please choose a different time slot.");
        }

        booking.setBookedByName(request.getBookedByName());
        booking.setBookedByMobile(request.getBookedByMobile());
        booking.setAmenityType(amenityType);
        booking.setBookingDate(request.getBookingDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setPurpose(request.getPurpose());

        return toResponse(bookingRepository.save(booking));
    }

    // Delete Booking
    public String deleteBooking(Long id) {
        AmenityBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        bookingRepository.delete(booking);
        return "Booking deleted successfully with id: " + id;
    }

    // Helper: Parse AmenityType
    private AmenityType parseAmenityType(String type) {
        try {
            return AmenityType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid amenityType: " + type + ". Valid values: GYM, BANQUET_HALL");
        }
    }

    // Helper: Parse BookingStatus
    private BookingStatus parseBookingStatus(String status) {
        try {
            return BookingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status + ". Valid values: PENDING, CONFIRMED, CANCELLED");
        }
    }

    // Helper: Entity -> Response
    private AmenityBookingResponse toResponse(AmenityBooking booking) {
        return new AmenityBookingResponse(
                booking.getBookingId(),
                booking.getBookedByName(),
                booking.getBookedByMobile(),
                booking.getAmenityType(),
                booking.getBookingDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getPurpose(),
                booking.getStatus()
        );
    }
}
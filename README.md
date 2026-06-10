# NestSync — Smart Society Management System

A production-ready **Spring Boot REST API** backend for managing residential societies.
Built with Java 21, Spring Security, JWT Authentication, and MySQL.

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security + JWT (jjwt 0.12.6) |
| Database | MySQL |
| ORM | Spring Data JPA + Hibernate |
| Build Tool | Maven |
| Utilities | Lombok |
| API Testing | Postman |

---

## 📦 Project Structure

Feature-based package structure under:
com.SocietyManagementSystem.SocietyManagementSystem_Backend

├── auth           # Login, Register, JWT token
├── user           # User entity, roles, UserDetailsService
├── security       # JwtUtil, JwtFilter, SecurityConfig, AuthHelper
├── block          # Block CRUD
├── building       # Building CRUD
├── flat           # Flat CRUD
├── owner          # Owner CRUD
├── worker         # Worker CRUD
├── notice         # Society/block notices
├── complaint      # Resident complaints + status tracking
├── events         # Society/block events
├── securityGuard  # Head guard + building guard management
├── visitor        # Visitor entry/exit logging
├── parkingSlot    # Resident + visitor parking
├── amenityBooking # Gym + Banquet Hall booking
├── maintenance    # Monthly maintenance payment tracking
├── gym            # Gym CRUD
└── banquetHall    # Banquet Hall CRUD


---

## 🔐 Authentication & Authorization

JWT-based stateless authentication with 4 roles:

| Role | Access |
|------|--------|
| `ADMIN` | Full access to all modules |
| `SECRETARY` | Manages own block only — buildings, flats, notices, events, complaints, maintenance |
| `RESIDENT` | Read-only + own complaints + own amenity bookings + own maintenance bills |
| `SECURITY_GUARD` | Visitors + Parking + Security Guards (own building only) |

### Auth Endpoints
| Method | URL | Access |
|--------|-----|--------|
| POST | `/api/auth/login` | Public |
| POST | `/api/auth/register` | ADMIN only |
| GET | `/api/auth/me` | All logged-in users |

---

## 📋 Modules & APIs

### Blocks
| Method | URL |
|--------|-----|
| POST | `/api/blocks/add` |
| GET | `/api/blocks/{id}` |
| GET | `/api/blocks/all` |
| PUT | `/api/blocks/update/{id}` |
| DELETE | `/api/blocks/delete/{id}` |

### Buildings
| Method | URL |
|--------|-----|
| POST | `/api/buildings/add` |
| GET | `/api/buildings/{id}` |
| GET | `/api/buildings/all` |
| PUT | `/api/buildings/update/{id}` |
| DELETE | `/api/buildings/delete/{id}` |

### Flats
| Method | URL |
|--------|-----|
| POST | `/api/flats/add` |
| GET | `/api/flats/{id}` |
| GET | `/api/flats/all` |
| PUT | `/api/flats/update/{id}` |
| DELETE | `/api/flats/delete/{id}` |

### Owners
| Method | URL |
|--------|-----|
| POST | `/api/owners/add` |
| GET | `/api/owners/{id}` |
| GET | `/api/owners/all` |
| PUT | `/api/owners/update/{id}` |
| DELETE | `/api/owners/delete/{id}` |

### Workers
| Method | URL |
|--------|-----|
| POST | `/api/workers/add` |
| GET | `/api/workers/{id}` |
| GET | `/api/workers/all` |
| PUT | `/api/workers/update/{id}` |
| DELETE | `/api/workers/delete/{id}` |
| POST | `/api/workers/{workerId}/assign-flat/{flatId}` |
| DELETE | `/api/workers/{workerId}/remove-flat/{flatId}` |

### Notices
| Method | URL |
|--------|-----|
| POST | `/api/notices/add` |
| GET | `/api/notices/{id}` |
| GET | `/api/notices/all` |
| GET | `/api/notices/block/{blockId}` |
| GET | `/api/notices/society-wide` |
| PUT | `/api/notices/update/{id}` |
| DELETE | `/api/notices/delete/{id}` |

### Complaints
| Method | URL |
|--------|-----|
| POST | `/api/complaints/add` |
| GET | `/api/complaints/{id}` |
| GET | `/api/complaints/all` |
| GET | `/api/complaints/status/{status}` |
| GET | `/api/complaints/flat/{flatId}` |
| GET | `/api/complaints/owner/{ownerId}` |
| GET | `/api/complaints/my-complaints` |
| PATCH | `/api/complaints/update-status/{id}?status=` |
| PUT | `/api/complaints/update/{id}` |
| DELETE | `/api/complaints/delete/{id}` |

### Events
| Method | URL |
|--------|-----|
| POST | `/api/events/add` |
| GET | `/api/events/{id}` |
| GET | `/api/events/all` |
| GET | `/api/events/block/{blockId}` |
| GET | `/api/events/society-wide` |
| GET | `/api/events/upcoming` |
| GET | `/api/events/past` |
| PUT | `/api/events/update/{id}` |
| DELETE | `/api/events/delete/{id}` |

### Security Guards
| Method | URL |
|--------|-----|
| POST | `/api/security-guards/add` |
| GET | `/api/security-guards/{id}` |
| GET | `/api/security-guards/all` |
| GET | `/api/security-guards/type/{type}` |
| GET | `/api/security-guards/block/{blockId}` |
| GET | `/api/security-guards/building/{buildingId}` |
| PUT | `/api/security-guards/update/{id}` |
| DELETE | `/api/security-guards/delete/{id}` |

### Visitors
| Method | URL |
|--------|-----|
| POST | `/api/visitors/add` |
| GET | `/api/visitors/{id}` |
| GET | `/api/visitors/all` |
| GET | `/api/visitors/flat/{flatId}` |
| GET | `/api/visitors/date/{date}` |
| GET | `/api/visitors/currently-inside` |
| PATCH | `/api/visitors/mark-exit/{id}?exitTime=` |
| PUT | `/api/visitors/update/{id}` |
| DELETE | `/api/visitors/delete/{id}` |

### Parking Management
| Method | URL |
|--------|-----|
| POST | `/api/parking/add` |
| GET | `/api/parking/{id}` |
| GET | `/api/parking/all` |
| GET | `/api/parking/building/{buildingId}` |
| GET | `/api/parking/flat/{flatId}` |
| GET | `/api/parking/type/{type}` |
| GET | `/api/parking/status/{status}` |
| GET | `/api/parking/building/{buildingId}/type/{type}` |
| GET | `/api/parking/building/{buildingId}/available` |
| PATCH | `/api/parking/assign-vehicle/{slotId}?vehicleNumber=` |
| PATCH | `/api/parking/remove-vehicle/{slotId}` |
| PUT | `/api/parking/update/{id}` |
| DELETE | `/api/parking/delete/{id}` |

### Amenity Booking
| Method | URL |
|--------|-----|
| POST | `/api/amenity-bookings/add` |
| GET | `/api/amenity-bookings/{id}` |
| GET | `/api/amenity-bookings/all` |
| GET | `/api/amenity-bookings/type/{type}` |
| GET | `/api/amenity-bookings/status/{status}` |
| GET | `/api/amenity-bookings/date/{date}` |
| GET | `/api/amenity-bookings/type/{type}/date/{date}` |
| GET | `/api/amenity-bookings/my-bookings` |
| PATCH | `/api/amenity-bookings/update-status/{id}?status=` |
| PUT | `/api/amenity-bookings/update/{id}` |
| DELETE | `/api/amenity-bookings/delete/{id}` |

### Maintenance Payments
| Method | URL |
|--------|-----|
| POST | `/api/maintenance/add` |
| GET | `/api/maintenance/{id}` |
| GET | `/api/maintenance/all` |
| GET | `/api/maintenance/flat/{flatId}` |
| GET | `/api/maintenance/status/{status}` |
| GET | `/api/maintenance/month/{month}/year/{year}` |
| GET | `/api/maintenance/flat/{flatId}/status/{status}` |
| GET | `/api/maintenance/my-bills` |
| PATCH | `/api/maintenance/mark-paid/{id}` |
| PATCH | `/api/maintenance/mark-overdue/{id}` |
| PUT | `/api/maintenance/update/{id}` |
| DELETE | `/api/maintenance/delete/{id}` |

---

## 🗄️ Database Design
Blocks → Buildings → Flats → Owners
→ Workers (Many-to-Many)
Blocks → SecurityGuard (HEAD_SECURITY_GUARD)
Buildings → SecurityGuard (SECURITY_GUARD)
Flats → Complaints
Flats → MaintenancePayments
Flats → Visitors
Flats/Buildings → ParkingSlots
Blocks → Notices (optional)
Blocks → Events (optional)
AmenityBookings (standalone)

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 21
- MySQL
- Maven

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/your-username/NestSync.git
cd NestSync
```

**2. Create MySQL database**
```sql
CREATE DATABASE nestsync;
```

**3. Configure application.properties**
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```
Fill in your database credentials and JWT secret.

**4. Run the project**
```bash
mvn spring-boot:run
```

**5. First login**
```json
POST /api/auth/login
{
  "username": "admin",
  "password": "admin123"
}
```
> ⚠️ Change the default admin password immediately after first login.

---

## 🔑 Key Design Decisions

- **Feature-based packages** — each module is self-contained
- **DTO pattern** — entities never exposed in API responses
- **Context-aware access** — Secretary scoped to block, Guard scoped to building, Resident scoped to flat
- **Conflict detection** — amenity bookings and maintenance bills prevent duplicates
- **CVE-2024-31033 fix** — jjwt upgraded to 0.12.6

---

## 📌 Important Notes

- Default admin credentials are for development only — change after first login
- All APIs require `Authorization: Bearer <token>` header except `/api/auth/login`
- Secretary can only manage data within their assigned block
- Security Guards can only manage visitors and parking for their assigned building

---

## 👨‍💻 Author

**Piyush Parashar**
- GitHub: https://github.com/PPiyush005
- LinkedIn: https://linkedin.com/in/piyush-parashar-58171131b

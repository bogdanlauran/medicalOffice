package ro.upt.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.upt.project.models.Appointment;
import ro.upt.project.models.User;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByTimeAfterAndTimeBeforeAndDoctor(LocalDateTime after, LocalDateTime before, User doctor);
}

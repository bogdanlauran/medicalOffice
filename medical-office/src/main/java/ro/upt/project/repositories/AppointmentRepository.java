package ro.upt.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.upt.project.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}

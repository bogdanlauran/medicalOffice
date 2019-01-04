package ro.upt.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.upt.project.models.Appointment;
import ro.upt.project.models.Role;
import ro.upt.project.models.User;
import ro.upt.project.repositories.AppointmentRepository;
import ro.upt.project.repositories.UserRepository;
import ro.upt.project.utils.AppointmentDto;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public UserService(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<User> getDoctors() {
        return userRepository.findAllByRole(Role.DOCTOR);
    }

    public void createAppointment(AppointmentDto appointmentDto) {
        LocalDateTime time = LocalDateTime.parse(appointmentDto.getTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm:ss"));
        Appointment appointment = new Appointment();
        String patientUsername = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(patientUsername).orElseThrow(EntityNotFoundException::new);

        appointment.setDoctor(userRepository.findById(appointmentDto.getDoctorId()).orElseThrow(EntityNotFoundException::new));
        appointment.setDetails(appointmentDto.getDetails());
        appointment.setTime(time);
        appointment.setPatient(user);

        appointmentRepository.save(appointment);
    }
}

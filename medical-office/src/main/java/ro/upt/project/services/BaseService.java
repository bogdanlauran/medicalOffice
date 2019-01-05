package ro.upt.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.upt.project.exceptions.MOGenericException;
import ro.upt.project.models.Appointment;
import ro.upt.project.models.Role;
import ro.upt.project.models.User;
import ro.upt.project.repositories.AppointmentRepository;
import ro.upt.project.repositories.UserRepository;
import ro.upt.project.utils.AppointmentDto;
import ro.upt.project.utils.RegisterDto;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BaseService {
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public BaseService(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<User> getDoctors() {
        return userRepository.findAllByRole(Role.DOCTOR);
    }

    public void createAppointment(AppointmentDto appointmentDto) {
        LocalDateTime time = LocalDateTime.parse(appointmentDto.getTime());

        // only 9 - 17 appointments allowed
        if(time.getHour() < 9 || time.getHour() > 17) {
            throw new MOGenericException("Please choose a time between 9:00 AM and 17:00.");
        }

        // not allowed in the past or right now.
        if(time.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new MOGenericException("You can't place an appointment at that time.");
        }

        User doctor = userRepository.findById(appointmentDto.getDoctorId()).orElseThrow(EntityNotFoundException::new);

        // don't allow overlapping appointments
        if(appointmentRepository.existsByTimeAfterAndTimeBeforeAndDoctor(time.minusMinutes(30), time, doctor)) {
            throw new MOGenericException("An appointment already exists at that time.");
        }

        Appointment appointment = new Appointment();
        String patientUsername = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(patientUsername).orElseThrow(EntityNotFoundException::new);

        appointment.setDoctor(doctor);
        appointment.setDetails(appointmentDto.getDetails());
        appointment.setTime(time);
        appointment.setPatient(user);

        appointmentRepository.save(appointment);
    }

    public void createUser(RegisterDto registerDto) {
        User user = new User();

        if(registerDto.getFirstName() == null || registerDto.getFirstName().length() < 3 ||
                registerDto.getLastName() == null || registerDto.getLastName().length() < 3 ||
                registerDto.getUsername() == null || registerDto.getUsername().length() < 3 ||
                registerDto.getPassword() == null || registerDto.getPassword().length() < 3) {
            throw new MOGenericException("All fields must have al least 3 characters.");
        }

        user.setRole(Role.PATIENT);
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());

        userRepository.save(user);
    }

    public List<Appointment> getAppointments() {
        User user = userRepository.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).orElseThrow(EntityNotFoundException::new);

        if(user.getRole() == Role.PATIENT) {
            return user.getPatientAppointments();
        }

        return user.getDoctorAppointments();
    }

    public void removeAppointment(long id) {
        appointmentRepository.deleteById(id);
    }
}

package ro.upt.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.upt.project.services.UserService;
import ro.upt.project.utils.AppointmentDto;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, Principal principal) {
        model.addAttribute("isAnonymous", principal == null);
        model.addAttribute("doctors", userService.getDoctors());
        return "index";
    }

    @GetMapping("/appointment")
    public String appointment(Model model) {
        model.addAttribute("doctors", userService.getDoctors());
        model.addAttribute("appointment", new AppointmentDto());
        return "appointment";
    }

    @PostMapping("/appointment")
    public String newAppointment(@ModelAttribute AppointmentDto appointmentDto, Model model) {
        userService.createAppointment(appointmentDto);
        return "redirect:/";
    }
}

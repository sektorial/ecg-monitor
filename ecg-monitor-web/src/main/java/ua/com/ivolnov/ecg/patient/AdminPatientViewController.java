package ua.com.ivolnov.ecg.patient;

import java.util.Optional;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.ivolnov.ecg.integration.EcgSourceConnector;

import static ua.com.ivolnov.ecg.common.UserRole.ADMIN;

@PreAuthorize("hasRole('" + ADMIN + "')")
@RequestMapping("/web/admin/patient")
@Controller
@RequiredArgsConstructor
public class AdminPatientViewController {

    private final PatientService patientService;
    private final EcgSourceConnector ecgSourceConnector;

    @PostConstruct
    public void initStubPatient() {
        final Patient patient = patientService.addPatient("patient #1");
        ecgSourceConnector.addPatientSource(patient);
    }

    @GetMapping
    public ModelAndView getPatients() {
        final ModelAndView view = new ModelAndView("admin-patient");
        view.addObject("msg", "Patient Management");
        view.addObject("patients", patientService.getAllPatients());
        return view;
    }

    @PostMapping("/add")
    public String addPatient(@RequestParam final String name) {
        final Patient patient = patientService.addPatient(name);
        ecgSourceConnector.addPatientSource(patient);
        return "redirect:/web/admin/patient";
    }

    @PostMapping("/remove")
    public String removePatient(@RequestParam final UUID id) {
        final Optional<Patient> patient = patientService.removePatient(id);
        patient.ifPresent(ecgSourceConnector::removePatientSource);
        return "redirect:/web/admin/patient";
    }
}

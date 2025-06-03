package ua.com.ivolnov.ecg.patient;

import java.util.Optional;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/web/admin/patient")
@Controller
@RequiredArgsConstructor
public class AdminPatientViewController {

    private final PatientService patientService;
    private final EcgSourceStub ecgSourceStub;

    @PostConstruct
    public void initStubPatient() {
        final Patient patient = patientService.addPatient("patient #1");
        ecgSourceStub.addPatientSource(patient);
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
        ecgSourceStub.addPatientSource(patient);
        return "redirect:/web/admin/patient";
    }

    @PostMapping("/remove")
    public String removePatient(@RequestParam final UUID id) {
        final Optional<Patient> patient = patientService.removePatient(id);
        patient.ifPresent(ecgSourceStub::removePatientSource);
        return "redirect:/web/admin/patient";
    }
}

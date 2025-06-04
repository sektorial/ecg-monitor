package ua.com.ivolnov.ecg.data;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ua.com.ivolnov.ecg.common.UserRole.STAFF;

@PreAuthorize("hasRole('" + STAFF + "')")
@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
class EcgDataController {

    private final EcgDataStorage storage;


    @GetMapping("/{id}/ecg")
    List<EcgData> getRecentEcgData(@PathVariable final UUID id,
                                   @RequestParam(value = "fromMillis", required = false) final Long fromMillis) {
        if (fromMillis != null) {
            return storage.getSince(id, fromMillis);
        }
        return storage.getAll(id);
    }

}


package ua.com.ivolnov.ecg.notifier;

import ua.com.ivolnov.ecg.data.EcgData;
import ua.com.ivolnov.ecg.patient.Patient;

public interface WsNotifier {

    void notify(final Patient patient, final EcgData ecgData);

}

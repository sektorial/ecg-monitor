package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

interface WsNotifier {

    void notify(final UUID patientId, final EcgData ecgData);

}

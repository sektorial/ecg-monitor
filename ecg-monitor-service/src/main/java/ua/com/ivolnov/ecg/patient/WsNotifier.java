package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

interface WsNotifier {

    void notify(final Patient patient, final EcgData ecgData);

}

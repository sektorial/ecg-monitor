import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import {addEcgPoint, initializeEcgChart} from './chart-helper';

let stompClient = null;

const connectPatientPageWS = (patientId) => {
    const socket = new SockJS('/ws-alerts');
    stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
            stompClient.subscribe(`/topic/patient/${patientId}`, (msg) => {
                try {
                    const data = JSON.parse(msg.body);
                    addEcgPoint(data);
                } catch (e) {
                    // show warning/error
                }
            });
        }
    });
    stompClient.activate();
}

const disconnectPatientPageWS = () => {
    if (stompClient) {
        stompClient.deactivate();
        stompClient = null;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    if (window.patientId) {
        initializeEcgChart();
        connectPatientPageWS(window.patientId);
        window.addEventListener('beforeunload', disconnectPatientPageWS);
    }
});

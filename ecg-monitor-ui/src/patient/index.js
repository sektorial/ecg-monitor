import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import {addEcgPoint, initializeEcgChart} from './chart-helper';

let stompClient = null;

async function loadHistoricalEcg(patientId) {
    const fromMillis = Date.now() - 3600_000;
    const response = await fetch(`/api/patient/${patientId}/ecg?from=${fromMillis}`);
    if (!response.ok) {
        alert('Failed to load ECG history');
        return;
    }
    const history = await response.json();
    history.forEach(addEcgPoint);
}

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

document.addEventListener('DOMContentLoaded', async () => {
    if (window.patientId) {
        initializeEcgChart();
        await loadHistoricalEcg(window.patientId);
        connectPatientPageWS(window.patientId);
        window.addEventListener('beforeunload', disconnectPatientPageWS);
    }
});

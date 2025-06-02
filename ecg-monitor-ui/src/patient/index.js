import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';

let stompClient = null;

const patientId = window.patientId;

function connectPatientPageWS() {
    if (!patientId) {
        console.log('No patientId');
        return;
    }
    const socket = new SockJS('/ws-alerts');
    stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
            stompClient.subscribe(`/topic/patient/${patientId}`, (msg) => {
                const data = JSON.parse(msg.body);
                console.log("ECG value:", data.value, "at", new Date(data.ts).toLocaleTimeString());
                // TODO render chart here
            });
        }
    });
    stompClient.activate();
}

function disconnectPatientPageWS() {
    if (stompClient) {
        stompClient.deactivate();
        stompClient = null;
    }
}

if (patientId) {
    connectPatientPageWS();
    window.addEventListener('beforeunload', disconnectPatientPageWS);
}

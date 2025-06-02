import _ from 'lodash';
import './index.css';
import './patient/index.js';
import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';

const footer = document.querySelector('.footer');
if (footer) {
    footer.textContent = _.join([
        'ECG Monitor',
        'V1.0, 2025'
    ], ' ');
}

const showAlert = message => {
    const alertBox = document.createElement('div');
    alertBox.className = 'ws-alert';
    alertBox.innerText = message;
    document.body.appendChild(alertBox);
    setTimeout(() => alertBox.remove(), 4000);
}

if (window.userRole === 'STAFF') {
    const client = new Client({
        webSocketFactory: () => new SockJS('/ws-alerts'),
        onConnect: () => {
            client.subscribe('/topic/alerts', message => {
                showAlert(message.body);
            });
        }
    });

    client.activate();
}

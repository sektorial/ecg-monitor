import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';

const ALERT_CONTAINER_ID = 'alertContainer';

const getAlertContainer = () => {
    let container = document.getElementById(ALERT_CONTAINER_ID);
    if (!container) {
        container = document.createElement('div');
        container.id = ALERT_CONTAINER_ID;
        container.style.cssText = `
            position: fixed; top: 24px; right: 24px; z-index: 9999;
            display: flex; flex-direction: column; gap: 14px; align-items: flex-end;
            pointer-events: none;
        `;
        document.body.appendChild(container);
    }
    return container;
}

const showAlert = ({
                       patient_id,
                       patient_name,
                   }) => {
    const message = 'has a critical state';
    const alertBox = document.createElement('div');
    alertBox.className = 'alertBox';
    alertBox.style.cssText = `
        background: #ffefc1; border: 1.5px solid #ffa200; 
        color: #212121; padding: 16px 28px 16px 20px;
        border-radius: 10px; box-shadow: 0 4px 24px #0002;
        font-size: 1.1em; display: flex; align-items: center; min-width: 250px;
        pointer-events: auto;
    `;

    const textSpan = document.createElement('span');
    textSpan.style.flex = '1';
    const alertLink = document.createElement('a');
    alertLink.href = `/web/staff/patient/${patient_id}`;
    alertLink.innerText = patient_name || patient_id;
    alertLink.style.cssText = 'color:#3476c1;text-decoration:underline; font-weight:bold; cursor:pointer; margin-right:4px;';

    textSpan.innerHTML = `🚨 `;
    textSpan.appendChild(alertLink);
    textSpan.insertAdjacentText('beforeend', `: ${message}`);

    const closeBtn = document.createElement('button');
    closeBtn.innerHTML = '✕';
    closeBtn.style.cssText = `
        background:none;border:none;font-size:1.2em;cursor:pointer;
        color:#888;margin-left:14px;
    `;

    const dismiss = () => {
        alertBox.remove();
    };

    alertLink.addEventListener('click', dismiss);
    closeBtn.addEventListener('click', dismiss);
    // Optional: clicking anywhere else on the alert
    alertBox.addEventListener('click', (e) => {
        if (e.target === alertLink || e.target === closeBtn) return;
        dismiss();
    });

    alertBox.appendChild(textSpan);
    alertBox.appendChild(closeBtn);
    getAlertContainer().appendChild(alertBox);
};

if (window.userRole === 'STAFF') {
    const client = new Client({
        webSocketFactory: () => new SockJS('/ws-alerts'),
        onConnect: () => {
            client.subscribe('/topic/alerts', message => {
                let alertData;
                try {
                    alertData = typeof message.body === 'string' ? JSON.parse(message.body) : message.body;
                } catch {
                    alertData = message.body;
                }
                showAlert(alertData);
            });
        }
    });

    client.activate();
}

import _ from 'lodash';
import './index.css';
import './patient/index.js';
import './alert';

const footer = document.querySelector('.footer');
if (footer) {
    footer.textContent = _.join([
        'ECG Monitor',
        'V1.0, 2025'
    ], ' ');
}


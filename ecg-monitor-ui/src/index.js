import _ from 'lodash';
import './index.css';

const footer = document.querySelector('.footer');
if (footer) {
    footer.textContent = _.join([
        'ECG Monitor',
        'V1.0, 2025'
    ], ' ');
}


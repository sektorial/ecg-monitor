import _ from 'lodash';

const component = () => {
    const element = document.createElement('div');
    element.innerHTML = _.join([
        'ECG Monitor',
        'V1.0, 2025'
    ], ' ');
    return element;
}

document.body.appendChild(component());

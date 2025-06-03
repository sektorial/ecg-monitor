import Chart from 'chart.js/auto';
import zoomPlugin from 'chartjs-plugin-zoom';
import streamingPlugin from 'chartjs-plugin-streaming';
import 'chartjs-adapter-date-fns';


Chart.register(zoomPlugin, streamingPlugin);

const VISIBLE_MILLIS = 15_000;
const REDRAW_MILLIS = 40;
const RETAIN_MILLIS = 3600_000;

let ecgChart = null;

export const initializeEcgChart = () => {
    const canvas = document.getElementById('ecgChart');
    if (!canvas) {
        alert('Could not find canvas element with id "ecgChart"');
        return;
    }
    const ctx = canvas.getContext('2d');
    ecgChart = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: [
                {
                    label: 'ECG (mV)',
                    data: [],
                    borderColor: '#5c3dfa',
                    borderWidth: 1.8,
                    fill: false,
                    tension: 0,
                    pointBackgroundColor: (ctx) => {
                        const raw = ctx.raw || {};
                        return raw.critical ? '#fa393d' : '#5c3dfa';
                    },
                    pointRadius: (ctx) => {
                        const raw = ctx.raw || {};
                        return raw.critical ? 3 : 0.2;
                    },
                    pointBorderWidth: (ctx) => {
                        const raw = ctx.raw || {};
                        return raw.critical ? 2 : 1;
                    },
                    pointBorderColor: (ctx) => {
                        const raw = ctx.raw || {};
                        return raw.critical ? '#b80b1c' : '#5c3dfa';
                    },
                }
            ]
        },
        options: {
            animation: false,
            responsive: true,
            scales: {
                x: {
                    type: 'realtime',
                    realtime: {
                        duration: VISIBLE_MILLIS,
                        refresh: REDRAW_MILLIS,
                        delay: 0,
                        pause: false,
                        ttl: RETAIN_MILLIS,
                        onRefresh: null
                    },
                    title: {
                        display: true,
                        text: 'Time (s)'
                    },
                    ticks: {}
                },
                y: {
                    title: {
                        display: true,
                        text: 'Voltage (mV)'
                    },
                    suggestedMin: -2,
                    suggestedMax: 2
                }
            },
            plugins: {
                legend: {display: false},
                zoom: {
                    pan: {
                        enabled: true,
                        mode: 'x'
                    },
                    zoom: {
                        wheel: {
                            enabled: true,
                        },
                        drag: {
                            enabled: false
                        },
                        pinch: {
                            enabled: false
                        },
                        mode: 'x'
                    }
                }
            }
        }
    });
};

export const addEcgPoint = ({
                                ts_millis,
                                voltage,
                                critical
                            }) => {
    if (!ecgChart) {
        alert('ECG chart not initialized. Please wait for the page to load.');
        return;
    }
    ecgChart.data.datasets[0].data.push({
        x: ts_millis,
        y: voltage,
        critical: !!critical
    });
};

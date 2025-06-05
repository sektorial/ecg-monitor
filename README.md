# ECG Monitoring Application

This project focuses on the development of a real-time ECG monitoring system. It enables medical staff to monitor  
patients' heart activity through a web application that displays live ECG data, stores historical records, and
notifies staff when critical states are detected by an AI model.

# Preconditions

1. Node.js v22.16.0 installed
2. Java 17 installed
3. Maven 3.6.3 installed

# Development

To speed up FE development HRM might be enabled:

- set the `ecg.development.hrm-enabled` property value to `true`
- execute `npm start` in terminal from `ecg-monitor-ui` subproject

From time to time NPM development server might start running in background.  
Use next commands in Windows to kill the process:

```commandline
netstat -ano | findstr :3000
taskkill /PID <pid_here> /F
```

# Application Startup

Execute next commands from command line under the project root directory:

1. ```mvn clean verify -Pprod```
2. ```java -jar .\ecg-monitor-web\target\ECG-MONITOR_prod_<version>.jar```, where `<version>` is the
   current [project version](pom.xml)



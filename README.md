# ECG Monitoring Application

This project focuses on the development of a real-time ECG monitoring system. It enables medical staff to monitor  
patients' heart activity through a web application that displays live ECG data, stores historical records, and
notifies staff when critical states are detected by an AI model.

# Preconditions

1. Node.js v22.16.0 installed
2. Java 17 installed
3. Maven 3.6.3 installed
4. Tomcat 9.0.71 installed

# Development

To speed up FE development HRM might be enabled:

- set the `ecg.development.hrm-enabled` property value to `true`
- execute `npm start` in terminal from `ecg-monitor-ui` subproject

From time to time NPM development server might start running in background.  
Use next commadns for Windows to kill the process:

```commandline
netstat -ano | findstr :3000
taskkill /PID <pid_here> /F
```

# Application Startup
1. Build project artifact, executing from the project root folder:
```commandline
mvn clean verify
```
2. Deploy the WAR artifact under `./ecg-monitor-web/target` to Tomcat



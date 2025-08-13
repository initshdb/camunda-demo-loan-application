# Camunda Loan Application Process

## BPMN
[📄 Loan Application Process BPMN](src/main/resources/LoanApplicationProcess.bpmn)  
  
![BPMN Diagram](docs/Loan_Application_Process_BPMN.png)

## Demo 
A short demo of the Loan Application process covering multiple scenarios.  
[📥 Demo Video](docs/Loan_Application_Process_Demo.mp4)

### Scenarios Demonstrated
1. **Auto Approval** – Loan amount **< 1000**  
   ⏱ **Time:** 0:00 – 0:40

2. **Loan Review** – Loan amount **> 1000**, **approved** with **manager escalation** every 1 minute (for demo) until review task is completed  
   ⏱ **Time:** 0:40 – 3:50

3. **Loan Review** – Loan amount **> 1000**, **rejected**  
   ⏱ **Time:** 3:50 – END
# InsurancePro
InsurancePro is a web-based Insurance Broker Management System developed using Java Servlets. This system allows insurance brokers to manage customer data, insurance policies, and claims efficiently. The project implements the Repository design pattern for file-based storage (XML/JSON) and follows J2EE Web Application Architecture principles.

## Team Members
- Rajat Sachdeva
- Samruddhi Chavan
- Sruthi Jayaprakash Pandiath
- Manpreet Kaur Gulati
- Shrabani Sagareeka

## Features
- User authentication and registration
- Manage customer, policy, and claim data
- CRUD operations with file-based storage
- Thread-safe operations
- Reporting functionalities

## Getting Started
To get a copy of the project up and running on your local machine, follow these steps:
- Clone the repository: `git clone https://github.com/r-sachdeva3105/InsurancePro.git`
- Navigate to the project directory: `cd InsurancePro`
- Set up the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse)
- Configure your environment (e.g., Java SDK, web server)

## Requirements
- Java JDK 11 or higher
- Apache Tomcat or any Java servlet container

## Access the Application
- Run the application and login into the application.
- Login using dummy username and password to access the dashboard and other functionality.

## Limitations
- Utilizing ServletContext to obtain the real path of the JSON files instead of depending on absolute paths. This ensures that the application accesses the JSON files located in the .metadata folder of the Eclipse workspace rather than those in the webapp folder.
- To verify the contents of the JSON files, check the eclipse console for path information.
- To address this inconsistency, the application will be migrated to a database.

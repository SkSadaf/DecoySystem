
# Decoy System

<ins><b>Overview</b></ins>

The Decoy System is a Java-based application that establishes a secure communication system among Firewall, Server, and User components. This system facilitates secure file transfers between the User and Server through the Firewall, ensuring the integrity and confidentiality of the communication.

<ins><b>Technologies Used</b></ins>

- **Java:** The primary programming language for implementing the application.
- **JDBC:** Java Database Connectivity for connecting to the ODBC database.
- **Sockets:** Java sockets are employed for secure communication between components.
- **Swing:** GUI components for creating a user-friendly interface.

<ins><b>Features</b></ins>

1. **Firewall:**
   - Secure File Transfer: Enables users to download files securely from the Server.
   - System Status Update: Updates the system status in the database upon exiting.

2. **Server:**
   - Status Display: Displays and updates the Server status in the database upon exiting.
   - File Handling: Allows file transfers upon User requests.

3. **User:**
   - User Interaction: Facilitates file downloads from the Server through the Firewall.
   - Status Update: Updates the User's status in the database upon exiting.

<ins><b>Requirements</b></ins>

- Java Runtime Environment (JRE)
- ODBC Database Connection
- Java Swing Library

<ins><b>Components</b></ins>

1. **Firewall (`Firewall.java`):**
   - Initiates and manages the Firewall application.
   - Facilitates secure file downloads and updates the system status.

2. **Server (`Server.java`):**
   - Manages the Server application.
   - Displays and updates Server status.
   - Handles file transfers upon User requests.

3. **User (`User.java`):**
   - Manages the User application.
   - Allows the User to request and download files.
   - Updates the User's status in the database.

<ins><b>Database Connectivity</b></ins>

- Connects to an ODBC database named "decoysystem" for storing user and system information.
- Ensures accurate system status updates by updating the database upon application exit.

<ins><b> File Transfer Mechanism</b></ins>

- Users request file downloads by specifying the desired file name.
- The Firewall initiates secure communication with the Server to facilitate the file transfer process.
- Java sockets are utilized for secure and reliable file transfers.

<ins><b> Usage</b></ins>

1. **Firewall Application:**
   - Execute `Firewall.java`.
   - Initiate secure file downloads from the Server.
   - Update the system status upon application exit.

2. **Server Application:**
   - Execute `Server.java`.
   - Monitor and update the Server's status in the database upon exiting.

3. **User Application:**
   - Execute `User.java`.
   - Enter the desired file name for download.
   - Update the User's status upon exiting.

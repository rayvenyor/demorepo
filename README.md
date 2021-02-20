## Purpose of the Project

The purpose of this project is to import a 10-column CSV into an SQLite database. Upon calling the POST endpoint, the system will insert all the valid rows into the SQLite database.

The system assumes that the CSV being consumed is 10 columns in length and will tag rows that don't have exactly 10 columns as bad data and will be stored in a separate CSV file for reporting purposes. A brief log file will also be generated along with the generated CSV file containing the bad data. It reports how many rows were processed, how many rows were actually inserted in the database, and how many rows were tagged as bad data.

Each time the POST API for the data import is called, it will reset the `client` table within the SQLite database.

## How to Run the Application

1. Make sure you're local machine is running on JDK 8 since the Spring Boot project uses Java 8.

2. Perform the Maven command  `mvn clean install -U` before running the project.

3. Execute the POST API using Postman or curl command:

   ####  **POSTMAN Steps:**

   1. Fill up the following fields in postman:

      ```yaml
      Request Method: POST
      url: localhost:8080/api/v1/client
      Headers: content-type: application/json
      ```

      For the request body, take note of the file name of the CSV to import and its absolute file path.

      ```json
      Request Body:
      {
        "fileName": "sampleFileName",
        "filePath": "/Users/user/Documents/sampleFileName.csv"
      }
      ```

   #### **cURL Steps:**

    1. Execute this command:

       ```curl
       curl --location --request POST 'http://localhost:8080/api/v1/client' 
       --header 'Authorization: ' 
       --header 'Content-Type: application/json' 
       --data-raw '{
           "fileName": "sampleFileName",
           "filePath": "/Users/user/Document/sampleFileName.csv.csv"
       }'
       ```

4. The output (the CSV File containing the bad data and the log file) will be generated in the project's classpath formatted as such:

   ```python
   sampleFileName-bad.csv #File with bad data
   sampleFileName.log #Log file
   ```

5. To verify how many records have been inserted into the SQLite database, a GET API can be called to output the number of records inserted in the database. To do this, just do the same steps in **Step 3 ** and just replace the request method **POST** to **GET**. 

   

## Design Overview

The code observes MVC architecture because this type of architecture is the most suitable design pattern to follow for Spring Boot projects. It promotes faster development processes since it loosely couples components from each other which makes the codebase more scalable, extensible, and more robust.

A few useful dependencies aside from the required dependecies are also included. Lombok for saving time and lines of code by not having to provide encapsulation methods and constructors because Lombok automatically generates them for you. Java Validation API also is included as a dependency for simple base validation for the models. Java Persistence API is also added to make CRUD database operations easier and also to reduce the total lines of code.

Of course, the code adheres to the 4 basic principles of object oriented programming (Encapsulation, Abstraction, Inheritance, and Polymorphism) by:

- Setting the variables of the model objects to private and generating accessor and mutator methods for them using Lombok.
- Applying polymorphism and abstraction to the service layer methods to provide scalability and reduce future code issues.

The estimated amount of time that it took to complete the code is about 2 full working days for the code to work and half a day for reviewing the code and writing the documentation.

# Button League

Button league is a web application for managing a football league. Users can see view played and upcoming matches, the league table and the teams and players. After logging in with an admin account you can manage the matches, teams and players. You can record scores of the matches after which the application calculates the league table accordingly.

The applicaiton doesn't have any styling as it was built as a backend and Spring Boot learning project.

## Built with
* Java
* Spring Boot
* Maven
* PostgreSQL

## Getting started

### Prerequisites
* Java JDK
* Maven
* PostgreSQL

### Installing and starting the application
1. Set up a PostgreSQL database:
   * Locally according to [PostgreSQL documentation](https://www.postgresql.org/docs/current/).
   * Or use a cloud service like [Railway](https://railway.app/template/postgres).
  
2. Set environment variables:

    `PGHOST`: Hostname of your PostgreSQL server.

    `PGPORT`: Port on which your PostgreSQL server is running.

    `PGDATABASE`: Name of the database you created for the application.

    `PGUSERNAME`: Your PostgreSQL username.

    `PGPASSWORD`: Your PostgreSQL password.

3. Clone the project:

    ```
    git clone https://github.com/ollimyy/button-league button_league
    cd button_league
    ```
4. Build the project:
   
   `mvn clean install`

5. Start the application

    `mvn spring-boot:run`

6. The application should now be available at http://localhost:8080.


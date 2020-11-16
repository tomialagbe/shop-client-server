
## App structure

#### Backend

The backend is implemented with Java/Dropwizard and provides an endpoint to load products from the database.
The database is pre-populated when the server is started.

## Setting up the app
- Run `docker-compose up` to start the backend server.
- Run the following commands to start the frontend app

````
cd shopclient
````

````
npm install
````

````
npm run start
````

The frontend can the be accessed at http://localhost:3000

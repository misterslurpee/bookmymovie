# Movie Ticket System

![coverage](https://gitlab.com/ramadistra/spring/badges/master/coverage.svg)

`Movie Ticket System` is an online platform to buy movie ticket.
This project is created to complete Advanced Programming final project.

## Developer Section

### Running The Code
Navigate to one of the service (movie, ticket, purchase) then
run `./mvnw spring-boot:run`. You can also just simply run 
the Application class in Intellij IDEA. The service should be
active in these following ports:
- movie: 8080
- ticket: 8081
- purchase: 8082

Do notice that if you want to start all the services, you need
to open three terminal sessions and run each services independently.

### Testing

View Maven Windows with these steps:
View -> Tool Windows -> Maven

After that, on the right of Intellij you will see Maven Windows.
Follow these steps:
parent -> Lifecycle -> test -> run (green play button in Maven Windows, not main toolbar)
parent -> Plugins -> jacoco -> jacoco:report-aggregate

to view coverage, see file explorer (left window) then
target -> site -> jacoco-aggregate -> right click on index.html -> open in browser

### Deployment
Run this command:
git push heroku `git subtree split --prefix service-folder-name master`:master --force

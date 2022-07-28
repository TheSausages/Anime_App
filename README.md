# Anime_App
Rewrite of my Bachelor's Degree Application

[link to Frontend here](https://github.com/TheSausages/Anime_Web_Application-Fronend "FrontEnd")

[link to Backend here](https://github.com/TheSausages/Anime_Web_Application-Backend "Backend")

### When starting on a new machine
1) install docker, node itp. (write what later)
2) run [basic-docker-compose](docker/compose/basic-docker-compose.yml)
3) open [keycloak admin console](http://127.0.0.1:8180/auth/)
4) log into administation console using *admin* and *Password1*
5) go into *Clients* -> *Default_User_Server* -> *Credentials* and regenerate the key
6) paste the key into *keycloakrealm.userserver.clientsecret* in [app properties](Anime_App/src/main/resources/application.properties)
7) do the same for *admin-cli* instead of *Default_User_Server* (use *keycloakrealm.master.clientsecret*)

### What to write about later
- enums in the database are done using varrach + check - this is because the driver does not work with enum Types saved in string form

### Small TODO
- Change log in, so it uses a keycloak page redirect, not a backend request
- add ci/cd pipeline to github actions (https://github.blog/2022-02-02-build-ci-cd-pipeline-github-actions-four-steps/)
- For date and datetime fields, save timezone information (or use instant and add timezone in front)
- (Think about) Change entities to use a long as IDs
- think about equals and hashcode for database entities ([https://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma/5729992#5729992](https://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma/5729992#5729992))
- Think how to update Anime data (ex. average episode duration time) from time to time (every x request, or every day on a given hour etc.)
- Create pages + endpoint for getting all threads/posts/achievements etc. for a given user

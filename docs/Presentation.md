# Presentation

Here is where the fronend is handled, and the data is rendered to the user. We use java servlets coupled with jsp pagesfor this task. For every page, we have one or two servlets acting upon it.

## Filter Servlets
Security filter intercepts all requests to our application, and checks if the user is authenticated, if not redirects him to the login page
# Business
The business side of our application is simple since the logic throughout our application is not that complex and can be handled easily by our servlets. The only logic we need is the authentication service, which we provide as an EJB component to hash users passwords when they register, as well as to check their passwords with their hashed ones that are stored in the database during login.
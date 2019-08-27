# codefellowship
## Description
This site is an acedemic project to use Java, Sping, web security and ect.  
Note: Users must set the application.properties values to your postgresql username.  
spring.datasource.username=  
spring.datasource.password=  
These are located [here]().
This will delete and create a clean table each run unless the last line ending in create is commented out. NOTE: The database codefellowship is required for this application to run correctly. Change to update to keep data presentent.
## Routes
### GetMappings
/ - Home page  
/users - This route displays all users in the system.  
/user/{id} - This route will display a particular users info and profile picture. (Publicly seen)  
/login - Used to show the login form.  
/signup - Used to allow users to register a new account.  
/myprofile - Shown after logging in, this page is the users profile page. (Private)  
### PostMappings
/users - Creates a new user and adds it into the database.
/login - Logs the user in and redirects to myprofile

# BlogApp
Blog App
Technical Requirements

Create a Table in SQL database(MySQL,Postgres ... whatever you are more familiar)
Name of Table : BlogPost
Fields will be: (id, title, content, category_id, date, author)
PRIMARY KEY : id
Not Null constraints: title,content
Unique constraints: title
category_id is a foreign key of BlogCategory 
Default(current time): date (this will serve as last modified date for the entity)
Create a BlogCategory table
Name of Table : BlogCategory 
Fields will be: (id, name)
PRIMARY KEY : id
Not Null constraints: name
Populate the table with test data

Expose  the following api endpoints 
GET: /api/posts (return list of all BlogPosts)
GET: /api/posts/{id} (return a single blogpost ,dependent from the parameter you passed in)
POST: /api/posts  (add a post)
PUT: /api/posts   (modify a post)
DELETE: /api/posts (deletes a post)
GET: /api/posts/filter (returns a list of filtered blogposts) - Bonus

SpringBoot will be used to create the endpoints. 
The endpoints will be exposed by RestControllers
Connect to the database you created above with hibernate, make the configuration in spring boot for connecting in database.
Use Spring data repositories (JPA) to access  database and retrieve the information.
Use RestControllers to expose the information retrieved from spring data repositories.
Apply Spring Security In-Memory Authentication for allowing user with username: admin and password: 12345678 to login. All endpoints should be authorized.
Bonus: add author (logged-in user) when creating a new blog post (don't forget the db)

Frontend consists of HTML and Javascript
Apply css styling
To consume the endpoints above use asynchronous javascript
Store everything in a git repo
Functional requirements

Content of the blogpost can be html
Form to create a new blogpost.
Table to show all blogposts and their attribute values. Bonus: Include filters to search for blogposts by title or category
The table mentioned above should have an actions columns, with buttons to edit and delete
Edit button redirect to the view of editing a blogpost: form with fields filled with blogpost data
Delete button used for deleting the entity. Before deleting, show a confirmation dialog

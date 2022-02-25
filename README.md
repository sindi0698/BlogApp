
Blog App<br /><br />

Technical Requirements<br />

Create a Table in SQL database(MySQL,Postgres ... whatever you are more familiar)<br />
Name of Table : BlogPost<br />
Fields will be: (id, title, content, category_id, date, author)<br />
PRIMARY KEY : id<br />
Not Null constraints: title,content<br />
Unique constraints: title<br />
category_id is a foreign key of BlogCategory <br />
Default(current time): date (this will serve as last modified date for the entity)<br />
Create a BlogCategory table<br />
Name of Table : BlogCategory <br />
Fields will be: (id, name)<br />
PRIMARY KEY : id<br />
Not Null constraints: name<br />
Populate the table with test data<br />
<br /><br />
Expose  the following api endpoints <br />
GET: /api/posts (return list of all BlogPosts)<br />
GET: /api/posts/{id} (return a single blogpost ,dependent from the parameter you passed in)<br />
POST: /api/posts  (add a post)<br />
PUT: /api/posts   (modify a post)<br />
DELETE: /api/posts (deletes a post)<br />
GET: /api/posts/filter (returns a list of filtered blogposts) - Bonus<br /><br />

SpringBoot will be used to create the endpoints. <br />
The endpoints will be exposed by RestControllers<br />
Connect to the database you created above with hibernate, make the configuration in spring boot for connecting in database.<br />
Use Spring data repositories (JPA) to access  database and retrieve the information.<br />
Use RestControllers to expose the information retrieved from spring data repositories.<br />
Apply Spring Security In-Memory Authentication for allowing user with username: admin and password: 12345678 to login. All endpoints should be authorized.<br />
Bonus: add author (logged-in user) when creating a new blog post (don't forget the db)
<br /><br />
Frontend consists of HTML and Javascript<br />
Apply css styling<br />
To consume the endpoints above use asynchronous javascript<br />
Store everything in a git repo<br />
Functional requirements<br /><br />

Content of the blogpost can be html<br />
Form to create a new blogpost.<br />
Table to show all blogposts and their attribute values. Bonus: Include filters to search for blogposts by title or category<br />
The table mentioned above should have an actions columns, with buttons to edit and delete<br />
Edit button redirect to the view of editing a blogpost: form with fields filled with blogpost data<br />
Delete button used for deleting the entity. Before deleting, show a confirmation dialog.<br />

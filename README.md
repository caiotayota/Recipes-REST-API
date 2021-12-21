# Recipes REST-API

### <a href="https://hyperskill.org/projects/180" target="_blank">(a JetBrains Academy Project)</a>
### A multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.

--------------------------------------------------------------------------------------------------------------

### The service support the following endpoints:

`POST /api/recipe/new` receives a recipe as a JSON object and returns a JSON object with one id field;

`GET /api/recipe/{id}` returns a recipe with a specified id as a JSON object;

`DELETE /api/recipe/{id}` deletes a recipe with a specified id.

`PUT /api/recipe/{id}` receives a recipe as a JSON object and updates a recipe with a specified id. Also, update the date field too. The server return the `204 (No Content)` status code. If a recipe with a specified id does not exist, the server return `404 (Not found)`. The server respond with `400 (Bad Request)` if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields can't be blank, arrays should have at least one item);

`GET /api/recipe/search` takes one of the two mutually exclusive query parameters:

* `category` – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
* `name` – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).

If no recipes are found, the program return an empty JSON array. If 0 parameters were passed, or more than 1, the server return `400 (Bad Request)`. The same response follows if the specified parameters are not valid. If everything is correct, it `return 200 (Ok)`.

`POST /api/register` receives a JSON object with two fields: email (string), and password (string). If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with `200 (Ok)`. If a user is already in the database, respond with the `400 (Bad Request)` status code. Both fields are required and must be valid: email should contain @ and . symbols, password should contain at least 8 characters and shouldn't be blank. If the fields do not meet these restrictions, the service should respond with `400 (Bad Request)`. Is used BCryptPasswordEncoder encoder before storing a password in a database.


### The Spring Boot Security dependency is included and configured access to the endpoints:

All implemented endpoints (*except `/api/register`*) is available only to the registered and then authenticated and authorized via HTTP Basic auth users. Otherwise, the server respond with the `401 (Unauthorized)` status code.

Additional restrictions – only an author of a recipe can delete or update a recipe. If a user is not the author of a recipe, but they try to carry out the actions mentioned above, the service respond with the `403 (Forbidden)` status code.

For testing purposes, `POST/actuator/shutdown` is available without authentication.

--------------------------------------------------------------------------------------------------------------

### Examples:

**Example 1** - `POST /api/recipe/new` request without authentication:

```json
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

Status code: `401 (Unauthorized)`


**Example 2** - `POST /api/register` request without authentication:

```json
{
   "email": "Cook_Programmer@somewhere.com",
   "password": "RecipeInBinary"
}
```

Status code: `200 (Ok)`


***Example 3*** - `POST /api/recipe/new` request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary:

```json
{
   "name": "Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
````

Response:

```json
{
   "id": 1
}
```

***Example 4*** - `PUT /api/recipe/1` request with basic authentication; email (login): Cook_Programmer@somewhere.com, password: RecipeInBinary:

```json
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

Status code: `204 (No Content)`

***Example 5*** - `GET /api/recipe/1` request with basic authentication; email (login): Cook_Programmer@somewhere.com, password: RecipeInBinary:

Response:

```json
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2020-01-02T12:11:25.034734",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

***Example 6*** - `POST /api/register` request without authentication:

```json
{
   "email": "CamelCaseRecipe@somewhere.com",
   "password": "C00k1es."
}
```

Status code: `200 (Ok)`

Further response for the `GET /api/recipe/1` request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.

```json
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2020-01-02T12:11:25.034734",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

Further `PUT /api/recipe/1` request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.

```json
{
   "name": "Warming Ginger Tea",
   "category": "beverage",
   "description": "Ginger tea is a warming drink for cool weather, ...",
   "ingredients": ["1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"],
   "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"]
}
```

Status code: `403 (Forbidden)`

Further `DELETE /api/recipe/1` request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.

Status code: `403 (Forbidden)`

package petstore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet"; // Address for the Pet entity

    public String readJSON(String caminhoJSon) throws IOException {  
        return new String(Files.readAllBytes(Paths.get(caminhoJSon)));
    }

    @Test
    (priority = 1)
    public void postPet() throws IOException { // Adds a pet into the db based on the JSON file
        String jsonBody = readJSON("db/pet1.json");

        given()
            .contentType("application/json")
            .log().all()
            .body(jsonBody)
        .when()
            .post(uri)
        .then()
            .log().all()
            .statusCode(200)
            // Verifies that the content was propperly sent
            .body("name", is("doggy mc dogface"))
            .body("status", is("available"))
            .body("tags.name", contains("STA training")) // in a JSON file, a square bracketed attribute is actually a list, and 'contains' is used to search for its contents
            .body("category.name", is("dog")) // in a JSON file, a non-square bracketed attribute is an object, and 'is' can be used when searching for its contents
        ;
    }

    @Test
    (priority = 2)
    public void getPet_valid() throws IOException { // searches for a pet using the id from the JSON file
        String petId = "2508202120210825";

        given()
            .contentType("application/json")
            .log().all()   
        .when()
            .get(uri+"/"+petId)
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("doggy mc dogface"))
            .body("status", is("available"))
            .body("tags.name", contains("STA training")) // in a JSON file, a square bracketed attribute is actually a list, and 'contains' is used to search for its contents
            .body("category.name", is("dog")) // in a JSON file, a non-square bracketed attribute is an object, and 'is' can be used when searching for its contents
        ;
    }

    @Test
    (priority = 3)
    public void putPet() throws IOException { // Adds a pet into the db based on the JSON file
        String jsonBody = readJSON("db/pet2.json");

        given()
            .contentType("application/json")
            .log().all()
            .body(jsonBody)
        .when()
            .put(uri)
        .then()
            .log().all()
            .statusCode(200)
            // Verifies that the content was propperly sent
            .body("name", is("face mc doggerson"))
            .body("status", is("unavailable"))
        ;
    }

    @Test
    (priority = 2)
    public void deletePet() throws IOException { // searches for a pet using the id from the JSON file
        String petId = "2508202120210825";

        given()
            .contentType("application/json")
            .log().all()   
        .when()
            .delete(uri+"/"+petId)
        .then()
            .log().all()
            .statusCode(200)
        ;
    }


} 

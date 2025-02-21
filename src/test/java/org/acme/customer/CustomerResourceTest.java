package org.acme.customer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.customer.entity.CustomerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CustomerResourceTest {

    @BeforeEach
    @Transactional
    void setup() {
        // Limpiar la base de datos antes de cada prueba
        CustomerEntity.deleteAll();
    }

    @AfterEach
    @Transactional
    void tearDown() {
        // Limpiar la base de datos antes de cada prueba
        CustomerEntity.deleteAll();
    }

    @Test
    public void testCreateCustomer_Success() {
        String customerJson = """
            {
                "firstName": "John",
                "middleName": "Michael",
                "lastName": "Doe",
                "secondLastName": "Smith",
                "email": "john.doe@example.com",
                "address": "123 Main St",
                "phone": "123456789",
                "country": "US"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(customerJson)
        .when()
            .post("/api/customer")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("email", equalTo("john.doe@example.com"))
            .body("firstName", equalTo("John"));
    }

    @Test
    @Transactional
    public void testCreateCustomer_Fail_EmailAlreadyExists() {

        String customerJson = """
        {
            "firstName": "John",
            "middleName": "Michael",
            "lastName": "Doe",
            "secondLastName": "Smith",
            "email": "jane.doe@example.com",
            "address": "123 Main St",
            "phone": "123456789",
            "country": "US"
        }
        """;

        given()
        .contentType(ContentType.JSON)
            .body(customerJson)
        .when()
            .post("/api/customer");

        given()
            .contentType(ContentType.JSON)
            .body(customerJson)
        .when()
            .post("/api/customer")
        .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .body("error", containsString("El correo electrónico ya está registrado."));
    }

    @Test
    public void testGetAllCustomers_EmptyList() {
        given()
        .when()
            .get("/api/customer")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("$", hasSize(0));
    }

    @Test
    public void testGetCustomerById_Success() {
        // Insertar un cliente antes de buscarlo
        int customerId = createCustomer("John", "Michael", "Doe", "Smith", 
        "jane.doe@example.com", "123 Main St", "123456789", "US");

       // Realizar GET con el ID obtenido
       given()
       .when()
           .get("/api/customer/" + customerId)
       .then()
           .statusCode(200)
           .body("email", equalTo("jane.doe@example.com")) // Coincide con el cliente insertado
           .body("firstName", equalTo("John"));
    }

    @Test
    public void testGetCustomerById_NotFound() {
        given()
        .when()
            .get("/api/customer/999")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode()) // NoSuchElementException
            .body("error", containsString("No fue encontrado el cliente con el siguiente id"));
    }

    @Test
    public void testUpdateCustomer_Success() {
        // Insertar cliente antes de actualizar
        int customerId = createCustomer("John", "Michael", "Doe", "Smith", 
        "jane.doe@example.com", "123 Main St", "123456789", "US");

        String updateJson = """
            {
                "email": "bob.new@example.com",
                "address": "New Address 123",
                "phone": "111222333",
                "country": "US"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(updateJson)
        .when()
            .put("/api/customer/" + customerId)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("email", equalTo("bob.new@example.com"))
            .body("address", equalTo("New Address 123"));
    }

    @Test
    public void testDeleteCustomer_Success() {
        // Insertar cliente antes de eliminar
        int customerId = createCustomer("John", "Michael", "Doe", "Smith", 
        "jane.doe@example.com", "123 Main St", "123456789", "US");

        given()
        .when()
            .delete("/api/customer/" + customerId)
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void testDeleteCustomer_NotFound() {
        given()
        .when()
            .delete("/api/customer/999")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode()) // NoSuchElementException
            .body("error", containsString("No fue encontrado el cliente con el siguiente id"));
    }

    private int createCustomer(String firstName, String middleName, String lastName, String secondLastName, 
                           String email, String address, String phone, String country) {
    String customerJson = String.format("""
        {
            "firstName": "%s",
            "middleName": "%s",
            "lastName": "%s",
            "secondLastName": "%s",
            "email": "%s",
            "address": "%s",
            "phone": "%s",
            "country": "%s"
        }
        """, firstName, middleName, lastName, secondLastName, email, address, phone, country);

    return given()
        .contentType(ContentType.JSON)
        .body(customerJson)
    .when()
        .post("/api/customer")
    .then()
        .statusCode(200) // 201 Created
        .extract()
        .path("id");
}
}

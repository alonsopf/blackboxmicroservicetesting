package app.functions

import app.TestBase
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder

object CatsServiceFunctions {

    private val catsServiceSpec = RequestSpecBuilder()
        .setContentType("application/json;charset=UTF-8")
        .setAccept("application/json")
        .build()

    fun getFacts(): String {
        return RestAssured.given()
                .spec(catsServiceSpec).log().all()
                .get("https://${TestBase.catsServiceHost()}/facts")
                .then()
                .statusCode(200)
                .extract().response().asString()
    }

}
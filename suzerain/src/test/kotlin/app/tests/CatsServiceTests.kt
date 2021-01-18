package app.tests

import app.Integration
import app.functions.CatsServiceFunctions
import app.util.objectMapperFactory
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.restassured.path.json.JsonPath

class CatsServiceTests : StringSpec() {

    init {

        "get(/facts) Get fun cats facts" {

            val factsResponse = CatsServiceFunctions.getFacts()
            factsResponse shouldNotBe null

            print(factsResponse)

            //Here we use JsonPath in the test rather than the function - we didn't
            // cast to a friendly model because this is a Unknow service            //Indexing into a list here
            JsonPath.from(factsResponse).using(objectMapperFactory).getBoolean("[0].status.verified") shouldBe true

        }.config(tags = setOf(Integration))

    }

}
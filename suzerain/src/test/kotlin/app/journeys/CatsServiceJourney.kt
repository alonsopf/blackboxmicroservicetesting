package app.journeys

import app.Journey
import app.functions.CatsServiceFunctions
import app.util.objectMapperFactory
import io.kotlintest.matchers.gt
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.FeatureSpec
import io.restassured.path.json.JsonPath

class CatsServiceJourney : FeatureSpec() {
    init {
        feature("Cats service visit") {

            scenario("Should give us the cats fun facts we ask for") {

                val factsResponse = CatsServiceFunctions.getFacts()
                factsResponse shouldNotBe null
                JsonPath.from(factsResponse).using(objectMapperFactory).getBoolean("[0].status.verified") shouldBe true
                JsonPath.from(factsResponse).using(objectMapperFactory).getLong("[0].status.sentCount") shouldBe gt(0)

            }.config(tags = setOf(Journey))

        }
    }
}
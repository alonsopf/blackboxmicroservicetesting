package app.journeys

import app.Journey
import app.functions.CarServiceFunctions
import app.functions.FoodServiceFunctions
import app.functions.CatsServiceFunctions
import app.util.objectMapperFactory
import io.kotlintest.matchers.gt
import io.kotlintest.matchers.lt
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.FeatureSpec
import io.restassured.path.json.JsonPath

class EntireEscapadeJourney : FeatureSpec() {
    init {
        feature("Get in the car, prepare the car, and go get food") {

            scenario("Should get in the car & makeit ready, go to drivethrough, which should give us the food we ask for, like we asked for it, in under a minute") {

                val carId = CarServiceFunctions.putCar("AMC", "Gremlin")
                carId shouldBe gt(0)

                val ready = CarServiceFunctions.isCarReady(carId)
                ready shouldBe false

                CarServiceFunctions.putBelt(carId)
                CarServiceFunctions.putEngine(carId)
                CarServiceFunctions.putWheels(carId)

                val readyNow = CarServiceFunctions.isCarReady(carId)
                readyNow shouldBe true

                val orderResponse = FoodServiceFunctions.getOrder("onionsAndTomatoes")
                orderResponse shouldNotBe null

                JsonPath.from(orderResponse).using(objectMapperFactory).getString("[0].with") shouldBe "onionsAndTomatoes"

                val windowResponse = FoodServiceFunctions.getWindow()
                windowResponse shouldNotBe null

                JsonPath.from(windowResponse).using(objectMapperFactory).getBoolean("[0].burger_correct") shouldBe true
                JsonPath.from(windowResponse).using(objectMapperFactory).getLong("[0].time_taken") shouldBe lt(60000)

                val factsResponse = CatsServiceFunctions.getFacts()
                factsResponse shouldNotBe null
                JsonPath.from(factsResponse).using(objectMapperFactory).getBoolean("[0].status.verified") shouldBe true
                JsonPath.from(factsResponse).using(objectMapperFactory).getLong("[0].status.sentCount") shouldBe gt(0)

            }.config(tags = setOf(Journey))

        }
    }
}
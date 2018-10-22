package computerdatabase
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
class LoadTestingSimulation extends Simulation{
    val httpProtocol = http.baseUrl("http://localhost:8080")
    val scnCreateItems = scenario("CreateItems").repeat(100){
    exec(http("PostItems-API")
        .post("/items")
        .header("Content-Type","application/json")
        .body(StringBody("""{"item": {"id": 123, "timestamp": "2016-01-01T23:01:01.000Z"}}"""))
        .check(status.is(201)))
    .pause(1)
    .exec(http("GetItems-API")
        .get("/items")
        .check(status.is(200))
    )}
    setUp(scnCreateItems.inject(constantUsersPerSec(20) during(10 seconds)).protocols(httpProtocol))
}
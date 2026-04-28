package ing.neuland.food.resource;

import ing.neuland.food.model.Food;
import ing.neuland.food.model.FoodRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class FoodResourceTest
{
	@Inject
	FoodRepository foodRepository;

	@AfterEach
	@Transactional
	void tearDown()
	{
		foodRepository.deleteAll();
	}

	@Test
	void shouldReturnEmptyListWhenNoFoodsExist()
	{
		given()
		.when()
			.get("/food")
		.then()
			.statusCode(200)
			.body("$", empty());
	}

	@Test
	@TestTransaction
	void shouldCreateFood()
	{
		// given
		Food food = new Food();
		food.setName("Lachs");
		food.setDescription("Gegrillter Lachs mit Kräutern.");

		// when / then
		given()
			.contentType(JSON)
			.body(food)
		.when().post("/food")
			.then()
			.statusCode(200)
			.body("name", equalTo("Lachs"));
	}

	@Test
	@TestTransaction
	void shouldReturnFoodById()
	{
		// given
		Food food = new Food();
		food.setName("Forelle");
		food.setDescription("Gebratene Forelle mit Mandelbutter.");

		ValidatableResponse response = given()
			.contentType(JSON)
			.body(food)
			.when().post("/food")
			.then()
			.statusCode(200);

		long id = response
			.extract().jsonPath().getLong("id");

		// when / then
		given()
			.when().get("/food/" + id)
			.then()
			.statusCode(200)
			.body("name", equalTo("Forelle"));
	}

	@Test
	void shouldReturn404ForUnknownFood()
	{
		given()
			.when().get("/food/99999")
			.then()
			.statusCode(404);
	}

	@Test
	void shouldReturn400ForInvalidIdFormat()
	{
		given()
			.when().get("/food/not-a-number")
			.then()
			.statusCode(400);
	}
}
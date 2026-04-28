package ing.neuland.food.model;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@QuarkusTest
class FoodRepositoryTest
{
	@Inject
	FoodRepository foodRepository;

	@Test
	@TestTransaction
	void shouldPersistFood()
	{
		// given
		Food food = new Food();
		food.setName("Viktoriabarsch");
		food.setDescription("Zartes Filet aus dem Viktoriasee, " +
			"in Nussbutter goldbraun gebraten, mit einem Hauch Zitrone " +
			"und rosa Pfeffer. Dazu samtiges Petersilienwurzel-Püree.");

		// when
		foodRepository.persist(food);

		// then
		assertThat(foodRepository.listAll(), hasSize(1));
	}

	@Test
	@TestTransaction
	void shouldPersistMultipleFoods()
	{
		// given
		Food food1 = new Food();
		food1.setName("Lachs");
		food1.setDescription("Gegrillter Lachs mit Dillsauce.");

		Food food2 = new Food();
		food2.setName("Forelle");
		food2.setDescription("Gebratene Forelle mit Mandelbutter.");

		// when
		foodRepository.persist(food1);
		foodRepository.persist(food2);

		// then
		assertThat(foodRepository.listAll(), hasSize(2));
	}

	@Test
	@TestTransaction
	void shouldFindFoodById()
	{
		// given
		Food food = new Food();
		food.setName("Zander");
		food.setDescription("Zanderfilet auf Ratatouille.");
		foodRepository.persist(food);

		// when
		Food found = foodRepository.findById(food.id);

		// then
		assertThat(found, notNullValue());
		assertThat(found.getName(), is(equalTo("Zander")));
	}

	@Test
	@TestTransaction
	void shouldDeleteFood()
	{
		// given
		Food food = new Food();
		food.setName("Hecht");
		food.setDescription("Hechtklößchen in Weißweinsoße.");
		foodRepository.persist(food);

		// when
		foodRepository.delete(food);

		// then
		assertThat(foodRepository.listAll(), is(empty()));
	}
}
package ing.neuland.food.service;

import ing.neuland.food.model.Food;
import ing.neuland.food.model.FoodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FoodService
{
	@Inject
	FoodRepository foodRepository;

	public List<Food> getAllFoods()
	{
		return foodRepository.listAll();
	}

	public Food getFoodById(Long id)
	{
		return foodRepository.findById(id);
	}

	@Transactional
	public void saveFood(Food food)
	{
		foodRepository.persist(food);
	}

	@Transactional
	public void deleteFoodById(Long id)
	{
		foodRepository.deleteById(id);
	}
}

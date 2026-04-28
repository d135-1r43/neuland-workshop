package ing.neuland.food.resource;

import ing.neuland.food.model.Food;
import ing.neuland.food.service.FoodService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@ApplicationScoped
@Path("/food")
public class FoodResource
{
	@Inject
	FoodService foodService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Food> getAllFoods()
	{
		return foodService.getAllFoods();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponses({
		@APIResponse(responseCode = "200", description = "Food item found"),
		@APIResponse(responseCode = "400", description = "Invalid id format, must be a number"),
		@APIResponse(responseCode = "404", description = "Food item not found")
	})
	public Food getFoodById(@PathParam("id") String id)
	{
		long idAsLong;
		try
		{
			idAsLong = Long.parseLong(id);
		}
		catch (Exception e)
		{
			throw new BadRequestException("Invalid id format, must be a number: " + id);
		}

		Food foodById = foodService.getFoodById(idAsLong);
		if (foodById == null)
		{
			throw new NotFoundException("Food with id " + id + " not found.");
		}
		return foodById;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Food saveFood(Food food)
	{
		foodService.saveFood(food);
		return food;
	}

	@DELETE
	public void deleteFoodById(Long id)
	{
		foodService.deleteFoodById(id);
	}
}

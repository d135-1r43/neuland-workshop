package ing.neuland.food.model;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FoodRepository implements PanacheRepository<Food>
{
	// auto implemented
}

package ohm.softa.a10.kitchen;

import ohm.softa.a10.KitchenHatchConstants;
import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

import java.util.Deque;
import java.util.LinkedList;

public class KitchenHatchImpl implements KitchenHatch{

	private final int maxMeals;
	public LinkedList<Order> orders;
	private LinkedList<Dish> dishes;

	public KitchenHatchImpl(int maxMeals, Deque<Order> orders){
		this.maxMeals = maxMeals;
		this.orders = (LinkedList<Order>) orders;
		this.dishes = new LinkedList<>();
		for(int i = 0; i< KitchenHatchConstants.ORDER_COUNT; i++){
			orders.add(new Order("Bestellung N° " + i));
		}
	}
	@Override
	public int getMaxDishes() {
		return maxMeals;
	}

	@Override
	public Order dequeueOrder(long timeout) {
		return orders.remove();
	}

	@Override
	public int getOrderCount() {
		return orders.size();
	}

	@Override
	public Dish dequeueDish(long timeout) {
		return dishes.remove();
	}

	@Override
	public void enqueueDish(Dish m) {
		dishes.addLast(m);
	}

	@Override
	public int getDishesCount() {
		return dishes.size();
	}
}

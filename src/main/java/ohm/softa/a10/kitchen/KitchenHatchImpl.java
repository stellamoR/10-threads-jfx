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

		Order o = null;
		synchronized (orders){
			if(orders.size()>=  1){
				o = orders.pop();
			}
			return o;
		}

	}

	@Override
	public int getOrderCount() {
		return orders.size();
	}

	@Override
	public Dish dequeueDish(long timeout) {
		long currentTimeStamp = System.nanoTime();

		synchronized(dishes){
			while(dishes.size()== 0){

				try {
					dishes.wait(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if( timeout>0 && dishes.size() == 0 && System.nanoTime() -currentTimeStamp>= timeout){
					dishes.notifyAll();
					return null;
				}
			}
			Dish ret = dishes.pop();
			dishes.notifyAll();
			return ret;
		}
	}

	@Override
	public void enqueueDish(Dish m) {
		synchronized (dishes){
			while(dishes.size() >= maxMeals){

				try {
					dishes.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			dishes.push( m);
			dishes.notifyAll();

		}
	}

	@Override
	public int getDishesCount() {
		return dishes.size();
	}

	public Deque<Dish> getDishes(){
		return dishes;
	}
}

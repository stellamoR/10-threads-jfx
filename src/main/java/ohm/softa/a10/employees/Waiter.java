package ohm.softa.a10.employees;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.KitchenHatch;
import ohm.softa.a10.kitchen.KitchenHatchImpl;
import ohm.softa.a10.model.Dish;

import java.util.Random;

public class Waiter implements Runnable{
	private String name;
	private ProgressReporter progressReporter;
	private KitchenHatch kitchenHatch;

	public Waiter(String name, ProgressReporter progressReporter, KitchenHatch kitchenHatch) {
		this.name = name;
		this.progressReporter = progressReporter;
		this.kitchenHatch = kitchenHatch;
	}

	@Override
	public void run() {
		while(kitchenHatch.getDishesCount() > 0 && kitchenHatch.getOrderCount()>0){
			Dish d = kitchenHatch.dequeueDish();
			System.out.println(d.getCookingTime());
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			progressReporter.updateProgress();

		}
		progressReporter.notifyWaiterLeaving();
	}
}

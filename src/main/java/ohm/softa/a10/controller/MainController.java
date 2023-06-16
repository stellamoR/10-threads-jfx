package ohm.softa.a10.controller;

import ohm.softa.a10.KitchenHatchConstants;
import ohm.softa.a10.employees.Cook;
import ohm.softa.a10.employees.Waiter;
import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.KitchenHatch;
import ohm.softa.a10.kitchen.KitchenHatchImpl;
import ohm.softa.a10.model.Order;
import ohm.softa.a10.util.NameGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static ohm.softa.a10.KitchenHatchConstants.*;

public class MainController implements Initializable {

	private final ProgressReporter progressReporter;
	private final KitchenHatch kitchenHatch;
	private final NameGenerator nameGenerator;

	@FXML
	private ProgressIndicator waitersBusyIndicator;

	@FXML
	private ProgressIndicator cooksBusyIndicator;

	@FXML
	private ProgressBar kitchenHatchProgress;

	@FXML
	private ProgressBar orderQueueProgress;

	public MainController() {
		nameGenerator = new NameGenerator();

		//TODO assign an instance of your implementation of the KitchenHatch interface
		this.kitchenHatch = new KitchenHatchImpl(KITCHEN_HATCH_SIZE, new LinkedList<Order>());
		this.progressReporter = new ProgressReporter(kitchenHatch, COOKS_COUNT, WAITERS_COUNT, ORDER_COUNT, KITCHEN_HATCH_SIZE);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderQueueProgress.progressProperty().bindBidirectional(this.progressReporter.orderQueueProgressProperty());
		kitchenHatchProgress.progressProperty().bindBidirectional(this.progressReporter.kitchenHatchProgressProperty());
		waitersBusyIndicator.progressProperty().bindBidirectional(this.progressReporter.waitersBusyProperty());
		cooksBusyIndicator.progressProperty().bind(this.progressReporter.cooksBusyProperty());


		Thread temp;
		for(int i = 0; i< COOKS_COUNT; i++){
			temp = new Thread(new Waiter("Kellner N° " + i, progressReporter,kitchenHatch));
			System.out.println((temp.getName()));
			temp.start();
		}
		for(int i = 0; i< WAITERS_COUNT; i++){
			temp = new Thread(new Cook("Koch N° "+ i, kitchenHatch, progressReporter));
			temp.start();
		}



		}
}

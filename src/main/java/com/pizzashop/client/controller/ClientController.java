package com.pizzashop.client.controller;

import com.pizzashop.client.constants.ClientConstants;
import com.pizzashop.client.util.CountDownTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class ClientController {
	@Value("${countdown.time.seconds}")
	String howlong;

	@GetMapping("orderComplete")
	public void orderComplete() {
		System.out.println("Congratulations, your pizza is ready!");
		System.out.println("Thank you for your order!");
		System.out.println(ClientConstants.READY_TO_ORDER);
	}

	@GetMapping("orderStarted")
	public void orderStarted() {
		System.out.println("Your order is cooking!");
	}

	@GetMapping("startCountDown")
	public void startCountDown() {
		System.out.println("Your pizza will be ready in 30 seconds");
		CountDownTimer.startCountDown(howlong);
	}

}

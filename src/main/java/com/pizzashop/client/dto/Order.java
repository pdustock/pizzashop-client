package com.pizzashop.client.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class Order implements Serializable {
	private String orderId;
	private String callbackUrl;
	private List<PizzasWithAmount> pizzasWithAmounts;

	public String getOrderId() {
		return orderId;
	}

	public Order() {
	}
}

package com.pizzashop.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzashop.client.constants.ClientConstants;
import com.pizzashop.client.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
@Slf4j
public class PizzaRunner implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    String serverPort;

    @Override
    public void run(String... args) throws Exception {
        readyToOrder();
    }

    void readyToOrder() throws IOException {
        System.out.println(ClientConstants.READY_TO_ORDER);
        Scanner inputScanner = new Scanner(System.in);
        String answer = inputScanner.nextLine();
        if (answer.equals("y"))
            placeOrder(new ArrayList<PizzasWithAmount>(), new ArrayList<IngredientsWithAmount>());
        else
            readyToOrder();
    }

    void placeOrder(List<PizzasWithAmount> pizzasWithAmountList, List<IngredientsWithAmount> ingredientsWithAmountList) throws IOException {
        System.out.println(ClientConstants.INGREDIENTS_LIST);
        for (Ingredients n : Ingredients.values()) {
            System.out.print(n + ",");
        }

        System.out.println("\n" + ClientConstants.ENTER_INGREDIENTS_FROM_ABOVE);
        Scanner inputScanner = new Scanner(System.in);
        String inputIngredients = inputScanner.nextLine();

        System.out.println("\n" + ClientConstants.HOW_MANY_THIS_TOPPING);
        String howManyThisTopping = inputScanner.nextLine();

        IngredientsWithAmount ingredientsWithAmount = new IngredientsWithAmount(inputIngredients, howManyThisTopping);
        ingredientsWithAmountList.add(ingredientsWithAmount);


        System.out.println(ClientConstants.MORE_TOPPING);
        String answer = inputScanner.nextLine();
        if (answer.equals("y"))
            placeOrder(pizzasWithAmountList, ingredientsWithAmountList);
        else {
            System.out.println("\n" + ClientConstants.HOW_MANY_THIS_PIZZA);
            String howManyThisPizzas = inputScanner.nextLine();
            Pizza pizza = new Pizza(ingredientsWithAmountList);
            PizzasWithAmount pizzasWithAmount = new PizzasWithAmount(pizza, howManyThisPizzas);
            pizzasWithAmountList.add(pizzasWithAmount);
            moreOrderOrNot(pizzasWithAmountList);
        }
    }

    void moreOrderOrNot(List<PizzasWithAmount> pizzasWithAmountList) throws IOException {
        System.out.println("\n" + ClientConstants.MORE_PIZZAS);
        Scanner inputScanner = new Scanner(System.in);
        String orderMore = inputScanner.nextLine();
        if (orderMore.equals("y"))
            placeOrder(pizzasWithAmountList, new ArrayList<IngredientsWithAmount>());
        else {
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setCallbackUrl(serverPort);
            order.setPizzasWithAmounts(pizzasWithAmountList);
            sendOrderToPizzaShop(order);
            readyToOrder();
        }
    }

    void sendOrderToPizzaShop(Order order) {
        String url = ClientConstants.SERVER_PREFIX + ClientConstants.ORDER;
        HttpEntity<Order> entity = new HttpEntity<>(order);
        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(ClientConstants.ENTER_ORDER_AGAIN);
        }
    }

}

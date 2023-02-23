package com.rremiao.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rremiao.orderservice.client.ProductClient;
import com.rremiao.orderservice.client.UserClient;
import com.rremiao.orderservice.dto.ProductDTO;
import com.rremiao.orderservice.dto.PurchaseOrderRequestDTO;
import com.rremiao.orderservice.dto.PurchaseOrderResponseDTO;
import com.rremiao.orderservice.dto.UserDTO;
import com.rremiao.orderservice.service.OrderFulfillmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private UserClient userClient;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private OrderFulfillmentService orderFulfillmentService;

	@Test
	void contextLoads() {
		Flux<PurchaseOrderResponseDTO> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
													 .map(t -> buildDto(t.getT1(), t.getT2()))
													 .flatMap(dto -> this.orderFulfillmentService.processOrder(Mono.just(dto)))
													 .doOnNext(System.out::println);

		StepVerifier.create(dtoFlux)
					.expectNextCount(4)
					.verifyComplete();
	}

	private PurchaseOrderRequestDTO buildDto(UserDTO userDTO, ProductDTO productDTO) {
		PurchaseOrderRequestDTO dto = new PurchaseOrderRequestDTO();

		dto.setUserId(userDTO.getId());
		dto.setProductId(productDTO.getId());

		return dto;
	}

}

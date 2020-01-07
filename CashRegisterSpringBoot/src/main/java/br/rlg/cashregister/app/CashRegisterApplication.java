package br.rlg.cashregister.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages="br.rlg.cashregister.controller.model")
@ComponentScan(basePackages={"br.rlg.cashregister"})
public class CashRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashRegisterApplication.class, args);
	}

}

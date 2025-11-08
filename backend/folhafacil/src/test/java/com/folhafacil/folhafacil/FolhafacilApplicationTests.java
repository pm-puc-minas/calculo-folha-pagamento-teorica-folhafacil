package com.folhafacil.folhafacil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.folhafacil.folhafacil.controller.BeneficioController;
import com.folhafacil.folhafacil.controller.FolhaPagamentoController;
import com.folhafacil.folhafacil.controller.FuncionarioController;
import com.folhafacil.folhafacil.controller.HoraExtraController;
import com.folhafacil.folhafacil.controller.LogController;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

@SpringBootTest
class FolhafacilApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads(){
		asserNotNull(context, "O ApplicationContext tem que ser carregado");
	}

	@Test
	void applicationBeanExists(){
		FolhafacilApplication appBean = context.getBean(FolhafacilApplication.class);
		assertNotNull(appBean, "O bean FolhafacilApplication deve estar presente no contexto");
	}

	@Test
	void controllerAreLoaded(){
		//verifica controllers se estao em bean
		BeneficioController beneficioController = context.getBean(BeneficioController.class);
		assertNotNull(beneficioController, "BeneficioController deve ser um bean");

		FolhaPagamentoController folhaPagamentoController = context.getBean(FolhaPagamentoController.class);
		assertNotNull(folhaPagamentoController, "FolhaPagamentoController deve ser um bean");

		FuncionarioController funcionarioController = context.getBean(FuncionarioController.class);
		assertNotNull(funcionarioController, "FuncionarioController deve ser um bean");

		HoraExtraController horaExtraController = context.getBean(HoraExtraController.class);
		assertNotNull(horaExtraController, "HoraExtraController deve ser um bean");

		LogController logController = context.getBean(LogController.class);
		assertNotNull(logController, "LogController deve ser um bean");

	}

}

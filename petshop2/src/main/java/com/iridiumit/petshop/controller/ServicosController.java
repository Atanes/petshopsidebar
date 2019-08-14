package com.iridiumit.petshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iridiumit.petshop.model.Servico;
import com.iridiumit.petshop.repository.Servicos;

@Controller
@RequestMapping("/atendimento/servicos")
public class ServicosController {

	@Autowired
	private Servicos servicos;

	@GetMapping
	public ModelAndView listar() {

		ModelAndView modelAndView = new ModelAndView("atendimento/servico/lista-servicos");

		modelAndView.addObject("servicos", servicos.findAllByOrderByDescricao());
		return modelAndView;
	}

	@GetMapping("/novo")
	public ModelAndView incluir(Servico servico) {
		ModelAndView modelAndView = new ModelAndView("atendimento/servico/cadastro-servicos");

		modelAndView.addObject(servico);

		return modelAndView;
	}

}

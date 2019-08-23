package com.iridiumit.petshop.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.petshop.model.Animal;
import com.iridiumit.petshop.model.Consulta;
import com.iridiumit.petshop.model.StatusConsulta;
import com.iridiumit.petshop.model.TipoConsulta;
import com.iridiumit.petshop.repository.Animais;
import com.iridiumit.petshop.repository.Consultas;
import com.iridiumit.petshop.repository.Permissoes;
import com.iridiumit.petshop.repository.Usuarios;

@Controller
@RequestMapping("/atendimento/consultas")
public class ConsultaController {

	@Autowired
	private Consultas consultas;
	
	@Autowired
	private Animais animais;

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Permissoes permissoes;

	@GetMapping
	public ModelAndView listar() {

		ModelAndView modelAndView = new ModelAndView("atendimento/consulta/lista-consultas");

		modelAndView.addObject("consultas", consultas.findByOrderByDataAtendimento());
		return modelAndView;
	}

	@DeleteMapping("/excluir/{codigo}")
	public String remover(@PathVariable Long codigo, RedirectAttributes attributes) {

		consultas.delete(codigo);

		attributes.addFlashAttribute("mensagem", "Consulta excluida com sucesso!!");

		return "redirect:/atendimento/consultas";
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		Consulta c = consultas.getOne(codigo);
		
		return incluir(c, c.getAnimal().getId());

	}

	@GetMapping("/novo/{id}")
	public ModelAndView incluir(Consulta consulta, @PathVariable("id") Long idAnimal) {
		ModelAndView modelAndView = new ModelAndView("atendimento/consulta/cadastro-consulta");

		Animal a = animais.getOne(idAnimal);
		
		consulta.setAnimal(a);

		modelAndView.addObject(consulta);
		modelAndView.addObject("usuarios", usuarios.findByPermissoes(permissoes.findByNome("ROLE_PS_VETERINARIO")));

		return modelAndView;
	}
	
	@PostMapping("/agendar")
	public ModelAndView agendar(Consulta consulta, RedirectAttributes attributes) {
		
		Date dataRegistro = new Date();
		
		consulta.setDataRegistro(dataRegistro);
		consulta.setDiagnostico("-");
		consulta.setSintomas("-");
		
		consultas.save(consulta);

		attributes.addFlashAttribute("mensagem", "Consulta atualizada com sucesso!!");

		return new ModelAndView("redirect:/atendimento/consultas");
	}
	
	@GetMapping("/atender/{codigo}")
	public ModelAndView atender(@PathVariable Long codigo) {
		
		ModelAndView modelAndView = new ModelAndView("atendimento/consulta/atender-consulta");
		
		Consulta c = consultas.getOne(codigo);
		
		Date dataAtendimento = new Date();
		
		c.setDataAtendimento(dataAtendimento);
		c.setStatus("ATENDIMENTO");
		
		consultas.saveAndFlush(c);
		
		modelAndView.addObject(c);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Consulta consulta, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return incluir(consulta, consulta.getAnimal().getId());
		}
		
		consulta.setStatus("FINALIZADA");

		consultas.save(consulta);

		attributes.addFlashAttribute("mensagem", "Consulta finalizada com sucesso!!");

		return new ModelAndView("redirect:/atendimento/consultas");
	}
	
	@ModelAttribute("StatusConsulta")
	public List<StatusConsulta> StatusConsulta(){
		return Arrays.asList(StatusConsulta.values());
	}
	
	@ModelAttribute("TipoConsulta")
	public List<TipoConsulta> TiposConsulta(){
		return Arrays.asList(TipoConsulta.values());
	}

}

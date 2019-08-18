package com.iridiumit.petshop.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.petshop.model.Animal;
import com.iridiumit.petshop.model.Cliente;
import com.iridiumit.petshop.model.Raca;
import com.iridiumit.petshop.repository.Animais;
import com.iridiumit.petshop.repository.Clientes;
import com.iridiumit.petshop.repository.Racas;
import com.iridiumit.petshop.repository.filtros.AnimalFiltro;

@Controller
@RequestMapping("/clientes/animais")
public class AnimalController {

	@Autowired
	private Clientes clientes;

	@Autowired
	private Animais animais;

	@Autowired
	private Racas racas;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") AnimalFiltro filtro) {

		String nome = filtro.getNome() == null ? "%" : filtro.getNome();

		ModelAndView modelAndView = new ModelAndView("animais/lista-animais");

		modelAndView.addObject("animais", animais.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = animais.getOne(id).getCliente();

		animais.delete(id);

		attributes.addFlashAttribute("mensagem", "Animal excluido com sucesso!!");

		return "redirect:/atendimento/clientes/selecao/" + c.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(animais.getOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Animal animal) {
		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal");

		modelAndView.addObject(animal);

		return modelAndView;
	}

	@GetMapping("/incluirAnimal/{id}")
	public ModelAndView incluirAnimal(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal");

		Cliente c = clientes.getOne(id);

		Animal a = new Animal();

		a.setCliente(c);

		modelAndView.addObject(a);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Animal animal, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(animal);
		}

		animal.setData_cadastro(new Date());

		animais.save(animal);

		attributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/selecao/" + animal.getCliente().getId());
	}

	// Método que recebe uma especie e devolve as raças relecionadas para o select
	@RequestMapping(value = "/listaRacas", method = RequestMethod.GET)
	public @ResponseBody List<Raca> adicionados(@RequestParam String especie, Model model) {

		List<Raca> r = racas.findByEspecieIgnoreCaseOrderByNome(especie);

		model.addAttribute("adicionados", r);
		return r;

	}

}

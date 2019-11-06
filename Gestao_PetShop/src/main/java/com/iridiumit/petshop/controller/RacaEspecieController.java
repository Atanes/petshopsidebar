package com.iridiumit.petshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import com.iridiumit.petshop.model.Raca;
import com.iridiumit.petshop.repository.Racas;
import com.iridiumit.petshop.repository.filtros.FiltroGeral;
import com.iridiumit.petshop.utils.PageUtils;

@Controller
@RequestMapping("/raca_especie")
public class RacaEspecieController {

	private static final String ORDERBYRACA = "nome";
	private static final int RECORDSPERPAGE = 10;

	@Autowired
	private Racas racas;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro,
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYRACA, direction = Direction.ASC) Pageable pageable,
			HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("raca/lista-raca_especie");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("racas", racas.findAll(pageable));
		} else {
			modelAndView.addObject("racas", racas.findByNomeContainingIgnoreCase(filtro.getTextoFiltro(), pageable));
		}
		
		PageUtils pageUtils = new PageUtils(httpServletRequest, pageable);

		modelAndView.addObject("controlePagina", pageUtils);

		return modelAndView;
	}

	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		try {
			racas.delete(id);
		} catch (DataIntegrityViolationException e) {

			attributes.addFlashAttribute("mensagem",
					"Raça não pode ser excluida porque está relacionada a algum animal!");

			return "redirect:/raca_especie";
		}

		attributes.addFlashAttribute("mensagem", "Raça excluida com sucesso!!");

		return "redirect:/raca_especie";
	}

	@GetMapping("/novo")
	public ModelAndView novo(Raca raca) {
		return new ModelAndView("raca/cadastro-raca_especie");
	}

	@PostMapping("/salvar")
	public ModelAndView cadastrar(@Valid Raca raca, BindingResult result, RedirectAttributes attributes) {

		if (raca.getId() == null) {
			Raca r = racas.findByNomeIgnoreCase(raca.getNome());
			if (r != null) {
				result.rejectValue("nome", "nome.existente");
			}
		}

		if (result.hasErrors()) {
			return novo(raca);
		}

		racas.save(raca);

		attributes.addFlashAttribute("mensagem", "Raça salva com sucesso!!");
		return new ModelAndView("redirect:/raca_especie/novo");
	}

	@RequestMapping(value = "/incluirRaca", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> salvar(@RequestParam("raca") String raca,
			@RequestParam("especie") String especie) {

		if (raca.isEmpty() || especie.isEmpty()) {
			return ResponseEntity.badRequest().body("Nome da Raça deve ser preenchido!!");
		}

		Raca r = new Raca();

		if (racas.findByNomeIgnoreCase(raca) == null) {
			r.setEspecie(especie);
			r.setNome(raca);

			racas.save(r);
		} else {
			return ResponseEntity.badRequest().body("Já existe uma raça com esse nome cadastrada na base de dados!!");
		}

		return ResponseEntity.ok(r);
	}

	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("raca/cadastro-raca_especie");

		Raca r = racas.findOne(id);

		modelAndView.addObject(r);

		return modelAndView;
	}

	public String montaURI(HttpServletRequest httpServletRequest) {

		System.out.println(httpServletRequest.getRequestURL().toString() + "?" + httpServletRequest.getQueryString());

		String uri = httpServletRequest.getRequestURI();

		if (httpServletRequest.getQueryString() != null) {

			uri += "?" + httpServletRequest.getQueryString() + "&";

		} else {
			return uri + "?";
		}

		return uri;
	}

}

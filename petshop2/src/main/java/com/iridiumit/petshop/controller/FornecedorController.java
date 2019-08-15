package com.iridiumit.petshop.controller;

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

import com.iridiumit.petshop.model.Fornecedor;
import com.iridiumit.petshop.repository.Fornecedores;
import com.iridiumit.petshop.repository.Produtos;
import com.iridiumit.petshop.repository.filtros.FornecedorFiltro;
import com.iridiumit.petshop.service.FornecedorService;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Autowired
	private Produtos produtos;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FornecedorFiltro filtro) {
		
		ModelAndView modelAndView = new ModelAndView("fornecedores/lista-fornecedores");
		
		List<Fornecedor> fornecedores = fornecedorService.filtrar(filtro);

		modelAndView.addObject("fornecedores", fornecedores);
		
		return modelAndView;
	}
	
	@GetMapping("/{id}")
	public ModelAndView fornecedorProduto(@PathVariable Long id) {
		
		ModelAndView modelAndView = new ModelAndView("fornecedores/lista-fornecedor-e-produtos");
		
		Fornecedor f = fornecedores.getOne(id);
		
		modelAndView.addObject(f);
		
		modelAndView.addObject("produtos", produtos.findByFornecedor(f));
		
		modelAndView.addObject("mensagem", "Fornecedor salvo com sucesso!");
		
		return modelAndView;
	}
	
	@DeleteMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		fornecedorService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Fornecedor excluido com sucesso!!");
		
		return "redirect:/fornecedores";
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(fornecedorService.localizar(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Fornecedor fornecedor) {
		ModelAndView modelAndView = new ModelAndView("fornecedores/cadastro-fornecedor");

		modelAndView.addObject(fornecedor);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult result, RedirectAttributes attributes) {
		
		if (fornecedor.getId() == null) {
			Fornecedor f = fornecedorService.localizarCnpj(fornecedor.getCnpj());
			if(f != null){
				result.rejectValue("cnpj", "cnpj.existente");
			}
        }
		
		if (result.hasErrors()) {
			return novo(fornecedor);
		}

		fornecedorService.salvar(fornecedor);

		attributes.addFlashAttribute("mensagem", "Fornecedor salvo com sucesso!!");

		return new ModelAndView("redirect:/fornecedores/" + fornecedor.getId());
	}
	
}

package com.iridiumit.petshop.controller;

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
import com.iridiumit.petshop.model.Produto;
import com.iridiumit.petshop.repository.Fornecedores;
import com.iridiumit.petshop.repository.Produtos;
import com.iridiumit.petshop.repository.filtros.FiltroGeral;


@Controller
@RequestMapping("/fornecedores/produtos")
public class ProdutoController {
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Autowired
	private Produtos produtos;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro) {
		
ModelAndView modelAndView = new ModelAndView("produtos/lista-produtos");
		
		if(filtro.getTextoFiltro() == null) {
			modelAndView.addObject("produtos", produtos.findAll());
		}else {
			modelAndView.addObject("produtos", produtos.findByDescricaoContainingIgnoreCase(filtro.getTextoFiltro()));
		}
		
		return modelAndView;
	}
	
	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorFornecedor(@PathVariable Long id, @ModelAttribute("filtro") FiltroGeral filtro) {

		Fornecedor f = fornecedores.getOne(id);

		ModelAndView modelAndView = new ModelAndView("fornecedores/lista-fornecedor-e-produtos");

		modelAndView.addObject(f);
		
		if(filtro.getTextoFiltro() == null) {
			modelAndView.addObject("produtos", produtos.findByFornecedor(f));
		}else {
			modelAndView.addObject("produtos", produtos.findByFornecedorAndDescricaoContainingIgnoreCase(f, filtro.getTextoFiltro()));
		}

		return modelAndView;

	}
	 

	@DeleteMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Fornecedor f = produtos.getOne(id).getFornecedor();

		produtos.delete(id);

		attributes.addFlashAttribute("mensagem", "Produto excluido com sucesso!!");

		return "redirect:/fornecedores/produtos/selecao/" + f.getId();
	}

	
	  @GetMapping("/{id}")
	  public ModelAndView editar(@PathVariable Long id) {
	  
		  return novo(produtos.getOne(id)); 
	  }
	  
	  @GetMapping("/novo")
	  public ModelAndView novo(Produto produto) {
		  
		  ModelAndView  modelAndView = new ModelAndView("produtos/cadastro-produto");
	  
		  modelAndView.addObject(produto);
	  
		  return modelAndView; 
	  }
	 
	
	@GetMapping("/incluirProduto/{id}")
	public ModelAndView incluirProduto(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("produtos/cadastro-produto");
		
		Fornecedor f = fornecedores.getOne(id);
		
		Produto p = new Produto();
		
		p.setFornecedor(f);
		
		modelAndView.addObject(p);

		return modelAndView;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(produto);
		}

		produtos.save(produto);
		
		Fornecedor f = produto.getFornecedor();

		attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!!");

		return new ModelAndView("redirect:/fornecedores/produtos/selecao/" + f.getId());
	}

}

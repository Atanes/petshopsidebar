package com.iridiumit.petshop.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.petshop.model.Endereco;
import com.iridiumit.petshop.model.Usuario;
import com.iridiumit.petshop.relatorios.UsuarioREL;
import com.iridiumit.petshop.repository.Enderecos;
import com.iridiumit.petshop.repository.filtros.UsuarioFiltro;
import com.iridiumit.petshop.service.UsuarioService;
import com.iridiumit.petshop.utils.PageUtils;

@Controller
@RequestMapping("/administracao/usuarios")
public class UsuarioController {
	
	private static final String ORDERBYUSUARIO = "nome";
	private static final int RECORDSPERPAGE = 5;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Enderecos enderecos;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") UsuarioFiltro filtro, 
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYUSUARIO, direction = Direction.ASC) Pageable pageable
			, HttpServletRequest httpServletRequest) {
		
		ModelAndView modelAndView = new ModelAndView("administracao/usuario/lista-usuarios");
		
		if(filtro.getNome() == null) {
			modelAndView.addObject("usuarios", usuarioService.listarTodos(pageable));
		}else {
			modelAndView.addObject("usuarios", usuarioService.filtrar(filtro.getNome(), pageable));
		}
		
		PageUtils pageUtils = new PageUtils(httpServletRequest, pageable);

		modelAndView.addObject("controlePagina", pageUtils);
		
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		usuarioService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Usuário inativado com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}
	
	@GetMapping("/inativar/{id}")
	public String inativar(@PathVariable Long id, RedirectAttributes attributes) {
		
		Usuario u = usuarioService.localizar(id);
		
		u.setAtivo(false);
		
		usuarioService.salvar(u);

		attributes.addFlashAttribute("mensagem", "Usuário inativado com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}
	
	@GetMapping("/ativar/{id}")
	public String ativar(@PathVariable Long id, RedirectAttributes attributes) {
		
		Usuario u = usuarioService.localizar(id);
		
		u.setAtivo(true);
		
		usuarioService.salvar(u);

		attributes.addFlashAttribute("mensagem", "Usuário re-ativado com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return editar(usuarioService.localizar(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("administracao/usuario/cadastro-usuario");
		
		modelAndView.addObject(usuario);
		
		modelAndView.addObject("permissoes", usuarioService.permissoes());

		return modelAndView;
	}
	
	@GetMapping("/editar")
	public ModelAndView editar(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("administracao/usuario/editar-usuario");

		modelAndView.addObject(usuario);
		
		modelAndView.addObject("permissoes", usuarioService.permissoes());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
	
		Usuario u = usuarioService.localizarCPF(usuario.getCpf());
		Endereco e = usuario.getEndereco();
		
		if (u != null) {
			result.rejectValue("cpf", "usuario.cpf.existente");
        }
		
		if (result.hasErrors()) {
			usuario.setSenha(null);
            return novo(usuario);
        } else {
        	enderecos.save(e);
        	usuario.setEndereco(e);
        
        	usuarioService.incluir(usuario);
        	attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!!");
        }
		
		return new ModelAndView("redirect:/administracao/usuarios/novo");
	}
	
	@PostMapping("/atualizar")
	public ModelAndView atualizar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		
		Endereco e = enderecos.findOne(usuario.getEndereco().getId());
		
		enderecos.save(e);

		if (result.hasErrors()) {
            return editar(usuario);
        } else {
        	usuarioService.atualizar(usuario);
        	attributes.addFlashAttribute("mensagem", "Usuario atualizado com sucesso!!");
        }
		
		return new ModelAndView("redirect:/administracao/usuarios");
			
	}
	
	@GetMapping(value = "/rel-usuarios", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelUsuarios() throws IOException {

		UsuarioREL relatorio = new UsuarioREL();
		try {
			relatorio.imprimir(usuarioService.listarTodos());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		InputStream in = this.getClass().getResourceAsStream("/relatorios/Relatorio_de_Usuarios.pdf");
		return IOUtils.toByteArray(in);
	}

}
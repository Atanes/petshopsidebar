package com.iridiumit.petshop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.petshop.model.Animal;
import com.iridiumit.petshop.model.Cliente;
import com.iridiumit.petshop.model.Raca;
import com.iridiumit.petshop.repository.Animais;
import com.iridiumit.petshop.repository.Clientes;
import com.iridiumit.petshop.repository.Racas;
import com.iridiumit.petshop.repository.filtros.FiltroGeral;
import com.iridiumit.petshop.service.FotoService;
import com.iridiumit.petshop.utils.PageUtils;

@Controller
@RequestMapping("/clientes/animais")
public class AnimalController {
	
	private static final String ORDERBYANIMAL = "nome";
	private static final int RECORDSPERPAGE = 5;
	
	//private Logger logger = LoggerFactory.getLogger(AmazonClient.class);
	
	private Logger logger = LoggerFactory.getLogger(FotoService.class);

	@Autowired
	private Clientes clientes;

	@Autowired
	private Animais animais;

	@Autowired
	private Racas racas;
	
	@Autowired
	private FotoService fotoService;

	/*
	 * @Autowired private AmazonClient amazonClient;
	 */

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro, 
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYANIMAL, direction = Direction.ASC) Pageable pageable
			, HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("animais/lista-animais");


		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("animais", animais.findAll(pageable));
		} else {
			modelAndView.addObject("animais", animais.findByNomeContainingIgnoreCase(filtro.getTextoFiltro(), pageable));
		}
		
		PageUtils pageUtils = new PageUtils(httpServletRequest, pageable);

		modelAndView.addObject("controlePagina", pageUtils);
		
		return modelAndView;
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = animais.findOne(id).getCliente();
		
		Animal a = animais.getOne(id);

		animais.delete(id);
		
		//System.out.println(amazonClient.deleteFileFromS3Bucket(a.getFoto()));
		logger.info(fotoService.removerFoto(a.getFoto()));

		attributes.addFlashAttribute("mensagem", "Animal excluido com sucesso!!");

		return "redirect:/atendimento/clientes/selecao/" + c.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(animais.findOne(id));
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

		Cliente c = clientes.findOne(id);

		Animal a = new Animal();

		System.out.println(a.getFoto());

		a.setCliente(c);

		modelAndView.addObject(a);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@RequestParam("file") MultipartFile file, @Valid Animal animal, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(animal);
		}

		if (!file.isEmpty()) {
			
			if(!animal.getFoto().isEmpty()) {
				//logger.info(amazonClient.deleteFileFromS3Bucket(animal.getFoto()));
				logger.info(fotoService.removerFoto(animal.getFoto()));
			}
			
			String arquivoFoto = fotoService.doUpload(file, animal);
			// String arquivoFoto = amazonClient.uploadFile(file, animal);

			if (arquivoFoto.equals("erro")) {
				attributes.addFlashAttribute("mensagem", "Problemas para salvar a foto do animal!!");
				return novo(animal);
			} else {
				animal.setFoto(arquivoFoto);
			}
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
		
		@GetMapping("/fotos/{nome:.*}")
		public @ResponseBody byte[] recuperarFoto(@PathVariable String nome) throws IOException {

			logger.info("Foto retornada:" + nome);
			
			return fotoService.recuperarFoto(nome);
		}
	

}

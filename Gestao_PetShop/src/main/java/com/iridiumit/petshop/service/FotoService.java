package com.iridiumit.petshop.service;

import static java.nio.file.FileSystems.getDefault;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iridiumit.petshop.model.Animal;

@Service
public class FotoService {

	private Path local;
	private Path localTemporario;	

	//Diretório para armazenar as fotos na máquina local
	private Path uploadRootPath = getDefault().getPath(System.getenv("USERPROFILE"), "//animaisfotos");
	
	public FotoService() {
	}

	private void criarPastas() {

		Map<String, String> map = System.getenv();
		map.entrySet().forEach(System.out::println);

		try {
			this.local = getDefault().getPath(System.getenv("USERPROFILE"), "//animaisfotos");
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);

			System.out.println("Pastas criadas com sucesso!!" + this.local);
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	public String doUpload(MultipartFile file, Animal animal) {
		
		if(local == null) {
			System.out.println("Variável LOCAL vazia!");
			criarPastas();
		}else {
			System.out.println("Variável LOCAL: " + local.toString());
		}
		
		File uploadRootDir = new File(uploadRootPath.toString());
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		String nomeArquivo = generateFileName(animal);
		
		String extensao = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), 
				file.getOriginalFilename().length());
		
		nomeArquivo += extensao;
		
		System.out.println("Nome do arquivo: " + nomeArquivo);

		// Client File Content Type
		String contentType = file.getContentType();
		System.out.println("Tipo do arquivo: " + contentType);

		if (nomeArquivo != null && nomeArquivo.length() > 0) {
			try {
				// Create the file at server
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + nomeArquivo);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());
				stream.close();

				System.out.println("Arquivo gravado: " + serverFile);
				
			} catch (Exception e) {
				System.out.println("Erro ao gravar o arquivo: " + nomeArquivo);
				return "erro";
			}
		}

		return nomeArquivo;
	}

	public byte[] recuperarFoto(String nomeFoto) {
		try {
			return Files.readAllBytes(this.uploadRootPath.resolve(nomeFoto));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String removerFoto(String nomeArquivo) {
		boolean excluido = new File(uploadRootPath + File.separator + nomeArquivo).delete();
		if(excluido) {
			return "Arquivo excluido com sucesso!!";
		}
		return "Arquivo não foi encontrado ou não foi excluido com sucesso!!";
	}
	
	private String generateFileName(Animal animal) {
		
		String nome = animal.getNome().replace(" ", "_");
		
		return animal.getCliente().getId() + "-" + nome + "-" + new Date().getTime();

	}

}

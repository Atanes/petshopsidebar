package com.iridiumit.petshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iridiumit.petshop.model.Permissao;
import com.iridiumit.petshop.model.Usuario;
import com.iridiumit.petshop.repository.Permissoes;
import com.iridiumit.petshop.repository.Usuarios;
import com.iridiumit.petshop.repository.filtros.UsuarioFiltro;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Permissoes permissoes;
	
	public List<Usuario> listarTodos() {
		return usuarios.findAll();
	}
	
	public List<Usuario> filtrar(UsuarioFiltro filtro) {
		
		if(filtro.getNome() == null) {
			return usuarios.findAll();
		}else {
			return usuarios.findByNomeContainingIgnoreCase(filtro.getNome());
		}

	}
	
	public void excluir(Long codigo) {
		Usuario u = usuarios.getOne(codigo);
		u.setAtivo(false);
		usuarios.save(u);
	}
	
	public Usuario localizar(Long id){
		return usuarios.getOne(id);
	}
	
	public Usuario localizarCPF(String cpf){
		return usuarios.findByCpf(cpf);
	}
	
	public Usuario localizarLogin(String login){
		return usuarios.findByLogin(login);
	}
	
	public void incluir(Usuario usuario){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hasSenha = passwordEncoder.encode(usuario.getSenha());
        
        usuario.setSenha(hasSenha);
        usuario.setAtivo(true);
        
        usuarios.save(usuario);
    }
	
	public void salvar(Usuario usuario) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hasSenha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(hasSenha);
        
        usuarios.save(usuario);
		
	}
	
	public void atualizar(Usuario usuario) {
		
        usuarios.save(usuario);
	}
	
	public List<Permissao> permissoes(){
		return permissoes.findAll();
	}

	@Override
    public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException {
        Usuario u = usuarios.findByLogin(login);

     if(u == null){
         throw new UsernameNotFoundException("O " + login + " não foi encontrado!");
     }

     return u;
    }

}

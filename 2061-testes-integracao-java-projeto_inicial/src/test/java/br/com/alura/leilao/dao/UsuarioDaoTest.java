package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {
	

	private UsuarioDao dao;
	private EntityManager em;
	
	private Usuario criarUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		em.persist(usuario);
		return usuario;
	}
	
	@BeforeEach
	public void beforeEach() {
		EntityManager em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	

	@Test
	void testBuscaDeUsuariCadastrado() {
		Usuario usuario = criarUsuario();
		Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(encontrado);
	}
	
	@Test
	void testBuscaDeUsuariNaoCadastrado() {
		criarUsuario();		
		Assert.assertThrows(NoResultException.class,
				()-> this.dao.buscarPorUsername("beltrano"));
	}
    
	@Test
	void deveriaRemoverUsuario() {
		Usuario usuario = criarUsuario();
		dao.deletar(usuario);
		Assert.assertThrows(NoResultException.class,
				()-> this.dao.buscarPorUsername(usuario.getNome()));
	}
}

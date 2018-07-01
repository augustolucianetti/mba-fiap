package br.com.fiap.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Escola;

public class EscolaHelper {
	private EntityManager em;

	public EscolaHelper(EntityManager em) {
		this.em = em;
	}

	public String salvar(Escola escola) {
		try {
			em.getTransaction().begin();
			em.persist(escola);
			em.getTransaction().commit();
			return "Escola incluída com sucesso!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String adicionarCurso(int idEscola, Curso curso) {
		try {
			Escola escola = em.find(Escola.class, idEscola);
			curso.setEscola(escola);
			escola.getCursos().add(curso);
			em.getTransaction().begin();
			em.persist(escola);
			em.getTransaction().commit();
			return "Escola atualizada com sucesso!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public List<Escola> listarEscolas() {
		TypedQuery<Escola> query = em.createQuery("Select e from Escola e", Escola.class);
		return query.getResultList();
	}

	public List<Curso> listarCursos(int idEscola) {
		TypedQuery<Curso> query = em.createQuery("Select c from Curso c Where c.escola.id = :idEscola",
				Curso.class);
		query.setParameter("idEscola", idEscola);
		return query.getResultList();
	}
	
	public Curso buscarCursoPorId(int idCurso) {
		TypedQuery<Curso> query = em.createQuery("Select c from Curso c where c.id = :id", Curso.class);
		query.setParameter("id", idCurso);
		return query.getSingleResult();
	}
	
	public String adicionarAluno(Aluno aluno) {
		try {
			em.getTransaction().begin();
			em.persist(aluno);
			em.getTransaction().commit();
			return "Aluno incluído com sucesso!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public Aluno buscarAlunoPorId(int idAluno) {
		
			TypedQuery<Aluno> query = em.createQuery("Select a from Aluno a where a.rm = :rm", Aluno.class);
			query.setParameter("rm", idAluno);
			return query.getSingleResult();
	}
	
	public List<Aluno> listarAlunos() {
		
		TypedQuery<Aluno> query = em.createQuery("Select a from Aluno a", Aluno.class);
		return query.getResultList();
	}
}

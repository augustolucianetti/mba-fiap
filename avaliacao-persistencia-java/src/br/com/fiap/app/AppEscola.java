package br.com.fiap.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.dao.CursoAlunoDao;
import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Escola;
import br.com.fiap.helper.EscolaHelper;
import br.com.fiap.viewmodel.CursoAlunoViewModel;

public class AppEscola {

	public static void main(String[] args) {

		Integer optionInteger = null;
		do  {
			String option = JOptionPane.showInputDialog("Digite: \n 1 para incluir escola \n 2 para "
					+ "listar escolas \n 3 para incluir curso \n 4 para listar cursos de uma escola "
					+ "\n 5 para adicionar aluno em um curso \n 6 para dar uma nota para o aluno em um eterminado curso \n 9 para sair");
			try {
				optionInteger = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "O campo aceita apenas números");
			}

			try {
				switch (optionInteger) {
				case 1:
					incluirEscola();
					break;

				case 2:
					listarEscola();
					break;

				case 3:
					incluirCurso();
					break;

				case 4:
					listarCursos();
					break;

				case 5:
					adicionarAluno();
					break;
				
				case 6:
					adicionarNota();
					break;

				default:
					break;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro na aplicação: "+ e); 
			}
		} while (optionInteger != 9);
	}

	private static void listarEscola() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("avaliacao");
		EntityManager em = emf.createEntityManager();

		EscolaHelper helper = new EscolaHelper(em);

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarEscolas().toArray(), null);
	}

	private static void incluirEscola() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("avaliacao");
		EntityManager em = emf.createEntityManager();

		Escola escola = new Escola();
		escola.setDescricao(JOptionPane.showInputDialog("Digite uma descricao para a escola"));
		escola.setEndereco(JOptionPane.showInputDialog("Digite o endereço da escola"));

		escola.setDataString(String.format("dd/MM/yyyy", JOptionPane.showInputDialog("Digite o endereco da escola DD/MM/YYYY")));

		EscolaHelper helper = new EscolaHelper(em);
		String retorno = helper.salvar(escola);
		JOptionPane.showMessageDialog(null, retorno);
	}

	private static void incluirCurso() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("avaliacao");
		EntityManager em = emf.createEntityManager();

		EscolaHelper helper = new EscolaHelper(em);

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarEscolas().toArray(), null);

		Curso curso = new Curso();

		curso.setDescricao(JOptionPane.showInputDialog("Digite o nome do curso"));
		curso.setEscola(escola);

		JOptionPane.showMessageDialog(null, helper.adicionarCurso(escola.getId(), curso));
	}

	private static void listarCursos() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("avaliacao");
		EntityManager em = emf.createEntityManager();

		EscolaHelper helper = new EscolaHelper(em);

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarEscolas().toArray(), null);


		JOptionPane.showInputDialog(null, "Lista de  cursos da escola" + escola.getDescricao(), "Cursos",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarCursos(escola.getId()).toArray(), null);


	}

	private static void adicionarAluno() throws Exception {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("avaliacao");
		EntityManager em = emf.createEntityManager();

		EscolaHelper helper = new EscolaHelper(em);

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarEscolas().toArray(), null);

		Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione o curso", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarCursos(escola.getId()).toArray(), null);

		Aluno aluno = new Aluno();
		aluno.setNome(JOptionPane.showInputDialog("Digite o nome do aluno"));
		String alunoAdicionado = helper.adicionarAluno(aluno);
		if (alunoAdicionado != null && !alunoAdicionado.isEmpty()) {

			List<Aluno> alunos = helper.listarAlunos();

			Aluno alunoSelecionado = alunos.get(alunos.size()-1);

			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			CursoAlunoDao dao = (CursoAlunoDao) context.getBean("jdbcCursoDao");

			JOptionPane.showMessageDialog(null, dao.adicionarCursoParaAluno(alunoSelecionado.getRm(), curso.getId()));
		}

	}

	private static void adicionarNota() {

		float nota = Float.valueOf(JOptionPane.showInputDialog("Digite a nota do aluno"));

		ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
		CursoAlunoDao dao = (CursoAlunoDao) context.getBean("jdbcCursoDao");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("avaliacao");
		EntityManager em = emf.createEntityManager();

		EscolaHelper helper = new EscolaHelper(em);

		
		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarEscolas().toArray(), null);


		Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione o curso", "Curso",
				JOptionPane.INFORMATION_MESSAGE, null, helper.listarCursos(escola.getId()).toArray(), null);
		
		List<CursoAlunoViewModel> alunosIds = dao.buscarAlunosDeUmCurso(curso.getId());
		
		List<Aluno> alunos = new ArrayList<>();
		for(CursoAlunoViewModel cursoAluno : alunosIds)  {
			Aluno alunoCorrente = helper.buscarAlunoPorId(cursoAluno.getAluno());
			alunos.add(alunoCorrente);
		}
		
		Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione o aluno que deseja adicionar a nota", "Alunos",
				JOptionPane.INFORMATION_MESSAGE, null, alunos.toArray(), null);

		try {
			JOptionPane.showMessageDialog(null, dao.adicionarNotaParaAlunoDeUmCurso(curso.getId(), aluno.getRm(), nota));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

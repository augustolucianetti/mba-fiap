package br.com.fiap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entity.Aluno;
import br.com.fiap.mapper.CursoAlunoMapper;
import br.com.fiap.viewmodel.CursoAlunoViewModel;

public class CursoAlunoDao {

	private JdbcTemplate jdbcTemplate;

	// propriedade: dataSource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String adicionarCursoParaAluno(int idAluno, int idCurso) throws Exception{
		
		try {
			String sql = "INSERT INTO CURSO_ALUNO " + "(CURSO ,ALUNO) VALUES (?,?)";
			jdbcTemplate.update(sql, idCurso, idAluno);
			return "Adicionado com sucesso!";
		} catch (Exception e) {
			e.getMessage();
			throw e;
		}
	}
	
	public List<CursoAlunoViewModel> buscarAlunosDeUmCurso(int idCurso) {
		
		String sqlQuery = "SELECT * FROM CURSO_ALUNO WHERE CURSO = ?";
		List<Integer> listaValoresParams = new ArrayList<>();
		listaValoresParams.add(idCurso);
		List<CursoAlunoViewModel> viewModel = jdbcTemplate.query(sqlQuery, listaValoresParams.toArray(), new CursoAlunoMapper());
		return viewModel;
	}
	
	public String adicionarNotaParaAlunoDeUmCurso(int idCurso, int idAluno, float nota) throws Exception {
		try {
			
			String sql = "UPDATE CURSO_ALUNO SET NOTA = ?  WHERE CURSO = ? AND ALUNO = ?";
			jdbcTemplate.update(sql, nota, idCurso, idAluno);
			return "Nota inserida com sucesso!";
		} catch (Exception e) {
			throw e;
		}
	}
}

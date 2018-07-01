package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.viewmodel.CursoAlunoViewModel;

public class CursoAlunoMapper implements RowMapper<CursoAlunoViewModel>{

	@Override
	public CursoAlunoViewModel mapRow(ResultSet rs, int arg1) throws SQLException {
		CursoAlunoViewModel cursoAlunoViewModel = new CursoAlunoViewModel();
		cursoAlunoViewModel.setId(rs.getInt("ID"));
		cursoAlunoViewModel.setNota(rs.getFloat("NOTA"));
		cursoAlunoViewModel.setAluno(rs.getInt("ALUNO"));
		cursoAlunoViewModel.setCurso(rs.getInt("CURSO"));
		
		return cursoAlunoViewModel;
	}
	
}

package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.entity.Aluno;

public class AlunoMapper implements RowMapper<Aluno>{

	@Override
	public Aluno mapRow(ResultSet rs, int arg1) throws SQLException {
		Aluno aluno = new Aluno();
		aluno.setNome(rs.getString("NOME"));
		aluno.setRm(rs.getInt("RM"));
		return aluno;
	}

}

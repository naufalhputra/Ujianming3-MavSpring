package com.juarakoding.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DatainfoRM implements RowMapper<Datainfo>{
	
	@Override
	public Datainfo mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		Datainfo datainfo = new Datainfo();
		datainfo.setDepartement(rs.getString("departement"));
		datainfo.setInfoworker(rs.getInt("infoworker"));
		
		return datainfo;
		
	}
	
}

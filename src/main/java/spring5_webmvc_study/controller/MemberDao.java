package spring5_webmvc_study.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query("select * from member where email = ?", memberRowMapper, email);
		return results.isEmpty() ? null : results.get(0);
	}

	public List<Member> selectAll(){
		return jdbcTemplate.query("select * from member", memberRowMapper);
	}
	
	public int count() {
		return jdbcTemplate.queryForObject("select count(*) from member", Integer.class);
	}
	
	/* pdf p157 */
	public void insert(Member member) {
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("insert into member(email, password, name, regdate) values(?, ?, ?, ?)", new String[] {"id"});
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
				
				return pstmt;
			}
		};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		
		/* keyHolder의 key값 알아내기 */
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
		
		
	}
	public void update(Member member) {
		jdbcTemplate.update("update member set name = ?, password = ? where email = ?", member.getName(), member.getPassword(), member.getEmail());
	}
	public void delete(Member member) {
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("delete from member where email = ?");
				
				pstmt.setString(1, member.getEmail());
				return pstmt;
			}
		};
		jdbcTemplate.update(psc);
	}
	
	// 회원 가입 날짜별 검색
	public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to){
		String sql = "select id, email, password, name, regdate from member where regdate between ? and ? order by regdate desc";
		return jdbcTemplate.query(sql, memberRowMapper, from, to);
	}
	
	// 회원 상세페이지 위한 id로 검색
	public Member selectById(Long memId) {
		String sql = "select id, email, password, name, regdate from member where id = ?";
		List<Member> results = jdbcTemplate.query(sql, memberRowMapper, memId);
		return results.isEmpty() ? null : results.get(0);
	}
	
	// MemberRowMapper 합치기
	private RowMapper<Member> memberRowMapper = new RowMapper<Member>() {
		
		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member member = new Member(
					rs.getString("email"), 
					rs.getString("password"), 
					rs.getString("name"), 
					rs.getTimestamp("regdate").toLocalDateTime()
					);
			member.setId(rs.getLong("id"));
			
			return member;
		}
	};
} 

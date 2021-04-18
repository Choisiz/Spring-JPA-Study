package Hello.Hellospring.repository;

import Hello.Hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements  MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Member save(Member member) {

        String sql = "insert into member(name) values(?)"; //sql 작성
        Connection conn = null; //DB연결객체
        PreparedStatement pstmt = null; //statement 를 상속받는 인페, sql 구문을 실행시키는 기능의 객체
        ResultSet rs = null; //db의 결과를 나타내는 데이터 테이블
        //statement 와 PreparedStatement 차이
        //결론적으로 PreparedStatement 사용을 권유한다.(SQL Injection 등 보안등으로)
        //시큐어 코딩에서 PreparedStatement 사용권유
        //차이는 캐시 사용유무이다. PreparedStatement 는 객체를 캐시에 담아 재사용한다.
        //따라서 반복적 쿼리시 PreparedStatement 성능이 더 좋다.
        try{
            conn = getConnection(); //DB연결
            pstmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,member.getName());
            pstmt.executeUpdate();
            rs=pstmt.getGeneratedKeys();//자동생성키

            if(rs.next()){ //결과에서 값이 있으면
                member.setId(rs.getLong(1));
            }else{
                throw new SQLException("id 조회 실패");
            }
            return member;
        }catch (Exception e) {
            throw  new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs); //자원 끊어주기
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt= conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e) {
            throw  new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt= conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        }catch (Exception e) {
            throw  new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name =?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt= conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e) {
            throw  new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void  close(Connection conn)throws SQLException {
        DataSourceUtils.releaseConnection(conn,dataSource);
    }
}

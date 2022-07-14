package com.example.springstudy.dao;

import com.example.springstudy.dbconntection.ConnectionMaker;
import com.example.springstudy.dbconntection.DConnectionMaker;
import com.example.springstudy.dbconntection.SimpleConnectionMaker;
import com.example.springstudy.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

/*
    UserDao 관심사항
    1. DB와 연결을 위한 커넥션을 어떻게 가져올것인가
    2. SQL 문장을 담을 Statement를 만들고 실행하기
    3. 작업이 끝나면 사용한 Statement와 Connection 오브젝트를 닫아 리소스를 돌려주기
 */
public class UserDao {

    /*
        현 문제점: DB 커넥션을 가져오는 코드가 여기저기 중복되어 나타나 있다.
        이렇게 하나의 관심사가 방만하게 중복되어 있고, 여기저기 흩어져 있어서 다른 관심의 대상과 얽혀 있으면
        변경이 일어날때 문제가 발생한다.

     */

    /*
        1. 메서드 추출로 인한 문제 해결: 중복된 DB 연결 코드를 getConnection()이라는 이름의 독립적인 메소드로 만들어준다.
        -> 관심ㅁㅁ이 다른 코드가 있는 메서드에는 영향을 주지 않을뿐더러, 관심 내용이 독립적으로 존재하므로 수정도 간단해졌다.
     */


    /*
        2. 상속을 통한 확장
        - 기존에는 같은 클래스에 다른 메소드로 분히됐던 DB 커넥션 연결이라는 관심을 이번에는 상속을 통해 서브클래스로 분리해보자
        why?
        만약 각각의 회사가 다른 DB를 사용한다는 가정하에
        고객에게 UserDao 의 소스코드를 직접 제공하지 않고, 미리 컴파일된 클래스 바이너리 파일만 제공하기 위해서
     */

    /*
       3. 클래스의 분리
       -완전히 독립적인 클래스로 분리하기
     */

    /*
       4. 인터페이스의 도입
       - 클래스의 분리 시 N사 D사의 별도의 DB 문제 해결을 위해서
       -> 하지만 그래도 new DConnectionMaker라는 특정 클래스의 객체를 UserDao에서 생성해주어야하는 문제 발생

     */

    /*
       5. 관계설정 책임의 분리
       - 클라이언트 오브젝트가 제3의 관심사항인 UserDao와 ConnectionMaker 구현 클래스의 관계를 결정
       - 클래스 사이에 관례를 만들지 않고 단지 오브젝트 사이에 다이내믹한 관계를 만든다.
     */
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeNewConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException
    {
        Connection c = connectionMaker.makeNewConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id =?"
        );
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    //public abstract Connection getConnection() throws SQLException, ClassNotFoundException;

    private Connection getConnectionV1() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection c = DriverManager.getConnection("jdbc:h2:mem:h2db","sa","sa");
        return c;
    }

//    public void addV2(User user) throws ClassNotFoundException, SQLException {
//        Connection c = getConnection();
//
//        PreparedStatement ps = c.prepareStatement(
//                "insert into users(id, name, password) values(?,?,?)"
//        );
//        ps.setString(1, user.getId());
//        ps.setString(2,user.getName());
//        ps.setString(3,user.getPassword());
//
//        ps.executeUpdate();
//
//        ps.close();
//        c.close();
//    }
//
//    public User getV2(String id) throws ClassNotFoundException, SQLException
//    {
//        Connection c = getConnection();
//
//        PreparedStatement ps = c.prepareStatement(
//                "select * from users where id =?"
//        );
//        ps.setString(1,id);
//
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        User user = new User();
//        user.setId(rs.getString("id"));
//        user.setName(rs.getString("name"));
//        user.setPassword(rs.getString("password"));
//
//        rs.close();
//        ps.close();
//        c.close();
//
//        return user;
//    }
//
//    public void addV1(User user) throws ClassNotFoundException, SQLException {
//        Class.forName("org.h2.Driver");
//        Connection c = DriverManager.getConnection("jdbc:h2:mem:h2db","sa","sa");
//
//        PreparedStatement ps = c.prepareStatement(
//                "insert into users(id, name, password) values(?,?,?)"
//        );
//        ps.setString(1, user.getId());
//        ps.setString(2,user.getName());
//        ps.setString(3,user.getPassword());
//
//        ps.executeUpdate();
//
//        ps.close();
//        c.close();
//    }
//
//    public User getV1(String id) throws ClassNotFoundException, SQLException
//    {
//        Class.forName("org.h2.Driver");
//        Connection c = DriverManager.getConnection("jdbc:h2:mem:h2db","sa","sa");
//
//        PreparedStatement ps = c.prepareStatement(
//                "select * from users where id =?"
//        );
//        ps.setString(1,id);
//
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        User user = new User();
//        user.setId(rs.getString("id"));
//        user.setName(rs.getString("name"));
//        user.setPassword(rs.getString("password"));
//
//        rs.close();
//        ps.close();
//        c.close();
//
//        return user;
//    }
}


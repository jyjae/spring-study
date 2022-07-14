package com.example.springstudy.dbconntection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    클래스의 분리: 관심사를 본격적으로 독립시키면서 동시에 손쉽게 확장
    -> 하지만 N사 D사 별로 DB가 다를경우의 문제를 해결하지 못함
    -> 해결 방법: 인터페이스의 도입
        - 두 개의 클래스가 서로 긴밀하게 연결되어 있지 않도록 중간에 추상적인 느슨한 연결고리를 만들어주는 방법
 */
public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection c = DriverManager.getConnection("jdbc:h2:mem:h2db", "sa", "sa");
        return c;
    }
}

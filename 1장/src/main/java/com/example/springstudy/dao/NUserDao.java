package com.example.springstudy.dao;

import com.example.springstudy.dbconntection.ConnectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    상속을 통해
    1. UserDao: DAO 핵심 기능인 어떻게 데이터를 등록하고 가져올것인가
    2. NUserDao: DB 연결 방법은 어떻게 할것인가
    라는 관심을 분리할 수 있다.

    1. 템플릿 메소드 패턴
    - 슈퍼클래스에서 기본적인 로직의 흐름을 만들고,
    그 기능의 일부를 추상 메서드나 오버라이딩이 가능한 protexted 메서드 등으로 만든 뒤
    서브 클래스에서 이런 메서드를 필요에 맞게 구현해서 사용하도록 하는 방법

    2. 팩토리 메소드 패턴
    - 서브클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것것
 */
class NUserDao extends UserDao {
    public NUserDao(ConnectionMaker connectionMaker) {
        super(connectionMaker);
    }
//
//    @Override
//    public Connection getConnection() throws SQLException, ClassNotFoundException {
//        Class.forName("org.h2.Driver");
//        Connection c = DriverManager.getConnection("jdbc:h2:mem:h2db", "sa", "sa");
//        return c;
//    }
}

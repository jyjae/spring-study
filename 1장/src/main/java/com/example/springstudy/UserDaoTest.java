package com.example.springstudy;

import com.example.springstudy.dao.DaoFactory;
import com.example.springstudy.dao.UserDao;
import com.example.springstudy.dbconntection.ConnectionMaker;
import com.example.springstudy.dbconntection.DConnectionMaker;
import com.example.springstudy.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
/*
    UserDaoTest는 단순 테스트를 위한 클래스이다.
 */
public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        SpringApplication.run(SpringstudyApplication.class, args);

        // 테스트를 위한 클래스에서 오브젝트를 생성중.. <- 책임과 관심사 분리 안됌
        //해결 방안: 팩토리 클래스를 만들어 객체의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려받자
        //ConnectionMaker connectionMaker = new DConnectionMaker();
        //UserDao dao = new UserDao(connectionMaker);


        //이제 Application Context를 만들어보자
        // @Configuration이 붙은 자바코드를 설정정보로 사용하려면 AnnotationConfigApplicationContext를 사용
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DaoFactory.class);
        // getBean()은 애플리케이션 컨텍스트가 관리하는 오브젝트를 요청하는 메소드
        UserDao dao = context.getBean("userDao", UserDao.class); 
        User user = new User();
        user.setId("whiteship");
        user.setName("정연재");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId()+" 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId()+" 조회 성공");
    }

}

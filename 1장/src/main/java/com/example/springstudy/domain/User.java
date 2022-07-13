package com.example.springstudy.domain;

import lombok.Getter;
import lombok.Setter;

/*
 *  자바빈 : 다음 두 가지 관례를 따라 만들어진 오브젝트를 가리킨다.
 *  1. 디폴트 생성자: 자바빈은 파라미터가 없는 디폴트 생성자를 갖고 있어야 한다.
 *      툴이나 프레임워크에서 리플렉션을 이용해 오브젝트를 생성하기 때문에 필요하다.
 *  2. 프로퍼티: 자바빈이 노출하는 이름을 가진 속성을 프로퍼티라고 한다.
 *      프로퍼티는 set으로 시작하는 수정자 메서드와 get으로 시작하는 접근자 메소드를 이용해 수정 또는 조회할 수 있다.
 */
@Getter
@Setter
public class User {
    String id;
    String name;
    String password;

}

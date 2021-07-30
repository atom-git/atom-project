package com.atom.mock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author zr
 * @description springboot context mvc测试
 * @date 2021/04/22
 */
@SpringBootTest
@Slf4j
public class MvcTests {
	/**
	 * rest请求模板
	 */
//	@Resource
//	private TestRestTemplate restTemplate;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Test
	public void generatePassword () {
		String password = "!QAZ2wsx";
		log.info("{}序列生成的密码为：{}", password, passwordEncoder.encode(password));
	}
}

package com.siy.protal;

import com.siy.protal.utils.CryptUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtalApplication.class)
public class ProtalApplicationTests {

	@Test
	public void contextLoads() {
		String chengliang = CryptUtils.encrypt("chengliang");
		System.out.println(chengliang);
		String decrypt = CryptUtils.decrypt(chengliang);
		System.out.println(decrypt);
	}

}

package study.spring.multidatasource.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper mapper;

	@Test
	void insert() {
		UserDto userDto = UserDto.builder()
				.loginId("hellose")
				.loginPassword("1234")
				.build();
		Assertions.assertNull(userDto.getId());

		mapper.insert(userDto);
		Assertions.assertNotNull(userDto.getId());
	}
}

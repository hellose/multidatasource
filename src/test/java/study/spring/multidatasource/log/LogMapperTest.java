package study.spring.multidatasource.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogMapperTest {

	@Autowired
	private LogMapper mapper;

	@Test
	void insert() {
		LogDto logDto = new LogDto("spring boot LogMapperTest");
		mapper.insert(logDto);
	}
}

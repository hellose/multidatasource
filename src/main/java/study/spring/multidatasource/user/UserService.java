package study.spring.multidatasource.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.spring.multidatasource.log.LogService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserMapper userMapper;
	private final LogService logService;

	@Transactional
	public void insertUser(UserDto userDto) {
		log.debug("=== UserService.insertUser ===");
		userMapper.insert(userDto);

		logService.insertLog(userDto);
	}

}

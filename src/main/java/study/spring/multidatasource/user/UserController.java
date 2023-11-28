package study.spring.multidatasource.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.spring.multidatasource.DatabaseAction;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/insert")
	public String register() throws Exception {
		// insert dto 생성
		UserDto userDto = UserDto.builder()
								.id(1L)
								.loginId("hellose")
								.loginPassword("1234")
								.build();
		userDto.setDatabaseAction(DatabaseAction.INSERT);

		// insert
		userService.insertUser(userDto);
		return "ok";
	}

}

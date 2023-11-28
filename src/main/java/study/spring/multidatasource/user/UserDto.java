package study.spring.multidatasource.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.spring.multidatasource.LogTextConvertable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends LogTextConvertable {

	private Long id;
	private String loginId;
	private String loginPassword;
	private String nickname;

	@Override
	public String toLogText() {
		if (this.getDatabaseAction() == null) {
			throw new RuntimeException("DatabaseAction can not be null");
		} else {
			return "USER: " + this.getDatabaseAction() + ": (ID: " + id + ", LOGIN_ID: " + loginId
					+ ", NICKNAME: " + nickname + ")";
		}
	}

}

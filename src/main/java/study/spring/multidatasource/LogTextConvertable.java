package study.spring.multidatasource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class LogTextConvertable {

	private DatabaseAction databaseAction;

	public abstract String toLogText();
}

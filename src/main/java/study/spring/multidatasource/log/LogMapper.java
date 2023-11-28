package study.spring.multidatasource.log;

import study.spring.multidatasource.config.MapperSecondary;

@MapperSecondary
public interface LogMapper {

	void insert(LogDto logDto);
}

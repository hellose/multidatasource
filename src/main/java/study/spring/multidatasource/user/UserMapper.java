package study.spring.multidatasource.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	void insert(UserDto userDto);

	UserDto select(Long id);

	void update(UserDto userDto);

	void delete(Long id);

}

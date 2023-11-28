package study.spring.multidatasource.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.spring.multidatasource.LogTextConvertable;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

	private final LogMapper logMapper;

	// TODO - 로그 확인 및 테스트중
	@Transactional("secondaryTransactionManager")
	public void insertLog(LogTextConvertable logWritable) {
		log.debug("=== LogService.insertLog ===");

		LogDto logDto = new LogDto(logWritable.toLogText());
		logMapper.insert(logDto);
	}
}

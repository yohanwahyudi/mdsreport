package test.prop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

@Service("service")
public class MyService implements IService {

	@Value("${config.string.keyUnresolvable}")
	private String stringKeyUnresolvable;

	@Value("${config.string.key}")
	private String stringKey;

	// treated as null
	@Value("${config.string.keyNull}")
	private String stringKeyNull;

	@Value("${config.int.key}")
	private int intKey;

	// not define in properties file
	@Value("${config.int.keyDefault:2016}")
	private int intKeyDefault;

	@Value("${config.boolean.key}")
	private boolean boolKey;

	@Value("${config.intList.key}")
	private List<Integer> intList;

	@Value("${config.stringList.key}")
	private List<String> stringList;

	@DateTimeFormat(pattern="yy/MM/dd HH:mm")
	@Value("${config.time.key}")
	private LocalDateTime time;
	
	@Value("${config.pattern.key}")
	private Pattern pattern;

	@Override
	public String getValue() {
		String result = "Properties Values from Configuration File:\n";

		result += "- stringKeyUnresolvable: " + stringKeyUnresolvable + "\n";
		result += "- stringKey: " + stringKey + "\n";
		result += "- stringKeyNull: " + stringKeyNull + "\n";
		result += "- intKey: " + intKey + "\n";
		result += "- intKeyDefault: " + intKeyDefault + "\n";
		result += "- boolKey: " + boolKey + "\n";
		result += "- intList: " + intList + "\n";
		result += "- stringList: " + stringList + "\n";
		result += "- time: " + time + "\n";
		result += "- pattern: " + pattern + "\n";

		return result;
	}
}


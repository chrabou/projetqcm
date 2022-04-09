package net.codejava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Collections.*;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn(List<String> stringList) {
		if (stringList == null) {
			return "";
		}

		for (int i = 0; i < stringList.size(); ++i) {
			stringList.set(i, stringList.get(i).replaceAll(";", ""));
		}
		return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
	}

	@Override
	public List<String> convertToEntityAttribute(String string) {
		return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
	}

	public static List<String> convertToEntityAttribute2(String string) {
		return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
	}
}
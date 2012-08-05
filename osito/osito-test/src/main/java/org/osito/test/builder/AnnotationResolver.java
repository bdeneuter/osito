package org.osito.test.builder;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class AnnotationResolver {

	void resolveForBuilding(Object obj) {
		for (Field field : getEmptyFieldsWithGenerateDefaultValueAnnotation(obj)) {
			AbstractTestBuilder<?> builder = createTestBuilder(field);
			setValue(obj, field, builder.build());
		}
	}

	private AbstractTestBuilder<?> createTestBuilder(Field field) {
		return createTestBuilder(field.getType());
	}

	private AbstractTestBuilder<?> createTestBuilder(Class<?> clazz) {
		try {
			String className = clazz.getName() + "TestBuilder";
			return (AbstractTestBuilder<?>) clazz.getClassLoader().loadClass(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("No test builder found for " + clazz.getCanonicalName(), e);
		}
	}

	private void setValue(Object obj, Field field, Object value) {
		try {
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Field> getEmptyFieldsWithGenerateDefaultValueAnnotation(Object obj) {
		List<Field> result = new ArrayList<Field>();
		for (Field field : getFields(obj)) {
			if (valueIsNull(field, obj)
					&& field.isAnnotationPresent(CreateDefaultWithBuilder.class)) {
				result.add(field);
			}
		}
		return result;
	}

	private List<Field> getFields(Object obj) {
		return getFields(obj.getClass());
	}

	private List<Field> getFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(asList(clazz.getDeclaredFields()));
		fields.addAll(getFieldsFromSuperClass(clazz));

		return fields;
	}

	private List<Field> getFieldsFromSuperClass(Class<?> clazz) {
		Class<?> parent = clazz.getSuperclass();
		if (parent != null) {
			return getFields(parent);
		}
		return emptyList();
	}

	private boolean valueIsNull(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.get(obj) == null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
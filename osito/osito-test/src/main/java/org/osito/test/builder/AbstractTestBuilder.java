package org.osito.test.builder;

import org.mockito.MockitoAnnotations;

public abstract class AbstractTestBuilder<T> {

	private AnnotationResolver annotationResolver = new AnnotationResolver();

	public AbstractTestBuilder() {
		MockitoAnnotations.initMocks(this);
	}

	public final T build() {
		annotationResolver.resolveForBuilding(this);
		return buildObject();
	}

	protected abstract T buildObject();

}
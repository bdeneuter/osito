package org.osito.test.jenkins;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.NODESET;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;

public class JenkinsRunner extends ParentRunner<Runner> {

	private static final Logger LOG = LoggerFactory.getLogger(JenkinsRunner.class);

	private Class<?> testClass;

	public JenkinsRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
		this.testClass = testClass;
	}

	public static String CHECK_STORIES_ON_JENKINS = "checkStoriesOnJenkins";


	@Override
	protected List<Runner> getChildren() {
		return getFailedTests().transform(toClass()).filter(notNull()).transform(toRunner()).toList();
	}

	private Function<Class<?>, Runner> toRunner() {
		return new Function<Class<?>, Runner>() {

			@Override
			public Runner apply(Class<?> testClass) {
				try {
					return new AllDefaultPossibilitiesBuilder(true).runnerForClass(testClass);
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	private Function<String, Class<?>> toClass() {
		return new Function<String, Class<?>>() {

			@Override
			public Class<?> apply(String className) {
				try {
					return Class.forName(className);
				} catch (ClassNotFoundException e) {
					LOG.info("Can not find test with class " + className);
					return null;
				}
			}
		};
	}

	@Override
	protected Description describeChild(Runner child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(Runner child, RunNotifier notifier) {
		child.run(notifier);
	}

	private FluentIterable<String> getFailedTests() {
		return from(newArrayList(getJenkinsJobs().value())).transformAndConcat(toFailedTests());
	}

	private Jobs getJenkinsJobs() {
		Jobs jobs = testClass.getAnnotation(Jobs.class);
		if (jobs == null) {
			throw new IllegalStateException("Please add @Jobs annotation to define the jenkins jobs");
		}
		return jobs;
	}

	private Function<String, Iterable<String>> toFailedTests() {
		return new Function<String, Iterable<String>>() {

			@Override
			public Iterable<String> apply(String job) {
				return getFailedTestsIn(job);
			}
		};
	}

	private Set<String> getFailedTestsIn(String job) {
		String testReport = getTestReport(job);
		if (testReport != null) {
			return parseReport(testReport);
		} else {
			return new HashSet<String>();
		}
	}

	private Set<String> parseReport(String testReport) {
		Set<String> failedTests = Sets.newHashSet();
		Document document = parseDocument(testReport);

		NodeList cases = getTestCases(document);
		for (int i = 0; i < cases.getLength(); i++) {
			DocumentFragment testCase = createFragment(document, cases.item(i));
			if (isFailed(getStatus(testCase))) {
				String className = getClassName(testCase);
				failedTests.add(className);
				LOG.info("Class to test: " + className);
			}
		}
		return failedTests;
	}

	private NodeList getTestCases(Document document) {
		return evaluateXPathExpression(document, "//case", NODESET);
	}

	private String getClassName(DocumentFragment testCase) {
		Node className = evaluateXPathExpression(testCase, "//className", NODE);
		String textContent = className.getTextContent();
		return textContent;
	}

	private String getTestReport(String job) {
		HttpClient client = HttpClientBuilder.create().build();
		try {
			HttpResponse response = client.execute(new HttpGet(host() + "/jenkins/job/" + job + "/lastUnstableBuild/testReport/api/xml"));
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				return IOUtils.toString(response.getEntity().getContent());
			} else if (statusCode == 404) {
				return null;
			} else {
				throw new RuntimeException("Could not fetch failed tests for job " + job + ": status code " + statusCode);
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	private String host() {
		return testClass.getAnnotation(Host.class).value();
	}

	private Node getStatus(DocumentFragment fragment) {
		return evaluateXPathExpression(fragment, "//status", NODE);
	}

	private DocumentFragment createFragment(Document document, Node item) {
		DocumentFragment fragment = document.createDocumentFragment();
		fragment.appendChild(item);
		return fragment;
	}

	private boolean isFailed(Node status) {
		return "FAILED".equalsIgnoreCase(status.getTextContent()) || "REGRESSION".equalsIgnoreCase(status.getTextContent());
	}

	protected Document parseDocument(String content) {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = domFactory.newDocumentBuilder();
			Document document = builder.parse(inputStream(content));
			return document;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream inputStream(String content) {
		return new ByteArrayInputStream(content.getBytes());
	}

	private XPathExpression getXPathExpression(XPath xPath, String expression) {
		try {
			return xPath.compile(expression);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> T evaluateXPathExpression(Node node, String expression, QName returnType) {
		return evaluateXPathExpression(node, getxPath(), expression, returnType);
	}

	@SuppressWarnings("unchecked")
	private <T> T evaluateXPathExpression(Node node, XPath xPath, String expression, QName returnType) {
		try {
			XPathExpression xpathExpression = getXPathExpression(xPath, expression);

			return (T) xpathExpression.evaluate(node, returnType);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	protected XPath getxPath() {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		return xPath;
	}

}

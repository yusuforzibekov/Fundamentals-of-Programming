package com.epam.rd.autocode.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.epam.rd.autocode.queue.CashBox.State;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * @author D. Kolesnikov
 */
public class ShopTest {

	//////////////////////////////////////////////////////////////////////////////

	private static boolean isAllTestsMustFailed;

	private static Throwable complianceTestFailedCause;

	static {
		try {
			String testClassName = new Exception().getStackTrace()[0].getClassName();
			String className = testClassName.substring(0, testClassName.lastIndexOf("Test"));
			Class<?> c = Class.forName(className);

			java.lang.reflect.Method[] methods = { 
					c.getDeclaredMethod("addBuyer", Buyer.class),
					c.getDeclaredMethod("getCashBox", int.class),
					c.getDeclaredMethod("setCashBoxState", int.class, CashBox.State.class),
					c.getDeclaredMethod("tact"),
					c.getDeclaredMethod("print")
			};

			org.apache.bcel.classfile.JavaClass jc = org.apache.bcel.Repository.lookupClass(c);
			for (java.lang.reflect.Method method : methods) {
				org.apache.bcel.classfile.Method m = jc.getMethod(method);
				org.apache.bcel.classfile.Code code = m.getCode();
				Assertions.assertTrue(code.getCode().length > 2, () -> m + " is not a stub");
			}
		} catch (Throwable t) {
			isAllTestsMustFailed = true;
			complianceTestFailedCause = t;
			t.printStackTrace();
		}
	}

	{
		if (isAllTestsMustFailed) {
			Assertions.fail(() -> "Compliance test failed: " + complianceTestFailedCause.getMessage());
		}
	}

	//////////////////////////////////////////////////////////////////////////////
	
	private static final PrintStream STD_OUT = System.out;

	private Shop shop;
	
	@BeforeEach
	void setUp() {
		Buyer.resetNames();
		shop = new Shop(5);
	}
	
	@Test
	void test1() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);
		shop.tact();

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(4, State.ENABLED);
		shop.tact();
		
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		String actual = getState(shop);
		String expected = "+DG-+E-+F";
		assertEquals(expected, actual);
	}

	@Test
	void test2() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(1, State.ENABLED);
		shop.setCashBoxState(3, State.ENABLED);
		shop.tact();

		String actual = getState(shop);
		String expected = "+BC+ED+GH+I-";
		assertEquals(expected, actual);
	}

	@Test
	void test3() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.IS_CLOSING);
		shop.tact();

		String actual = getState(shop);
		String expected = "+BCDE-|GHI--";
		assertEquals(expected, actual);
	}

	@Test
	void test4() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.IS_CLOSING);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.tact();

		shop.setCashBoxState(3, State.ENABLED);
		shop.tact();
		
		String actual = getState(shop);
		String expected = "+CDE-|HI+MLK-";
		assertEquals(expected, actual);
	}

	@Test
	void test5() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.IS_CLOSING);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.tact();

		shop.setCashBoxState(3, State.ENABLED);
		shop.tact();

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		String actual = getState(shop);
		String expected = "+CDENP-|HI+MLKOQ-";
		assertEquals(expected, actual);
	}

	@Test
	void test6() throws IOException {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(1, State.ENABLED);
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(0, State.IS_CLOSING);
		shop.addBuyer(Buyer.nextBuyer());

		shop.tact();
		
		String actual = getState(shop);
		String expected = "|BC+FD---";
		assertEquals(expected, actual);
	}
	
	@Test
	void complianceTestLambdaExpressionsAreRestrictedForUsing() {
		Stream.of(Shop.class)
			.map(Class::getDeclaredMethods)
			.flatMap(Stream::of)
			.filter(m -> Modifier.isStatic(m.getModifiers()))
			.filter(m -> Modifier.isPrivate(m.getModifiers()))
			.map(Method::getName)
			.filter(name -> name.contains("lambda$"))
			.findAny()
			.ifPresent(m -> 
					fail(() -> "Using of lambda expressions is restricted: " + m));
	}

	@Test
	void appShouldUseOnlyOptionalFromJavaUtilPackage() {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("src/main/java/");
		spoon.buildModel();

		spoon.getModel()
			.getElements(new TypeFilter<>(CtTypeReference.class))
			.stream()
			.filter(r -> r.toString().startsWith("java.util.stream"))
			.map(CtTypeReference::getQualifiedName)
			.findAny()
			.ifPresent(name -> 
					fail(() -> "Using of stream API is restricted: " + name));
	}
	
	@Test
	void addBuyerShouldAddBuyerToProperCashBoxWhenThereAreMoreThanOneShortestQueues() {
		shop = new Shop(3);
		for (int j = 0; j < 3; j++) {
			shop.getCashBox(j).setState(State.ENABLED);
		}

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(0).getQueue().getLast().toString(), "A");

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(1).getQueue().getLast().toString(), "B");

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(2).getQueue().getLast().toString(), "C");

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(0).getQueue().getLast().toString(), "D");
	}
	
	@Test
	void printShouldWorkProperly() throws IOException {
		shop = new Shop(3);
		String actual = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos)) {
			System.setOut(ps);

			shop.setCashBoxState(0, State.ENABLED);
			shop.setCashBoxState(1, State.ENABLED);
			shop.setCashBoxState(2, State.ENABLED);
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());

			shop.setCashBoxState(0, State.IS_CLOSING);
			shop.tact();
			shop.addBuyer(Buyer.nextBuyer());

			shop.setCashBoxState(1, State.IS_CLOSING);
			shop.addBuyer(Buyer.nextBuyer());
			shop.addBuyer(Buyer.nextBuyer());
			shop.tact();
			shop.tact();
			
			shop.print();
			actual = baos.toString();
		} finally {
			System.setOut(STD_OUT);
		}
		
		actual = actual.replaceAll("\r", "").trim();
		String expected = "#0[-]~#1[|]J~#2[+]KL".replace('~', '\n');
		assertEquals(expected, actual);
	}
	
	private static String getState(Shop shop) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < shop.getCashBoxCount(); j++) {
			CashBox cashBox = shop.getCashBox(j);
			State s = cashBox.getState();
			sb.append(s == State.ENABLED ? '+' : (s == State.DISABLED ? '-' : '|'));
			for (Buyer b : shop.getCashBox(j).getQueue()) {
				sb.append(b);
			}
		}
		return sb.toString();
	}

}

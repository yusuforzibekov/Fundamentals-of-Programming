package com.epam.rd.autocode.queue;

import java.util.Deque;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author D. Kolesnikov
 */
public class CashBoxTest {

	private static boolean isAllTestsMustFailed;

	private static Throwable complianceTestFailedCause;

	static {
		try {
			String testClassName = new Exception().getStackTrace()[0].getClassName();
			String className = testClassName.substring(0, testClassName.lastIndexOf("Test"));
			Class<?> c = Class.forName(className);

			java.lang.reflect.Method[] methods = { 
					c.getDeclaredMethod("getQueue"),
					c.getDeclaredMethod("serveBuyer"),
					c.getDeclaredMethod("inState", CashBox.State.class),
					c.getDeclaredMethod("notInState", CashBox.State.class),
					c.getDeclaredMethod("addLast", Buyer.class),
					c.getDeclaredMethod("removeLast"),
					c.getDeclaredMethod("toString"),
					c.getDeclaredMethod("setState", CashBox.State.class),
					c.getDeclaredMethod("getState")
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
	
	@Test
	void test() {
		CashBox cb = new CashBox(0);
		cb.addLast(Buyer.nextBuyer());
		Deque<Buyer> q1 = cb.getQueue();
		Deque<Buyer> q2 = cb.getQueue();
		
		Assertions.assertIterableEquals(q1, q2);
		Assertions.assertNotSame(q1, q2);
	
			
	}

}
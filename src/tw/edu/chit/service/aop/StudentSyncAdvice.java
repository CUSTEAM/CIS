package tw.edu.chit.service.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

public class StudentSyncAdvice implements AfterReturningAdvice {

	Logger log = Logger.getLogger(StudentSyncAdvice.class);

	public void afterReturning(Object org, Method method, Object[] args,
			Object target) throws Throwable {

		log.error("Method Name:" + method.getName());
		log.error("Target Name:" + target.toString());

	}

}

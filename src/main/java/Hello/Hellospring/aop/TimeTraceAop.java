package Hello.Hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* Hello.Hellospring.service..*(..))")
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis(); //시작시간
        System.out.println("시작:"+joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally {
            long finish =System.currentTimeMillis(); //종료시간
            long timeMs = finish - start;
            System.out.println("종료:"+ joinPoint.toString()+ " " +timeMs+"ms");
        }
    }
}

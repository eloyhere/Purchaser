package team.item.purchaser.forum.aspect;

import java.util.*;
import org.aspectj.lang.*;
import java.util.function.*;
import java.util.stream.Stream;

import jakarta.servlet.http.*;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import team.item.purchaser.forum.exception.AuthenticationException;
import team.item.purchaser.forum.exception.CacheableException;
import team.item.purchaser.forum.exception.DuplicateElementException;
import team.item.purchaser.forum.exception.ParameterPresentException;

import javax.management.modelmbean.ModelMBeanConstructorInfo;


@Aspect
@Component
public class ResponseAspect {

    private static final List<Class<?>> whiteList = List.of(
            ModelAndView.class,
            ResponseEntity.class
    );

    private static final HashMap<Class<? extends Throwable>, Function<Throwable, ResponseEntity<?>>> mapper = new HashMap<>();

    static {
        mapper.put(NullPointerException.class, (throwable)-> new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        mapper.put(DuplicateElementException.class, (throwable)-> new ResponseEntity<>(throwable.getMessage(), HttpStatus.BAD_REQUEST));
        mapper.put(AuthenticationException.class, (throwable) -> new ResponseEntity<>(throwable.getMessage(), HttpStatus.UNAUTHORIZED));
        mapper.put(ParameterPresentException.class, (throwable)-> new ResponseEntity<>(throwable.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @Pointcut("execution(* team.item.purchaser.forum.controller.*.*(..))")
    public void pointcut(){

    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point){
        try {
            Object result = point.proceed();
            Class<?> clazz = result.getClass();
            if(whiteList.contains(clazz)){
                return result;
            }
            return ResponseEntity.ok(result);
        }catch (Throwable throwable){
            return mapper.getOrDefault(throwable.getClass(), (t)-> new ResponseEntity<>(t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).apply(throwable);
        }
    }
}

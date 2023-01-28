package team.item.purchaser.forum.resolver;

import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.*;
import team.item.purchaser.forum.exception.ParameterPresentException;

import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.*;

public class EntityResolver implements HandlerMethodArgumentResolver {

    private static final String DateFormatPattern = "yyyy-MM-dd HH:mm:ss";

    private final HashMap<Class<?>, Function<String, ?>> handler = new HashMap<>();

    public EntityResolver(){
        handler.put(UUID.class, (Function<String, UUID>) uuid -> {
            uuid = uuid.replaceAll("\"", "");
            return UUID.fromString(uuid);
        });
        handler.put(Integer.class, (Function<String, Integer>) Integer::parseInt);
        handler.put(LocalDate.class, (Function<String, LocalDate>) text -> LocalDate.parse(text, DateTimeFormatter.ofPattern(DateFormatPattern)));
        handler.put(LocalTime.class, (Function<String, LocalTime>) text -> LocalTime.parse(text, DateTimeFormatter.ofPattern(DateFormatPattern)));
        handler.put(LocalDateTime.class, (Function<String, LocalDateTime>) text -> LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DateFormatPattern)));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz.isAnnotationPresent(Entity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> clazz = parameter.getParameterType();
        Object object = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String[]> parameterMap = Objects.requireNonNull(request).getParameterMap();
        for (Field field : fields) {
            field.setAccessible(true);
            String[] values = parameterMap.get(field.getName());
            if(Objects.nonNull(values) && values.length > 0){
                field.set(object, handler.getOrDefault(field.getType(), content -> content).apply(values[0]));
            }
        }
        return object;
    }
}

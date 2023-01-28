package team.item.purchaser.forum.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.util.ParameterMap;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.util.*;
import java.util.function.BiFunction;

public class UUIDListResolver implements HandlerMethodArgumentResolver {

    private static final List<Class<?>> whiteList = List.of(List.class, Set.class, Queue.class, Collection.class);


    private final BiFunction<HttpServletRequest, MethodParameter, ArrayList<UUID>> handler = (request, parameter) -> {
        Map<String, String[]> parameters = request.getParameterMap();
        String[] values = parameters.get("uuids");
        ArrayList<UUID> list = new ArrayList<>();
        if(values[0].contains(",")){
            values = values[0].split(",");
        }
        for (String value : values) {
            list.add(UUID.fromString(value.replaceAll("\"", "")));
        }
        return list;
    };

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return whiteList.contains(parameter.getParameterType());
    }

    @Override
    public List<UUID> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return handler.apply(webRequest.getNativeRequest(HttpServletRequest.class), parameter);
    }
}

package team.item.purchaser.forum.converter;

import java.util.*;
import jakarta.persistence.*;

@Converter
public class Videos implements AttributeConverter<ArrayList<String>, String> {

    @Override
    public String convertToDatabaseColumn(ArrayList<String> parameter) {
        return parameter.stream().reduce("", (a, b)-> a+","+b);
    }

    @Override
    public ArrayList<String> convertToEntityAttribute(String string) {
        ArrayList<String> result = new ArrayList<>();
        String[] array = string.split(",");
        Collections.addAll(result, array);
        return result;
    }
}
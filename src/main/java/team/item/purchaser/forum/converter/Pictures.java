package team.item.purchaser.forum.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import jakarta.persistence.*;
import team.item.purchaser.forum.exception.EntityConvertException;

import javax.sql.rowset.serial.SerialBlob;

@Converter
public class Pictures implements AttributeConverter<ArrayList<String>, Blob> {

    @Override
    public Blob convertToDatabaseColumn(ArrayList<String> parameter) {
        try {
            String text = parameter.stream().reduce("", (a, b)-> a+","+b);
            return new SerialBlob(text.getBytes(StandardCharsets.UTF_8));
        }catch (SQLException exception){
            throw new EntityConvertException(exception);
        }
    }

    @Override
    public ArrayList<String> convertToEntityAttribute(Blob blob) {
        try {
            InputStream stream = blob.getBinaryStream();
            char[] buffer = new char[stream.available()];
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            int read = reader.read(buffer);
            ArrayList<String> result = new ArrayList<>();
            String[] array = new String(buffer).split(",");
            Collections.addAll(result, array);
            return result;
        }catch (IOException | SQLException exception){
            throw new EntityConvertException(exception);
        }
    }
}

package team.item.purchaser.forum.converter;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;
import org.hibernate.Hibernate;
import org.hibernate.type.descriptor.java.BlobJavaType;
import team.item.purchaser.forum.exception.EntityConvertException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

@Converter
public class Picture implements AttributeConverter<String, Blob> {

    @Override
    public Blob convertToDatabaseColumn(String string) {
        try {
            return new SerialBlob(string.getBytes(StandardCharsets.UTF_8));
        }catch (SQLException exception){
            throw new EntityConvertException(exception);
        }
    }

    @Override
    public String convertToEntityAttribute(Blob blob) {
        try {
            InputStream stream = blob.getBinaryStream();
            char[] buffer = new char[stream.available()];
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            reader.read(buffer);
            return new String(buffer);
        }catch (IOException | SQLException exception){
            throw new EntityConvertException(exception);
        }
    }
}

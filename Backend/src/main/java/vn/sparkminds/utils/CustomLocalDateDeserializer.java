package vn.sparkminds.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.util.StdConverter;

public class CustomLocalDateDeserializer extends StdConverter<String, LocalDate> {

    @Override
    public LocalDate convert(String arg0) {
        // TODO Auto-generated method stub
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(arg0, formatter);
    }

}

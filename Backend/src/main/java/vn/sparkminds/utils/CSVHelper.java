package vn.sparkminds.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.dto.request.AddBookRequest;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = {"title", "description", "price", "discounted_price",
            "discount_persent", "quantity", "image_url"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }



    public static List<AddBookRequest> csvBooks(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<AddBookRequest> bookReqList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                AddBookRequest bookReq = new AddBookRequest();

                bookReq.setTitle(csvRecord.get("title"));
                bookReq.setDescription(csvRecord.get("description"));
                bookReq.setPrice(Double.parseDouble(csvRecord.get("price")));
                bookReq.setDiscountedPrice(Double.parseDouble(csvRecord.get("discountedPrice")));
                bookReq.setDiscountPersent(Double.parseDouble(csvRecord.get("discountPersent")));
                bookReq.setTotalBook(Integer.parseInt(csvRecord.get("quantity")));
                bookReq.setImageUrl(csvRecord.get("image_url"));
                bookReq.setAuthorId(Long.parseLong(csvRecord.get("authorId")));
                bookReq.setCategoryId(Long.parseLong(csvRecord.get("categoryId")));
                bookReq.setPublisherId(Long.parseLong(csvRecord.get("publisherId")));
                bookReqList.add(bookReq);
            }
            return bookReqList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}

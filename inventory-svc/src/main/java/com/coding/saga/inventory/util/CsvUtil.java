package com.coding.saga.inventory.util;

import com.coding.saga.inventory.ItemDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CsvUtil {
    public static List<ItemDto> readCsv(InputStream is) {
        try (CSVParser parser = CSVParser.parse(is, StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
            final List<CSVRecord> records = parser.getRecords();
            return records.stream()
                          .skip(1)
                          .map(r ->
                                  new ItemDto(
                                          r.get(0), r.get(1),
                                          BigDecimal.valueOf(Double.parseDouble(r.get(2))),
                                          Integer.parseInt(r.get(3))
                                  )
                          ).collect(toList());
        } catch (IOException ex) {
            log.error("Failed to parse CSV file");
            ex.printStackTrace();
            throw new IllegalArgumentException("Cannot read this CSV file");
        }
    }
}

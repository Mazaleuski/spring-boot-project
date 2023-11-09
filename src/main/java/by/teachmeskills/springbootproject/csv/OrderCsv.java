package by.teachmeskills.springbootproject.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderCsv {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private int price;

    @CsvDate("yyyy-MM-dd")
    @CsvBindByName
    private LocalDate date;

    @CsvBindByName
    private int userId;

    @CsvBindByName
    private String productsName;
}

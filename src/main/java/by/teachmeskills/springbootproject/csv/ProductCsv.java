package by.teachmeskills.springbootproject.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductCsv {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private int price;

    @CsvBindByName(column = "category_id")
    private int categoryId;

    @CsvBindByName
    private String imagePath;
}

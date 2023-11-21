package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams {
    private String searchKey;
    private int priceTo;
    private int priceFrom;
    private String categoryName;
}

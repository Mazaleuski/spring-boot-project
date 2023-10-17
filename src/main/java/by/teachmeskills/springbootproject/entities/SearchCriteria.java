package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCriteria {
    private String searchString;
    private int paginationNumber;
}

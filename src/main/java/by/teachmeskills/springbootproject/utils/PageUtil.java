package by.teachmeskills.springbootproject.utils;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public final class PageUtil {
    public static ModelMap addAttributesFromPage(Page<? extends BaseEntity> page, int pageNumber, int pageSize) {
        ModelMap modelParam = new ModelMap();
        List<Integer> pageNumbers = IntStream.rangeClosed(0, page.getTotalPages() - 1)
                .boxed()
                .collect(Collectors.toList());
        modelParam.addAttribute("totalPages", pageNumbers);
        modelParam.addAttribute("pageNumber", pageNumber);
        modelParam.addAttribute("pageSize", pageSize);
        modelParam.addAttribute("numMov", page.getTotalPages() - 1);
        modelParam.addAttribute("penNumMov", page.getTotalPages() - 2);
        return modelParam;
    }
}

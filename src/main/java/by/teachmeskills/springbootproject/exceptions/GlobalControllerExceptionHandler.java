package by.teachmeskills.springbootproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.ERROR;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.ERROR_PAGE;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView handleAuthException(Exception e) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR, e.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName(ERROR_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CartIsEmptyException.class)
    public ModelAndView handleCartException(Exception e) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR, e.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName(ERROR_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }
}

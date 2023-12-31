package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.utils.UserPrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.CART;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCT_ID;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.CART_PAGE;

@RestController
@RequestMapping("/cart")
@SessionAttributes(CART)
@AllArgsConstructor
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/open")
    public ModelAndView openCartPage() {
        return new ModelAndView(CART_PAGE.getPath());
    }

    @GetMapping("/add")
    public ModelAndView addProductToCart(@RequestParam(PRODUCT_ID) int id, @ModelAttribute(CART) Cart cart) {
        return cart.addProductToCart(productService.findById(id), cart);
    }

    @GetMapping("/delete")
    public ModelAndView removeProductFromCart(@RequestParam(PRODUCT_ID) int id, @ModelAttribute(CART) Cart cart) {
        return cart.removeProductFromCart(id, cart);
    }

    @GetMapping("/order")
    public ModelAndView makeOrder(@ModelAttribute(CART) Cart cart) throws CartIsEmptyException, AuthorizationException {
        User user = userService.findByEmail(UserPrincipalUtil.getEmail());
        return orderService.create(user, cart);
    }

    @ModelAttribute(CART)
    public Cart shoppingCart() {
        return new Cart();
    }
}

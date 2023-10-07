package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.CART;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCT;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.PRODUCT_PAGE;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductRepository productRepository;

    public ModelAndView addProductToCart(int productId, Cart cart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.findById(productId);
        cart.addProduct(product);
        modelParams.addAttribute(CART, cart);
        modelParams.addAttribute(PRODUCT, product);

        return new ModelAndView(PRODUCT_PAGE.getPath(), modelParams);
    }

    public ModelAndView removeProductFromCart(int productId, Cart cart) {
        ModelMap modelParams = new ModelMap();

        cart.removeProduct(productId);
        modelParams.addAttribute(CART, cart);

        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }
}

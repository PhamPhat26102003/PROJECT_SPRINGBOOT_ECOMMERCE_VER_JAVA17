package projectspringboot.library.service;

import projectspringboot.library.model.Accessories;
import projectspringboot.library.model.Customer;
import projectspringboot.library.model.Laptop;
import projectspringboot.library.model.ShoppingCart;

public interface IShoppingCartService {
    //Laptop
    ShoppingCart addItemToCart(Laptop laptop, int quantity, Customer customer);

    ShoppingCart updateItemInCart(Laptop laptop, int quantity, Customer customer);

    ShoppingCart deleteItemInCart(Laptop laptop, Customer customer);

}

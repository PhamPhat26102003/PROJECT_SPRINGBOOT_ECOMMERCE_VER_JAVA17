package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspringboot.library.model.*;
import projectspringboot.library.repository.ICartItemRepository;
import projectspringboot.library.repository.IShoppingCartRepository;
import projectspringboot.library.service.IShoppingCartService;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartService implements IShoppingCartService {
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Override
    public ShoppingCart addItemToCart(Laptop laptop, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();

        if(cart == null){
            cart = new ShoppingCart();
        }

        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, laptop.getId());
        if(cartItems == null){
            cartItems = new HashSet<>();
            if(cartItem == null){
                cartItem = new CartItem();
                cartItem.setLaptop(laptop);
                cartItem.setTotalPrice(quantity * laptop.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        }else{
            if(cartItem == null){
                cartItem = new CartItem();
                cartItem.setLaptop(laptop);
                cartItem.setTotalPrice(quantity * laptop.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }else{
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * laptop.getCostPrice()));
                cartItemRepository.save(cartItem);
            }
        }
        cart.setCartItem(cartItems);

        int totalItem = totalItem(cart.getCartItem());
        double totalPrice = totalPrice(cart.getCartItem());

        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setCustomer(customer);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemInCart(Laptop laptop, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItem();

        CartItem cartItem = findCartItem(cartItems, laptop.getId());
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * laptop.getCostPrice());
        cartItemRepository.save(cartItem);

        int totalItems = totalItem(cartItems);
        double totalPrice = totalPrice(cartItems);

        cart.setTotalItem(totalItems);
        cart.setTotalPrice(totalPrice);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart deleteItemInCart(Laptop laptop, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItem();

        CartItem cartItem = findCartItem(cartItems, laptop.getId());
        cartItems.remove(cartItem);

        cartItemRepository.delete(cartItem);

        int totalItem = totalItem(cartItems);
        double totalPrice = totalPrice(cartItems);

        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setCartItem(cartItems);
        return shoppingCartRepository.save(cart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long id){
        if(cartItems == null){
            return null;
        }
        CartItem cartItem = null;
        for(CartItem item : cartItems){
            if(item.getLaptop().getId() == id){
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItem(Set<CartItem> cartItems){
        int totalItem = 0;
        for(CartItem item : cartItems){
            totalItem += item.getQuantity();
        }
        return totalItem;
    }

    private double totalPrice(Set<CartItem> cartItems){
        double totalPrice = 0.0;
        for(CartItem item : cartItems){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}

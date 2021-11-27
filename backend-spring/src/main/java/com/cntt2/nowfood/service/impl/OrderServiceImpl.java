package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.common.Constants;
import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.Order;
import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.order.CartDto;
import com.cntt2.nowfood.dto.order.FeeOrder;
import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.dto.order.OrderSearchDto;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.OrderMapper;
import com.cntt2.nowfood.repository.OrderRepository;
import com.cntt2.nowfood.repository.ProductRepository;
import com.cntt2.nowfood.repository.UserRepository;
import com.cntt2.nowfood.service.OrderService;
import com.cntt2.nowfood.utils.CommonUtils;
import com.cntt2.nowfood.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:32 PM
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements
        OrderService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Value("${order.ghn.token}")
    private String tokenGHN;

    @Override
    @Transactional
    public OrderDto checkout(CartDto form) {
        Order entity = orderMapper.toEntity(form);
        // add product check quantity
        Set<Integer> ids = form.getCartItems()
                .stream()
                .map(CartDto.CartItemDto::getProductId)
                .collect(Collectors.toSet());
        Set<Product> products = productRepository.findByIds(ids);
        // set shop order, get shop from first product
        Product firstProduct = products.stream().findFirst().orElseThrow(()->new ValidException("Sản phẩm không hợp lệ!"));
        Shop shop = firstProduct.getShop();
        entity.setShop(shop);
        // set Order detail
        form.getCartItems().forEach(item -> {
            Product product = products.stream().
                    filter(x -> x.getId().equals(item.getProductId())).
                    findFirst().orElseThrow(() ->
                        new ValidException("Sản phẩm id= " + item.getProductId() + " không hợp lệ !")
            );
            if(product.getIsSoldOut())
                new ValidException("Sản phẩm " + product.getName() + " đã hết hàng !");
            if (!shop.getId().equals(product.getShop().getId()))
                throw new ValidException("Các sản phẩm trong giỏ hàng không cùng 1 shop!");
            entity.addOrderDetail(product, item.getQuantity());
            // set totalOrder
            Integer totalOrder = product.getTotalOrder()+item.getQuantity();
            product.setTotalOrder(totalOrder);
        });
        // set payment status
        entity.setPaymentStatus(Constants.PaymentStatus.UNPAID);
        // set order status
        entity.setOrderStatus(Constants.OrderStatus.PENDING);
        // set Customer order
        UserPrincipal user = SecurityUtils.getCurrentUser();
        if(user == null) new ValidException("User chưa đăng nhập");
        entity.setCustomer(userRepository.findByUsername(user.getUsername()));
        // set fee order
        entity.setShipPrice(getFeeOrder(entity).getData().getTotal());
        // set total order
        entity.setTotalPrice(entity.getTotalPrice());
        Order result = orderRepository.save(entity);
        return orderMapper.toDto(result);
    }

    @Override
    public FeeOrder getFeeOrder(Order order) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = new HashMap<>();
        map.put("service_id", 53321);
        map.put("insurance_value", order.getTotalPrice().intValue());
        map.put("coupon", null);
        map.put("from_district_id", 1542);
        map.put("to_district_id", 1444);
        map.put("to_ward_code", 20314);
        map.put("height", 15);
        map.put("length", 15);
        map.put("weight", 1000);
        map.put("width", 15);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("token",tokenGHN);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map,httpHeaders);
        String uri = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
        ResponseEntity<FeeOrder> response =  restTemplate.postForEntity(uri,request, FeeOrder.class);
        return response.getBody();
    }

    @Override
    public Page<OrderDto> findByAdvSearch(OrderSearchDto form) {
        Pageable pageable = CommonUtils.getPageRequest(form);
        Page<Order> ordes = orderRepository.findByAdvSearch(form,pageable);
        return ordes.map(orderMapper::toDto);
    }

    @Override
    public OrderDto findById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new ValidException("Không tìm thấy đơn hàng!"));
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto approve(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new ValidException("Không tìm thấy đơn hàng!"));
        if(order.getOrderStatus() != Constants.OrderStatus.PENDING){
            throw new ValidException("Đơn hàng không ở trạng thái chờ xác nhận !");
        }
        order.setOrderStatus(Constants.OrderStatus.CONFIRMED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
    @Override
    public OrderDto reject(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new ValidException("Không tìm thấy đơn hàng!"));
        if(order.getOrderStatus() != Constants.OrderStatus.PENDING){
            throw new ValidException("Đơn hàng không ở trạng thái chờ xác nhận !");
        }
        order.setOrderStatus(Constants.OrderStatus.CANCELED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}

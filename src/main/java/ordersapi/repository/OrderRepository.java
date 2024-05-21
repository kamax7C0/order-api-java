package ordersapi.repository;

import ordersapi.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ProductOrder, Long> {
}

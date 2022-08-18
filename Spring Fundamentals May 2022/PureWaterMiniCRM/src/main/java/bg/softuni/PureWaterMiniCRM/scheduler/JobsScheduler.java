package bg.softuni.PureWaterMiniCRM.scheduler;

import bg.softuni.PureWaterMiniCRM.services.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JobsScheduler {

    private final OrderService orderService;

    public JobsScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 30, initialDelay = 30)
    public void checkExpiryDateOfOrdersAndDelete() {

        this.orderService.deleteOverDueOrders();

    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 30, initialDelay = 30)
    public void checkOrdersAndComplete() {

        this.orderService.completeOrders();

    }
}

package cn.freeexchange.member.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.freeexchange.common.base.event.EventSourcingEnum;

@Configuration
public class RabbitConfig {
	
	
	@Bean
    public Queue UserEventQueue() {
        return new Queue(EventSourcingEnum.USER_EVENT_SOURCING.getCode(), true); // true表示持久化该队列
    }
	
	@Bean
    public Queue MemeberEventQueue() {
        return new Queue(EventSourcingEnum.ACCOUNT_EVENT_SOURCING.getCode(), true); // true表示持久化该队列
    }
}

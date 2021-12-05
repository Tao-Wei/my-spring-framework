package com.tw.springframework.context;

import com.tw.springframework.config.ListableBeanFactory;
import com.tw.springframework.event.ApplicationEventPublisher;

public interface ApplicationContext extends ListableBeanFactory , ApplicationEventPublisher {

}

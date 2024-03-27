package ru.mts.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mts.service.CreateAnimalServiceImpl;

@Component
@Scope("prototype")
public class CreateAnimalServiceBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CreateAnimalServiceImpl) {
            CreateAnimalServiceImpl animalService = (CreateAnimalServiceImpl) bean;
            animalService.defineTypeOfAnimals();
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

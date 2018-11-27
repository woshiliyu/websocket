package com.example.websocet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EchartsController {

    @RequestMapping(value = "/test" ,method = RequestMethod.POST )
    public List<Product> test(){
        List<Product> list = new ArrayList<Product>();

        //这里把“类别名称”和“销量”作为两个属性封装在一个Product类里，每个Product类的对象都可以看作是一个类别（X轴坐标值）与销量（Y轴坐标值）的集合
        list.add(new Product("衬衣", 10));
        list.add(new Product("短袖", 20));
        list.add(new Product("大衣", 30));

       return list;
    }
}

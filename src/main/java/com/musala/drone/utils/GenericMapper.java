package com.musala.drone.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class GenericMapper <T, V>{
    @Autowired
   private ModelMapper modelMapper = new ModelMapper();
   public V mapForward(T t, Class<V> v)
   {
       return modelMapper.map(t, v);
   }
    public T mapReverse(V v, Class<T> t)
    {
        return modelMapper.map(v, t);
    }
}

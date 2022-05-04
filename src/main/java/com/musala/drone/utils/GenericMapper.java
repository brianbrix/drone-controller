package com.musala.drone.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class GenericMapper <T, V>{
   private final ModelMapper modelMapper;

   public GenericMapper()
    {
        this.modelMapper= new ModelMapper();
        this.modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }
   public V mapForward(T t, Class<V> v)
   {

       return this.modelMapper.map(t, v);
   }
    public T mapReverse(V v, Class<T> t)
    {
        return this.modelMapper.map(v, t);
    }
}

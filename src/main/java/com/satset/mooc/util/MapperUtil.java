package com.satset.mooc.util;

import org.modelmapper.ModelMapper;

public class MapperUtil {

    private static ModelMapper modelMapper;

    private MapperUtil() {
    }

    public static ModelMapper getInstance() {
        if(modelMapper==null) {
            synchronized (ModelMapper.class) {
                if(modelMapper == null) modelMapper = new ModelMapper();
            }
        }
        return modelMapper;
    }

}

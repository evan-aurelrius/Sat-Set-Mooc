package com.satset.mooc.util;

import org.modelmapper.ModelMapper;

public class ModelMapperInstance {

    private static ModelMapper modelMapper;

    public static synchronized ModelMapper getInstance() {
        if(modelMapper==null) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    private ModelMapperInstance() {}

}

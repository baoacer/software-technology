package com.example.jwt.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.jwt.dto.model.ImageDto;
import com.example.jwt.entities.ImagesEntity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ImageMapper {
    private ModelMapper mapper;

    public ImageDto mapToDto(ImagesEntity image) {
        return mapper.map(image, ImageDto.class);
    }
}

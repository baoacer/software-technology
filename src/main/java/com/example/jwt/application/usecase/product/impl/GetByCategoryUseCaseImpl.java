package com.example.jwt.application.usecase.product.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.product.GetByCategoryUseCase;
import com.example.jwt.dto.mapper.ImageMapper;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.response.ObjectResponse;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.ProductRepository;
import com.example.jwt.utils.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetByCategoryUseCaseImpl implements GetByCategoryUseCase {

    private final ProductRepository productRepository;
    private final ImageMapper imageMapper;
    private final Logger logger = LoggerFactory.getLogger(GetByCategoryUseCaseImpl.class);

    @Override
    public ObjectResponse<ProductDto> execute(int pageNumber, int pageSize, String sortBy, String slug) {
        try {
            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

            Page<ProductEntity> pagedResult = this.productRepository.findByCategory(slug, paging);

            List<ProductDto> content = pagedResult
                    .getContent()
                    .stream()
                    .map(product -> ProductDto.builder()
                            .id(product.getId())
                            .category(product.getCategoryEntity().getName())
                            .tags(product.getTags())
                            .title(product.getTitle())
                            .slug(product.getSlug())
                            .vendor(product.getVendorEntity().getName())
                            .price(product.getPrice())
                            .available(product.getInventory().getQuantity() > AppConstants.LIMIT_OUT_OF_STOCK)
                            .images(product
                                    .getImages()
                                    .stream()
                                    .filter(image -> image.getPosition() == AppConstants.FIRST_IMAGE_POSITION)
                                    .map(t -> this.imageMapper.mapToDto(t)).toList())
                            .build())
                    .collect(Collectors.toList());

            ObjectResponse<ProductDto> objectResponse = new ObjectResponse<>();
            objectResponse.setContent(content);
            objectResponse.setPageNo(pagedResult.getNumber());
            objectResponse.setPageSize(pagedResult.getSize());
            objectResponse.setTotalElements(pagedResult.getTotalElements());
            objectResponse.setTotalPages(pagedResult.getTotalPages());
            objectResponse.setLast(pagedResult.isLast());

            return objectResponse;
        } catch (Exception e) {
            this.logger.error("Error occurred while retrieving products by category", e);
            throw new RuntimeException(e);
        }
    }
}

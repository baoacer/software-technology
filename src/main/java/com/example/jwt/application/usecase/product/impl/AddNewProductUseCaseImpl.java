package com.example.jwt.application.usecase.product.impl;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.inventory.CreateInventoryUseCase;
import com.example.jwt.application.usecase.product.AddNewProductUseCase;
import com.example.jwt.dto.model.ImageDto;
import com.example.jwt.dto.model.InventoryDto;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.request.CreateInventoryRequest;
import com.example.jwt.dto.request.CreateProductRequest;
import com.example.jwt.entities.*;
import com.example.jwt.infra.repositories.*;
import com.example.jwt.utils.InventoryStatus;
import com.example.jwt.utils.SlugUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AddNewProductUseCaseImpl implements AddNewProductUseCase {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;
    private final ImageRepository imageRepository;
    private final CreateInventoryUseCase createInventoryUseCase;

    @Override
    public ProductDto execute(CreateProductRequest createProductRequest) {
        try {
            ProductEntity product = new ProductEntity();

            CategoryEntity category = this.categoryRepository.findById(createProductRequest.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            VendorEntity vendor = this.vendorRepository.findById(createProductRequest.getVendorId())
                    .orElseThrow(() -> new NotFoundException("Vendor Not Found"));

            Byte maxPosition = this.imageRepository.getMaxPosition();

            List<ImagesEntity> images = createProductRequest.getImageUrls().stream()
                    .map(url -> ImagesEntity.builder()
                            .src(url)
                            .alt(SlugUtil.toSlug(url))
                            .position((byte) (maxPosition != null ? maxPosition + 1 : 1))
                            .product(product)
                            .build()
                    ).toList();

            product.setTitle(createProductRequest.getTitle());
            product.setDescription(createProductRequest.getDescription());
            product.setTags(createProductRequest.getTags());
            product.setPrice(createProductRequest.getPrice());
            product.setCategoryEntity(category);
            product.setVendorEntity(vendor);
            product.setSlug(SlugUtil.toSlug(createProductRequest.getTitle()));
            product.setImages(images);

            ProductEntity saveProduct = this.productRepository.save(product);

            // Inventory
            CreateInventoryRequest request = new CreateInventoryRequest();
            request.setProductId(saveProduct.getId());
            request.setQuantity((short) 0);
            request.setStatus(InventoryStatus.OUT_OF_STOCK);
            InventoryDto inventoryDto = this.createInventoryUseCase.execute(request);

            List<ImageDto> imageDtos = saveProduct.getImages().stream().map(
                    img -> ImageDto.builder()
                            .id(img.getId())
                            .src(img.getSrc())
                            .alt(img.getAlt())
                            .position(img.getPosition())
                            .build()
            ).toList();

            return ProductDto.builder()
                    .id(saveProduct.getId())
                    .title(saveProduct.getTitle())
                    .price(saveProduct.getPrice())
                    .description(saveProduct.getDescription())
                    .slug(saveProduct.getSlug())
                    .tags(saveProduct.getTags())
                    .category(saveProduct.getCategoryEntity().getName())
                    .vendor(saveProduct.getVendorEntity().getName())
                    .inventory(inventoryDto)
                    .images(imageDtos)
                    .build();
        } catch (RuntimeException e) {
            System.out.println("Add New Product Error: " + e.getMessage());
            return null;
        }
    }
}

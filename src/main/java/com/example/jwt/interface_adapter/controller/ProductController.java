package com.example.jwt.interface_adapter.controller;

import java.util.List;

import com.example.jwt.application.usecase.product.*;
import com.example.jwt.dto.request.CreateProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jwt.application.usecase.category.GetAllCategoryUseCase;
import com.example.jwt.dto.model.CategoryDto;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.model.SearchResponseDto;
import com.example.jwt.dto.request.UpdateProductRequest;
import com.example.jwt.dto.response.ObjectResponse;
import com.example.jwt.utils.AppConstants;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private GetAllCategoryUseCase getAllCategoryUseCase;
    private GetByCategoryUseCase getByCategoryUseCase;
    private GetProductByIdUseCase getProductByIdUseCase;
    private SearchProductUseCase searchProductUseCase;
    private UpdateProductUseCase updateProductUseCase; // Thêm UpdateProductUseCase
    private AddNewProductUseCase addNewProductUseCase;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest request){
        ProductDto productDto = this.addNewProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @GetMapping
    public ResponseEntity<ObjectResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "category", required = false) String category) {
        return new ResponseEntity<>(this.getByCategoryUseCase.execute(pageNo, pageSize, sortBy, category),
                HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(this.getAllCategoryUseCase.execute(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable short id) {
        return new ResponseEntity<>(this.getProductByIdUseCase.execute(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResponseDto>> searchProduct(@RequestBody String query) {
        return new ResponseEntity<>(this.searchProductUseCase.execute(query), HttpStatus.OK);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getProductSuggestions() {
        return new ResponseEntity<>(List.of("laptop-gaming", "chuot", "ban-phim", "vo-may-tinh", "man-hinh"),
                HttpStatus.OK);
    }

    // Thêm route để cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable short id, @RequestBody UpdateProductRequest updateProductRequest) {
        // Gọi use case để cập nhật sản phẩm
        this.updateProductUseCase.execute(id, updateProductRequest);

        // Trả về response với mã trạng thái 204 (NO_CONTENT) để chỉ ra rằng cập nhật thành công
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
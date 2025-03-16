package com.example.jwt.interface_adapter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.category.GetAllCategoryUseCase;
import com.example.jwt.application.usecase.product.GetByCategoryUseCase;
import com.example.jwt.application.usecase.product.GetProductByIdUseCase;
import com.example.jwt.application.usecase.product.SearchProductUseCase;
import com.example.jwt.dto.model.CategoryDto;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.model.SearchResponseDto;
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

    @GetMapping("search")
    public ResponseEntity<List<SearchResponseDto>> searchProduct(@RequestBody String query) {
        return new ResponseEntity<>(this.searchProductUseCase.execute(query), HttpStatus.OK);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getProductSuggestions() {
        return new ResponseEntity<>(List.of("laptop-gaming", "chuot", "ban-phim", "vo-may-tinh", "man-hinh"),
                HttpStatus.OK);
    }
}

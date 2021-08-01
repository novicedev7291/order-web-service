package com.coding.saga.inventory.controller;

import com.coding.saga.inventory.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @PostMapping(value = "/bulk", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> bulkInsert(@RequestPart MultipartFile file) throws IOException {
        service.bulkAdd(file.getInputStream());
        return ResponseEntity.ok().build();
    }
}

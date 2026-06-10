package com.SocietyManagementSystem.SocietyManagementSystem_Backend.block;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/block")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BlockResponse>> getAllBlock() {
        return ResponseEntity.ok(blockService.getAllBlock());
    }

    @GetMapping("/{blockId}")
    public ResponseEntity<BlockResponse> getBlockDetailById(
            @PathVariable Long blockId) {

        return ResponseEntity.ok(blockService.getBlockDetails(blockId));
    }

    @PostMapping("/add")
    public ResponseEntity<BlockResponse> addBlock(
            @RequestBody BlockRequest blockRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(blockService.addBlock(blockRequest));
    }

    @PutMapping("/update/{blockId}")
    public ResponseEntity<BlockResponse> updateBlock(
            @PathVariable Long blockId,
            @RequestBody BlockRequest blockRequest) {

        return ResponseEntity.ok(
                blockService.updateBlock(blockId, blockRequest)
        );
    }

    @DeleteMapping("/delete/{blockId}")
    public ResponseEntity<String> deleteBlock(
            @PathVariable Long blockId) {

        return ResponseEntity.ok(
                blockService.deleteBlock(blockId)
        );
    }
}

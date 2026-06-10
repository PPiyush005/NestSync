package com.SocietyManagementSystem.SocietyManagementSystem_Backend.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public BlockResponse getBlockDetails(long blockId) {

        Blocks block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block Not Found"));

        return new BlockResponse(
                block.getBlockId(),
                block.getBlockName(),
                block.getTotalBuildings(),
                block.getBlockType(),
                block.getSecretaryName()
        );
    }

    public List<BlockResponse> getAllBlock() {

        return blockRepository.findAll()
                .stream()
                .map(block -> new BlockResponse(
                        block.getBlockId(),
                        block.getBlockName(),
                        block.getTotalBuildings(),
                        block.getBlockType(),
                        block.getSecretaryName()
                ))
                .toList();
    }

    public BlockResponse addBlock(BlockRequest blockRequest) {

        Blocks block = new Blocks();

        block.setBlockName(blockRequest.getBlock_name());
        block.setBlockType(blockRequest.getBlock_type());
        block.setTotalBuildings(blockRequest.getTotal_building());
        block.setSecretaryName(blockRequest.getSecretary_name());

        Blocks savedBlock = blockRepository.save(block);

        return new BlockResponse(
                savedBlock.getBlockId(),
                savedBlock.getBlockName(),
                savedBlock.getTotalBuildings(),
                savedBlock.getBlockType(),
                savedBlock.getSecretaryName()
        );
    }

    public BlockResponse updateBlock(Long blockId, BlockRequest blockRequest) {

        Blocks block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block Not Found"));

        block.setBlockName(blockRequest.getBlock_name());
        block.setBlockType(blockRequest.getBlock_type());
        block.setTotalBuildings(blockRequest.getTotal_building());
        block.setSecretaryName(blockRequest.getSecretary_name());

        Blocks updatedBlock = blockRepository.save(block);

        return new BlockResponse(
                updatedBlock.getBlockId(),
                updatedBlock.getBlockName(),
                updatedBlock.getTotalBuildings(),
                updatedBlock.getBlockType(),
                updatedBlock.getSecretaryName()
        );
    }

    public String deleteBlock(Long blockId) {

        Blocks block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block Not Found"));

        blockRepository.delete(block);

        return "Block deleted successfully";
    }
}

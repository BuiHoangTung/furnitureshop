package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.*;
import com.myproject.furnitureshop.dto.response.CategoryCreationResponse;
import com.myproject.furnitureshop.dto.response.CategoryFileUploadResponse;
import com.myproject.furnitureshop.dto.response.CategoryInfoUpdateResponse;
import com.myproject.furnitureshop.dto.response.CategoryNode;
import com.myproject.furnitureshop.entity.CategoryEntity;
import com.myproject.furnitureshop.enums.BusinessDirectoryPath;
import com.myproject.furnitureshop.enums.CategoryLevel;
import com.myproject.furnitureshop.enums.CategoryStatus;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.mapper.CategoryMapper;
import com.myproject.furnitureshop.repository.CategoryRepository;
import com.myproject.furnitureshop.service.CategoryService;
import com.myproject.furnitureshop.service.FileStorageService;
import com.myproject.furnitureshop.transactions.event.FileDeleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository,
                              FileStorageService fileStorageService,
                              ApplicationEventPublisher applicationEventPublisher,
                              CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.categoryMapper = categoryMapper;
    }

    private CategoryEntity findCategoryById(long id) {
        return this.categoryRepository.findCategoryEntityById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAT_NOT_FOUND));
    }

    private CategoryEntity getParent(String parentName, CategoryLevel childLevel) {
        if(childLevel == CategoryLevel.ROOT) {
            return null;
        }

        if(parentName == null || parentName.isBlank())
            throw new AppException(ErrorCode.CAT_PARENT_REQUIRED);

        CategoryEntity parent = this.categoryRepository.findCategoryEntityByName(parentName)
                .orElseThrow(() -> new AppException(ErrorCode.CAT_PARENT_NOT_FOUND));

        if(parent.getLevel().getScore() != childLevel.getScore() + 1) {
            throw new AppException(ErrorCode.CAT_INVALID_PARENT_LEVEL);
        }

        return parent;
    }

    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    @Transactional
    @Override
    public CategoryCreationResponse createCategory(CategoryCreationRequest request) {
        if(this.categoryRepository.existsCategoryEntityByName(request.name())) {
            throw new AppException(ErrorCode.CAT_ALREADY_EXISTS);
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryLevel categoryLevel = CategoryLevel.valueOf(request.level());
        CategoryStatus status = categoryLevel == CategoryLevel.LEAF
                ? CategoryStatus.DRAFT
                : CategoryStatus.ACTIVE;
        CategoryEntity parentEntity = this.getParent(request.parentName(), categoryLevel);

        categoryEntity.setName(request.name());
        categoryEntity.setLevel(categoryLevel);
        categoryEntity.setParent(parentEntity);
        categoryEntity.setStatus(status);

        categoryEntity = this.categoryRepository.save(categoryEntity);

        return CategoryCreationResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .level(categoryEntity.getLevel().name())
                .parentName(
                        Optional.ofNullable(categoryEntity.getParent())
                                .map(CategoryEntity::getName)
                                .orElse(null)
                )
                .build();
    }

    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    @Override
    public CategoryFileUploadResponse uploadCategoryFile(CategoryFileUploadRequest request) {
        CategoryEntity categoryEntity = this.findCategoryById(request.categoryId());

        if(CategoryLevel.LEAF != categoryEntity.getLevel()) {
            throw new AppException(ErrorCode.CAT_INVALID_LEVEL);
        }

        String bannerImageName = this.fileStorageService
                .saveFile(request.banner(), BusinessDirectoryPath.CATEGORY_DIRECTORY_PATH).getUrl();

        String thumbnailImageName = this.fileStorageService
                .saveFile(request.thumbnail(), BusinessDirectoryPath.CATEGORY_DIRECTORY_PATH).getUrl();

        categoryEntity.setBannerImageName(bannerImageName);
        categoryEntity.setThumbnailImageName(thumbnailImageName);
        categoryEntity.setStatus(CategoryStatus.ACTIVE);

        categoryEntity = this.categoryRepository.save(categoryEntity);

        return CategoryFileUploadResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .level(categoryEntity.getLevel().name())
                .banner(categoryEntity.getBannerImageName())
                .thumbnail(categoryEntity.getThumbnailImageName())
                .build();
    }

    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    @Transactional
    @Override
    public CategoryFileUploadResponse updateFile(CategoryFileUpdateRequest request, boolean isBanner) {
        CategoryEntity categoryEntity = this.findCategoryById(request.id());

        if(CategoryLevel.LEAF != categoryEntity.getLevel()) {
            throw new AppException(ErrorCode.CAT_INVALID_LEVEL);
        }

        if(CategoryStatus.ACTIVE != categoryEntity.getStatus()) {
            throw new AppException(ErrorCode.CAT_ONLY_ACTIVE);
        }

        String oldFile = (isBanner)
                ? categoryEntity.getBannerImageName()
                : categoryEntity.getThumbnailImageName();
        String newFile = this.fileStorageService.saveFile(request.file(), BusinessDirectoryPath.CATEGORY_DIRECTORY_PATH).getUrl();

        if(isBanner) {
            categoryEntity.setBannerImageName(newFile);
        } else {
            categoryEntity.setThumbnailImageName(newFile);
        }
        this.categoryRepository.save(categoryEntity);

        if(oldFile != null) {
            this.applicationEventPublisher.publishEvent(new FileDeleteEvent(oldFile));
        }

        return CategoryFileUploadResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .level(categoryEntity.getLevel().name())
                .banner(categoryEntity.getBannerImageName())
                .thumbnail(categoryEntity.getThumbnailImageName())
                .build();
    }

    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    @Override
    public CategoryInfoUpdateResponse updateCategoryInfo(CategoryInfoUpdateRequest request, long id) {
        CategoryEntity categoryToUpdate = this.findCategoryById(id);
        CategoryEntity parent = this.categoryRepository.findCategoryEntityByName(request.parentName())
                .orElseThrow(() -> new AppException(ErrorCode.CAT_PARENT_NOT_FOUND));
        Optional<CategoryEntity> existingCategory = this.categoryRepository.findCategoryEntityByName(request.name());

        if(existingCategory.isPresent() && existingCategory.get().getId() != categoryToUpdate.getId()) {
            throw new AppException(ErrorCode.CAT_ALREADY_EXISTS);
        }

        if(parent.getLevel().getScore() != categoryToUpdate.getLevel().getScore() + 1) {
            throw new AppException(ErrorCode.CAT_INVALID_PARENT_LEVEL);
        }

        categoryToUpdate.setName(request.name());
        categoryToUpdate.setParent(parent);
        categoryToUpdate = this.categoryRepository.save(categoryToUpdate);

        return CategoryInfoUpdateResponse.builder()
                .id(categoryToUpdate.getId())
                .name(categoryToUpdate.getName())
                .level(categoryToUpdate.getLevel().name())
                .bannerImageName(categoryToUpdate.getBannerImageName())
                .thumbnailImageName(categoryToUpdate.getThumbnailImageName())
                .parentName(
                        Optional.ofNullable(categoryToUpdate.getParent())
                                .map(CategoryEntity::getName)
                                .orElse(null)
                )
                .build();
    }

    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    @Transactional
    @Override
    public void softDeleteCategory(long id) {
        CategoryEntity categoryEntity = this.findCategoryById(id);

        if(categoryEntity.getStatus() == CategoryStatus.DELETED) {
            throw new AppException(ErrorCode.CAT_ALREADY_DELETED);
        }

        categoryEntity.setStatus(CategoryStatus.DELETED);

        List<CategoryEntity> children = this.categoryRepository.findAllChildrenCategoryEntity(id);
        children.forEach(child -> {
            if (child.getStatus() != CategoryStatus.DELETED) {
                child.setStatus(CategoryStatus.DELETED);
            }
        });
    }

    @Override
    public void hardDeleteCategory(long id) { // Relate to Products
    }

    @Override
    public void activateCategory(long id) { // Relate to Products

    }

    private CategoryNode dfs(CategoryEntity categoryEntity) {
        if(categoryEntity == null) {
            return null;
        }

        CategoryNode node = this.categoryMapper.toCategoryNode(categoryEntity);

        for(CategoryEntity child : categoryEntity.getChildren()) {
            node.getChildren().add(dfs(child));
        }

        return node;
    }

    private CategoryNode buildOnlyActiveTree(CategoryNode rootNode, List<CategoryEntity> children) {
        Map<Long, CategoryNode> map = new HashMap<>();
        map.put(rootNode.getId(), rootNode);

        for(CategoryEntity child : children) {
            if(child.getStatus() == CategoryStatus.ACTIVE) {
                long parentId = child.getParent().getId();
                if(map.containsKey(parentId)) {
                    CategoryNode childNode = this.categoryMapper.toCategoryNode(child);
                    map.get(parentId).getChildren().add(childNode);
                    map.put(child.getId(), childNode);
                }
            }
        }

        return rootNode;
    }

    @PreAuthorize("hasAuthority('CATEGORY_READ')")
    @Override
    public CategoryNode getCategoryHierarchy(String name) {
        CategoryEntity parentEntity = this.categoryRepository.findCategoryEntityByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.CAT_NOT_FOUND));

        if(parentEntity.getStatus() != CategoryStatus.ACTIVE) {
            throw new AppException(ErrorCode.CAT_NOT_FOUND);
        }

        return this.dfs(parentEntity);
    }

    @Transactional
    @Override
    public CategoryNode getOnlyActiveCategoryHierarchy(String name) {
        CategoryEntity parentEntity = this.categoryRepository.findCategoryEntityByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.CAT_NOT_FOUND));

        if(parentEntity.getStatus() != CategoryStatus.ACTIVE) {
            throw new AppException(ErrorCode.CAT_NOT_FOUND);
        }

        List<CategoryEntity> children = this.categoryRepository.findAllChildrenCategoryEntity(parentEntity.getId());

        return this.buildOnlyActiveTree(this.categoryMapper.toCategoryNode(parentEntity), children);
    }

    @Override
    public List<CategoryNode> getOnlyActiveRootCategories() {
        return this.categoryRepository
                .findCategoryEntitiesByLevel(CategoryLevel.ROOT)
                .stream()
                .filter((category) -> category.getStatus() == CategoryStatus.ACTIVE)
                .map(this.categoryMapper::toCategoryNode)
                .toList();
    }

    @PreAuthorize("hasAuthority('CATEGORY_READ')")
    @Override
    public List<CategoryNode> getCategoriesByLevel(String level) {
        CategoryLevel categoryLevel;

        try {
            categoryLevel = CategoryLevel.valueOf(level);

            return this.categoryRepository.findCategoryEntitiesByLevel(categoryLevel)
                    .stream().map(this.categoryMapper::toCategoryNode)
                    .toList();
        } catch(Exception e) {
            throw new AppException(ErrorCode.CAT_INVALID_LEVEL);
        }
    }
}

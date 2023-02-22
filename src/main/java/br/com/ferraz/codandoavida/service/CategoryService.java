package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.dto.CategoryDTO;
import br.com.ferraz.codandoavida.enums.Status;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Page<Category> findAllActive(CategoryDTO dto, Pageable pageable) {
        return repository.findByStatusAndNameAndCreationUser(Status.ACTIVE, dto.getName(), dto.getCreationUser(), pageable);
    }
}

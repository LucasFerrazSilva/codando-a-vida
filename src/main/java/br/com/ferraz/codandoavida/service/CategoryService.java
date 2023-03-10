package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.dto.CategoryDTO;
import br.com.ferraz.codandoavida.enums.Status;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {

    final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAllActive() {
        return repository.findByStatus(Status.ACTIVE);
    }

    public Page<Category> findAllActive(CategoryDTO dto, Pageable pageable) {
        return repository.findByStatusAndNameAndCreationUser(Status.ACTIVE, dto.getName(), dto.getCreationUser(), pageable);
    }

    public Category findById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(Category obj) {
        repository.save(obj);
    }

    public Category inactivate(Integer id) {
        Category obj = findById(id);
        obj.inactivate();
        save(obj);

        return obj;
    }

    public Category findByName(String categoryName) {
        return repository.findByName(categoryName).orElseThrow();
    }
}

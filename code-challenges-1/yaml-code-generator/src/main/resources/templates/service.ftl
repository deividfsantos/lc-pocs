package ${packageName};

import ${modelPackage}.${entity.name};
import ${repositoryPackage}.${entity.repositoryName()};
import java.util.List;
import java.util.Optional;

public class ${entity.serviceName()} {

    private final ${entity.repositoryName()} repository;

    public ${entity.serviceName()}(${entity.repositoryName()} repository) {
        this.repository = repository;
    }

    public void save(${entity.name} entity) {
        repository.save(entity);
    }

    public List<${entity.name}> findAll() {
        return repository.findAll();
    }

    public Optional<${entity.name}> findFirst() {
        return repository.findFirst();
    }

    public int count() {
        return repository.count();
    }
}


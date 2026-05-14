package ${packageName};

import ${modelPackage}.${entity.name};
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ${entity.repositoryName()} {

    private final List<${entity.name}> store = new ArrayList<>();

    public void save(${entity.name} entity) {
        store.add(entity);
    }

    public List<${entity.name}> findAll() {
        return List.copyOf(store);
    }

    public Optional<${entity.name}> findFirst() {
        return store.stream().findFirst();
    }

    public void deleteAll() {
        store.clear();
    }

    public int count() {
        return store.size();
    }
}


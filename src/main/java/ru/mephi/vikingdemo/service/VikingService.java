package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VikingService {
    // каждый раз при изменении создаётся новая копия списка 
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    @Autowired
    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }
    
    public List<Viking> findAll() {
        return List.copyOf(vikings);
    }

    public Viking createRandomViking() {
        

        Viking viking = vikingFactory.createRandomViking();

        vikings.add(viking);
        return viking;
    }

    public Viking addViking(Viking viking) {
        if (viking == null) {
            throw new IllegalArgumentException("Viking cannot be null");
        }
        vikings.add(viking);
        return viking;
    }

    public boolean deleteViking(int index) {
        if (index >= 0 && index < vikings.size()) {
            vikings.remove(index);
            return true;
        }
        return false;
    }

    public Optional<Viking> updateViking(int index, Viking updatedViking) {
        if (index >= 0 && index < vikings.size() && updatedViking != null) {
            Viking newViking = new Viking(
                    updatedViking.name(),
                    updatedViking.age(),
                    updatedViking.heightCm(),
                    updatedViking.hairColor(),
                    updatedViking.beardStyle(),
                    updatedViking.equipment());
            vikings.set(index, newViking);
            return Optional.of(newViking);
        }
        return Optional.empty();
    }
}

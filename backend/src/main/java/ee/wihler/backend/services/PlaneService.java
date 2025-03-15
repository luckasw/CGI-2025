package ee.wihler.backend.services;

import ee.wihler.backend.entities.Plane;
import ee.wihler.backend.repositories.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaneService {

    @Autowired
    private PlaneRepository planeRepository;

    public Plane getPlaneById(Long id) {
        return planeRepository.findById(id).orElse(null);
    }
}

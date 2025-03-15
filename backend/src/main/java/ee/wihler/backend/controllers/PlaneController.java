package ee.wihler.backend.controllers;

import ee.wihler.backend.entities.Plane;
import ee.wihler.backend.services.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planes")
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    @GetMapping("/{id}")
    public Plane getPlane(@PathVariable Long id) {
        return planeService.getPlaneById(id);
    }
}

package com.example.botanix.controller;

import com.example.botanix.model.Plantas;
import com.example.botanix.repository.PlantasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantas")
public class PlantasController
{
    @Autowired
    private PlantasRepository plantasRepository;

    @GetMapping
    public List<Plantas> getAll()
    {
        return plantasRepository.findAll();
    }

    @GetMapping("/{id}")
    public Plantas getById(@PathVariable Long id)
    {
        return plantasRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Plantas save(@RequestBody Plantas plantas)
    {
        return plantasRepository.save(plantas);
    }

    @PutMapping("/{id}")
    public Plantas update(@PathVariable Long id, @RequestBody Plantas plantas)
    {
        plantas.setId_planta(id);
        return plantasRepository.save(plantas);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id)
    {
        plantasRepository.deleteById(id);
    }
}
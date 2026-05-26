package com.example.botanix.controller;

import com.example.botanix.model.Jardineros;
import com.example.botanix.repository.JardinerosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jardineros")
public class JardinerosController
{
    @Autowired
    private JardinerosRepository jardinerosRepository;

    @GetMapping
    public List<Jardineros> getAll()
    {
        return jardinerosRepository.findAll();
    }

    @GetMapping("/{id}")
    public Jardineros getById(@PathVariable Long id)
    {
        return jardinerosRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Jardineros save(@RequestBody Jardineros jardineros)
    {
        return jardinerosRepository.save(jardineros);
    }

    @PutMapping("/{id}")
    public Jardineros update(@PathVariable Long id, @RequestBody Jardineros jardineros)
    {
        jardineros.setId_jardinero(id);
        return jardinerosRepository.save(jardineros);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id)
    {
        jardinerosRepository.deleteById(id);
    }
}

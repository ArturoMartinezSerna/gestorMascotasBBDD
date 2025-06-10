package com.example.demo.controller;


import com.example.demo.model.Mascota;
import com.example.demo.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "formulario";
    }

    @GetMapping("/crud")
    public String mostrarMascotas(Model model){
        model.addAttribute("mascotas",mascotaRepository.findAll());
        return "crud";
    }

    @PostMapping("/crud")
    public String leerFormulario(@ModelAttribute Mascota mascota, Model model){
        mascotaRepository.save(mascota);
        return "redirect:/crud";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable int id){
        Mascota mascota = mascotaRepository.findById(id).get();
        model.addAttribute("mascota",mascota);
        return "formulario";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(Model model, @PathVariable int id){
        mascotaRepository.deleteById(id);
        return "redirect:/crud";
    }
}

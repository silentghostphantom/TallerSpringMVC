package com.uaemex.mx.features.carrera.controller;
import com.uaemex.mx.features.carrera.model.dto.CarreraDTO;
import com.uaemex.mx.features.carrera.model.dto.CreateOrUpdateCarreraDTO;
import com.uaemex.mx.features.carrera.service.CarreraService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/carreras")
public class CarreraController {
    private final CarreraService carreraService;
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }
    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("carreras", carreraService.listar());
        return "carreras/lista";
    }

    // Inicializar estatus y eliminado y asignarlos al modelo
    @GetMapping("/nuevo")
    public String nuevo(Model modelo) {
        CreateOrUpdateCarreraDTO dto = new CreateOrUpdateCarreraDTO();
        dto.setEstatus(false);
        dto.setEliminado(false);
        modelo.addAttribute("carreraDto", dto);
        return "carreras/carrerasForm";
    }

    // Convierte CarreraDTO a CreateOrUpdateCarreraDTO y agrega el "id" al modelo
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        CarreraDTO c = carreraService.buscarPorId(id);

        CreateOrUpdateCarreraDTO dto = new CreateOrUpdateCarreraDTO();
        dto.setSiglas(c.getSiglas());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setEstatus(c.getEstatus() != null ? c.getEstatus() : true);
        dto.setEliminado(c.getEliminado() != null ? c.getEliminado() : false);

        model.addAttribute("carreraDto", dto);
        model.addAttribute("id", id);  
        return "carreras/carrerasForm";
    }

    // Captura duplicados y los muestra en el form
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("carreraDto") CreateOrUpdateCarreraDTO carreraDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "carreras/carrerasForm";
        }
        try {
            carreraService.guardar(carreraDto);
        } catch (RuntimeException e) {
            String msg = e.getMessage() == null ? "Error al guardar la carrera" : e.getMessage();
            if (msg.toLowerCase().contains("siglas")) {
                bindingResult.rejectValue("siglas", "error.carreraDto", msg);
            } else if (msg.toLowerCase().contains("nombre")) {
                bindingResult.rejectValue("nombre", "error.carreraDto", msg);
            } else {
                bindingResult.reject("error.carreraDto", msg);
            }
            return "carreras/carrerasForm";
        }
        return "redirect:/carreras";
    }

    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("carreraDto") CreateOrUpdateCarreraDTO carreraDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "carreras/carrerasForm";
        }
        try {
            carreraService.actualizar(id, carreraDto);
        } catch (RuntimeException e) {
            String msg = e.getMessage() == null ? "Error al actualizar la carrera" : e.getMessage();
            if (msg.toLowerCase().contains("siglas")) {
                bindingResult.rejectValue("siglas", "error.carreraDto", msg);
            } else if (msg.toLowerCase().contains("nombre")) {
                bindingResult.rejectValue("nombre", "error.carreraDto", msg);
            } else {
                bindingResult.reject("error.carreraDto", msg);
            }
            model.addAttribute("id", id);
            return "carreras/carrerasForm";
        }
        return "redirect:/carreras";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            carreraService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Carrera eliminada correctamente.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/carreras";
    }
}
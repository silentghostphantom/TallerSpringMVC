package com.uaemex.mx.features.alumno.controller;

import com.uaemex.mx.features.alumno.model.dto.AlumnoDTO;
import com.uaemex.mx.features.alumno.model.dto.CreateOrUpdateAlumnoDTO;
import com.uaemex.mx.features.alumno.service.AlumnoService;
import com.uaemex.mx.features.carrera.service.CarreraService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final CarreraService carreraService;

    public AlumnoController(AlumnoService alumnoService,
                            CarreraService carreraService) {
        this.alumnoService = alumnoService;
        this.carreraService = carreraService;
    }

    /* ===================== LISTAR ===================== */
    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("alumnos", alumnoService.listar());
        return "alumnos/lista";
    }

    /* ===================== NUEVO ===================== */
    @GetMapping("/nuevo")
    public String nuevo(Model modelo) {
        CreateOrUpdateAlumnoDTO dto = new CreateOrUpdateAlumnoDTO();
        dto.setEstatus(true);      // activo por defecto
        dto.setEliminado(false);   // no eliminado por defecto
        modelo.addAttribute("alumnoDto", dto);
        modelo.addAttribute("carreras", carreraService.listar());
        return "alumnos/alumnosForm";
    }

    /* ===================== EDITAR (GET) ===================== */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        AlumnoDTO a = alumnoService.buscarPorId(id);

        CreateOrUpdateAlumnoDTO dto = new CreateOrUpdateAlumnoDTO();
        dto.setNombreAlumno(a.getNombreAlumno());
        dto.setPaternoAlumno(a.getPaternoAlumno());
        dto.setMaternoAlumno(a.getMaternoAlumno());
        dto.setEmailAlumno(a.getEmailAlumno());
        dto.setEstatus(a.getEstatus() != null ? a.getEstatus() : true);
        dto.setEliminado(a.getEliminado() != null ? a.getEliminado() : false);
        dto.setIdCarrera(a.getIdCarrera());

        modelo.addAttribute("alumnoDto", dto);
        modelo.addAttribute("id", id);                       // marca modo edición en la vista
        modelo.addAttribute("carreras", carreraService.listar());
        return "alumnos/alumnosForm";
    }

    /* ===================== GUARDAR (POST) ===================== */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("alumnoDto") CreateOrUpdateAlumnoDTO alumnoDto,
                          BindingResult bindingResult,
                          Model modelo) {
        if (bindingResult.hasErrors()) {
            modelo.addAttribute("carreras", carreraService.listar());
            return "alumnos/alumnosForm";
        }
        try {
            alumnoService.guardar(alumnoDto);
        } catch (RuntimeException e) {
            aplicarError(bindingResult, e);
            modelo.addAttribute("carreras", carreraService.listar());
            return "alumnos/alumnosForm";
        }
        return "redirect:/alumnos";
    }

    /* ===================== ACTUALIZAR (POST) ===================== */
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("alumnoDto") CreateOrUpdateAlumnoDTO alumnoDto,
                             BindingResult bindingResult,
                             Model modelo) {
        if (bindingResult.hasErrors()) {
            modelo.addAttribute("id", id);
            modelo.addAttribute("carreras", carreraService.listar());
            return "alumnos/alumnosForm";
        }
        try {
            alumnoService.actualizar(id, alumnoDto);
        } catch (RuntimeException e) {
            aplicarError(bindingResult, e);
            modelo.addAttribute("id", id);
            modelo.addAttribute("carreras", carreraService.listar());
            return "alumnos/alumnosForm";
        }
        return "redirect:/alumnos";
    }

    /* ===================== ELIMINAR ===================== */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            alumnoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Alumno eliminado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/alumnos";
    }

    /* ===================== HELPER ===================== */
    /**
     * Decide a qué campo del formulario asignar el mensaje de error según
     * el texto que lanzó el servicio (email duplicado, carrera inexistente, etc.).
     */
    private void aplicarError(BindingResult bindingResult, RuntimeException e) {
        String msg = e.getMessage() == null ? "Error inesperado" : e.getMessage();
        String lower = msg.toLowerCase();

        if (lower.contains("email")) {
            bindingResult.rejectValue("emailAlumno", "error.alumnoDto", msg);
        } else if (lower.contains("carrera")) {
            bindingResult.rejectValue("idCarrera", "error.alumnoDto", msg);
        } else {
            bindingResult.reject("error.alumnoDto", msg);
        }
    }
}
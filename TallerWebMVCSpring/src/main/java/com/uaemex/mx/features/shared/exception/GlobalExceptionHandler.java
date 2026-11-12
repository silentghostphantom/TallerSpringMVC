package com.uaemex.mx.features.shared.exception;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleGlobalError(Exception ex, Model model) {
        // Enviamos el código de estado para que se muestre en el título: "Error (500)"
        model.addAttribute("status", 500);
        // Un mensaje amigable para el usuario
        model.addAttribute("error", "Error Interno del Servidor");
        // El detalle del error (opcional, útil para debug en el taller)
        model.addAttribute("message", ex.getMessage() != null ? ex.getMessage() : "Ha ocurrido un error inesperado.");
        return "error";
    }
}
// Funciones JavaScript personalizadas para la aplicación
document.addEventListener('DOMContentLoaded', function() {
    // Inicialización de tooltips si se usan
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});
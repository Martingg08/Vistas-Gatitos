package com.gui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Controlador para centralizar la lógica de negocio y acceso a datos
 * 
 * @author Tincho
 */
public class Controlador {
    
    // TODO: Inyectar conexión a BD y DAOs
        
    private Integer idVoluntarioActual;
    
    public Controlador(Integer idVoluntarioActual) {
        this.idVoluntarioActual = idVoluntarioActual;
        // TODO: Inicializar conexión a BD o DAOs
    }
    
    // ==================== MÓDULO: ADMINISTRADOR - VER VOLUNTARIOS ====================

    /**
     * Obtiene la lista de todos los voluntarios
     * 
     * @return Lista de voluntarios con su información básica
     */
    public List<VoluntarioSimple> obtenerVoluntarios() {
        // TODO: BASE DE DATOS - Consultar voluntarios

        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        List<VoluntarioSimple> voluntarios = new ArrayList<>();

        voluntarios.add(new VoluntarioSimple(2, "voluntario1", "vol1@colonia.com", "555-0002", "Av. Principal 123", 4.5, "Muy responsable"));
        voluntarios.add(new VoluntarioSimple(3, "voluntario2", "vol2@colonia.com", "555-0003", "Calle Secundaria 456", 4.0, "Buen desempeño"));
        voluntarios.add(new VoluntarioSimple(7, "maria_vol", "maria@colonia.com", "555-0007", "Boulevard Norte 789", 5.0, "Excelente"));
        voluntarios.add(new VoluntarioSimple(9, "carlos_vol", "carlos@colonia.com", "555-0009", "Pasaje Sur 321", 3.8, null));

        return voluntarios;
    }

    // ==================== CLASES DE DATOS (DTOs) ====================

    /**
     * DTO simple para información de voluntarios
     */
    public static class VoluntarioSimple {
        public final Integer idUsuario;
        public final String nombreUsuario;
        public final String correoElectronico;
        public final String numeroTelefono;
        public final String direccion;
        public final Double reputacion;
        public final String detallesReputacion;

        public VoluntarioSimple(Integer id, String nombre, String correo, String telefono,
                               String direccion, Double reputacion, String detalles) {
            this.idUsuario = id;
            this.nombreUsuario = nombre;
            this.correoElectronico = correo != null ? correo : "No especificado";
            this.numeroTelefono = telefono != null ? telefono : "No especificado";
            this.direccion = direccion != null ? direccion : "No especificada";
            this.reputacion = reputacion;
            this.detallesReputacion = detalles;
        }
    }
    
    // ==================== MÓDULO: GATOS ====================
    
    /**
     * Registra un nuevo gato en el sistema
     * 
     * @return ID del gato registrado, o null si falló
     */
    public Integer registrarGato(String nombre, String color, String caracteristicas, String estadoSalud, Integer edadAproximada, String rutaFoto, String codigoQR) {
        // TODO: BASE DE DATOS - Guardar gato
        
        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Gato registrado: " + nombre);
        return new Random().nextInt(1000); // ID simulado
    }
    
    /**
     * Obtiene los datos de un gato específico
     * 
     * @return Datos del gato, o null si no existe
     */
    public DatosGatoResumen obtenerDatosGato(Integer idGato) {
              
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        if (idGato == 1) {
            return new DatosGatoResumen(1, "Michi", "Naranja", 12, "Saludable");
        } else if (idGato == 2) {
            return new DatosGatoResumen(2, "Pelusa", "Blanco", 8, "En recuperación");
        } else if (idGato == 3) {
            return new DatosGatoResumen(3, "Garfield", "Atigrado", 24, "Saludable");
        }
        return null;
    }
    
    /**
     * Obtiene la lista de gatos asignados a familias
     */
    public List<GatoAsignadoInfo> obtenerGatosAsignados() {
        // TODO: BASE DE DATOS - Consultar gatos asignados
                
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        List<GatoAsignadoInfo> lista = new ArrayList<>();
        lista.add(new GatoAsignadoInfo(
            1, "Michi", 1, "Familia González", "Calle Principal 123", "123-456-7890", "Buena", "Adopción Definitiva", LocalDate.of(2025, 10, 15), LocalDate.of(2025, 11, 1)
        ));
        lista.add(new GatoAsignadoInfo(
            2, "Pelusa", 2, "Familia Martínez", "Av. Libertador 456", "098-765-4321", "Excelente", "Tránsito", LocalDate.of(2025, 11, 5), null
        ));
        lista.add(new GatoAsignadoInfo(
            3, "Garfield", 1, "Familia González", "Calle Principal 123", "123-456-7890", "Buena", "Tránsito", LocalDate.of(2025, 11, 10), null
        ));
        return lista;
    }
    
    // ==================== MÓDULO: FAMILIAS ====================
    
    /**
     * Obtiene los datos de una familia específica
     * 
     * @return Datos de la familia, o null si no existe
     */
    public DatosFamiliaResumen obtenerDatosFamilia(Integer idFamilia) {
        // TODO: BASE DE DATOS - Consultar familia
       
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        if (idFamilia == 1) {
            return new DatosFamiliaResumen(1, "Familia González", "Calle Principal 123", 
                                           "123-456-7890", "Activa", "Disponible");
        } else if (idFamilia == 2) {
            return new DatosFamiliaResumen(2, "Familia Martínez", "Av. Libertador 456", 
                                           "098-765-4321", "Activa", "Ocupada temporalmente");
        }
        return null;
    }
    
    // ==================== MÓDULO: ASIGNACIONES ====================
    
    /**
     * Asigna un gato a una familia
     * 
     * @return true si la asignación fue exitosa, false en caso contrario
     */
    public boolean asignarGatoAFamilia(Integer idGato, Integer idFamilia, LocalDate fechaAsignacion, String tipoAsignacion) {
        // TODO: BASE DE DATOS - Guardar asignación
        
        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Asignación registrada: Gato " + idGato + " -> Familia " + idFamilia);
        return true;
    }
    
    // ==================== MÓDULO: VISITAS ====================
    
    /**
     * Registra una visita de seguimiento
     * 
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarVisita(Integer idGato, LocalDate fechaVisita, String estadoGeneral, String observaciones, String sugerencias) {
        // TODO: BASE DE DATOS - Guardar visita
               
        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Visita registrada: Gato " + idGato + " - " + fechaVisita);
        return true;
    }
    
    // ==================== MÓDULO: TAREAS ====================
    
    /**
     * Obtiene las tareas del mes para el voluntario actual
     */
    public Map<LocalDate, List<TareaInfo>> obtenerTareasDelMes(YearMonth mesAnio) {
        // TODO: BASE DE DATOS - Consultar tareas del mes
                
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        Map<LocalDate, List<TareaInfo>> tareas = new HashMap<>();
        
        LocalDate fecha1 = LocalDate.now().withDayOfMonth(5);
        if (fecha1.getMonth() == mesAnio.getMonth() && fecha1.getYear() == mesAnio.getYear()) {
            List<TareaInfo> lista1 = new ArrayList<>();
            lista1.add(new TareaInfo(1, "Revisar estado de salud de Michi", "Revisión", "PENDIENTE", fecha1, "Michi"));
            lista1.add(new TareaInfo(2, "Llevar al veterinario", "Veterinario", "PENDIENTE", fecha1, "Michi"));
            tareas.put(fecha1, lista1);
        }
        
        LocalDate fecha2 = LocalDate.now().withDayOfMonth(10);
        if (fecha2.getMonth() == mesAnio.getMonth() && fecha2.getYear() == mesAnio.getYear()) {
            List<TareaInfo> lista2 = new ArrayList<>();
            lista2.add(new TareaInfo(3, "Alimentación especial", "Alimentación", "FINALIZADA", fecha2, "Pelusa"));
            tareas.put(fecha2, lista2);
        }
        
        LocalDate fecha3 = LocalDate.now().withDayOfMonth(15);
        if (fecha3.getMonth() == mesAnio.getMonth() && fecha3.getYear() == mesAnio.getYear()) {
            List<TareaInfo> lista3 = new ArrayList<>();
            lista3.add(new TareaInfo(4, "Control post-operatorio", "Seguimiento", "PENDIENTE", fecha3, "Garfield"));
            tareas.put(fecha3, lista3);
        }
        
        return tareas;
    }
    
    /**
     * Finaliza una tarea (cambia su estado a FINALIZADA)
     * 
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean finalizarTarea(Integer idTarea) {
        // TODO: BASE DE DATOS - Actualizar estado de tarea
                
        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Tarea finalizada: " + idTarea);
        return true;
    }
    
    // ==================== CLASES DE DATOS (DTOs) ====================
    
    /**
     * DTO para información de gatos asignados a familias
     */
    public static class GatoAsignadoInfo {
        public final Integer idGato;
        public final String nombreGato;
        public final Integer idFamilia;
        public final String nombreFamilia;
        public final String direccionFamilia;
        public final String telefonoFamilia;
        public final String reputacionFamilia;
        public final String tipoAsignacion;
        public final LocalDate fechaAsignacion;
        public final LocalDate fechaUltimaVisita;
        
        public GatoAsignadoInfo(Integer idGato, String nombreGato, Integer idFamilia,
                                String nombreFamilia, String direccionFamilia, String telefonoFamilia,
                                String reputacionFamilia, String tipoAsignacion,
                                LocalDate fechaAsignacion, LocalDate fechaUltimaVisita) {
            this.idGato = idGato;
            this.nombreGato = nombreGato;
            this.idFamilia = idFamilia;
            this.nombreFamilia = nombreFamilia;
            this.direccionFamilia = direccionFamilia;
            this.telefonoFamilia = telefonoFamilia;
            this.reputacionFamilia = reputacionFamilia;
            this.tipoAsignacion = tipoAsignacion;
            this.fechaAsignacion = fechaAsignacion;
            this.fechaUltimaVisita = fechaUltimaVisita;
        }
    }
    
    /**
     * DTO para resumen de datos de gato
     */
    public static class DatosGatoResumen {
        public final Integer idGato;
        public final String nombre;
        public final String color;
        public final Integer edadAproximada;
        public final String estadoDeSalud;
        
        public DatosGatoResumen(Integer id, String nombre, String color, Integer edad, String estado) {
            this.idGato = id;
            this.nombre = nombre;
            this.color = color;
            this.edadAproximada = edad;
            this.estadoDeSalud = estado;
        }
    }
    
    /**
     * DTO para resumen de datos de familia
     */
    public static class DatosFamiliaResumen {
        public final Integer idFamilia;
        public final String nombre;
        public final String direccion;
        public final String telefono;
        public final String estado;
        public final String disponibilidad;
        
        public DatosFamiliaResumen(Integer id, String nombre, String direccion, String telefono, String estado, String disponibilidad) {
            this.idFamilia = id;
            this.nombre = nombre;
            this.direccion = direccion;
            this.telefono = telefono;
            this.estado = estado;
            this.disponibilidad = disponibilidad;
        }
    }
    
    /**
     * DTO para información de tareas
     */
    public static class TareaInfo {
        public final Integer idTarea;
        public final String descripcion;
        public final String tipo;
        public String estado; // No final porque se puede actualizar
        public final LocalDate fechaAsignacion;
        public final String nombreGato;
        
        public TareaInfo(Integer idTarea, String descripcion, String tipo, String estado, 
            LocalDate fechaAsignacion, String nombreGato) {
            this.idTarea = idTarea;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.estado = estado;
            this.fechaAsignacion = fechaAsignacion;
            this.nombreGato = nombreGato;
        }
    }
    
        // ==================== MÓDULO: VETERINARIO - ESTADO DE SALUD ====================

    /**
     * Obtiene información detallada de un gato por su ID
     * 
     * @return Información del gato, o null si no existe
     */
    public DatosGatoDetalle obtenerDatosGatoDetalle(Integer idGato) {
        // TODO: BASE DE DATOS - Consultar gato con detalles completos
        
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        if (idGato == 1) {
            return new DatosGatoDetalle(1, "Michi", "Naranja", 12, "Sano", "Gato tranquilo, muy sociable");
        } else if (idGato == 2) {
            return new DatosGatoDetalle(2, "Pelusa", "Blanco", 8, "En tratamiento", "Recuperándose de cirugía");
        } else if (idGato == 3) {
            return new DatosGatoDetalle(3, "Garfield", "Atigrado", 24, "Enfermo", "Problemas digestivos");
        } else if (idGato == 4) {
            return new DatosGatoDetalle(4, "Luna", "Negro", 6, "Esterilizado", "Esterilizada recientemente");
        }
        return null;
    }

    /**
     * Actualiza el estado de salud de un gato
     * 
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarEstadoDeSalud(Integer idGato, String nuevoEstado, String observacion) {
        // TODO: BASE DE DATOS - Actualizar estado de salud
       
        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Estado actualizado: Gato " + idGato + " -> " + nuevoEstado);
        if (observacion != null && !observacion.trim().isEmpty()) {
            System.out.println("Observación: " + observacion);
        }
        return true;
    }

    // ==================== CLASES DE DATOS (DTOs) ====================

    /**
     * DTO para información detallada de un gato
     */
    public static class DatosGatoDetalle {
        public final Integer idGato;
        public final String nombre;
        public final String color;
        public final Integer edadAproximada;
        public final String estadoSalud;
        public final String caracteristicas;

        public DatosGatoDetalle(Integer id, String nombre, String color, Integer edad, 
                               String estado, String caracteristicas) {
            this.idGato = id;
            this.nombre = nombre;
            this.color = color;
            this.edadAproximada = edad;
            this.estadoSalud = estado;
            this.caracteristicas = caracteristicas;
        }
    }
    
        // ==================== MÓDULO: VETERINARIO - DIAGNÓSTICO Y TRATAMIENTO ====================

    /**
     * Obtiene información completa de un gato incluyendo historial médico
     * 
     * @return Ficha clínica del gato, o null si no existe
     */
    public FichaClinicaGato obtenerFichaClinica(Integer idGato) {
        // TODO: BASE DE DATOS - Consultar gato con historial médico
        
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        if (idGato == 1) {
            FichaClinicaGato ficha = new FichaClinicaGato(
                1, "Michi", "Naranja", 12, "Sano", "Gato tranquilo, muy sociable", "Familia González", "Calle Principal 123"
            );
            ficha.historialMedico = obtenerHistorialMedico(idGato);
            return ficha;
        } else if (idGato == 2) {
            FichaClinicaGato ficha = new FichaClinicaGato(
                2, "Pelusa", "Blanco", 8, "En tratamiento", "Recuperándose de cirugía", "Familia Martínez", "Av. Libertador 456"
            );
            ficha.historialMedico = obtenerHistorialMedico(idGato);
            return ficha;
        } else if (idGato == 3) {
            FichaClinicaGato ficha = new FichaClinicaGato(
                3, "Garfield", "Atigrado", 24, "Enfermo", "Problemas digestivos", "En colonia", "Sin asignación"
            );
            ficha.historialMedico = obtenerHistorialMedico(idGato);
            return ficha;
        }
        return null;
    }

    /**
     * Obtiene el historial médico de un gato
     */
    private List<RegistroHistorialMedico> obtenerHistorialMedico(Integer idGato) {
        // TODO: BASE DE DATOS - Consultar historial médico
       
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        List<RegistroHistorialMedico> historial = new ArrayList<>();

        if (idGato == 1) {
            historial.add(new RegistroHistorialMedico(
                LocalDate.of(2025, 10, 15),
                "Chequeo general - Buen estado",
                "Ninguno requerido",
                "Examen físico",
                "Normal"
            ));
            historial.add(new RegistroHistorialMedico(
                LocalDate.of(2025, 9, 5),
                "Vacunación antirrábica",
                "Vacuna aplicada",
                "Vacunación",
                "Completada"
            ));
        } else if (idGato == 2) {
            historial.add(new RegistroHistorialMedico(
                LocalDate.of(2025, 11, 1),
                "Post-operatorio de esterilización",
                "Antibióticos y reposo",
                "Control post-quirúrgico",
                "En recuperación"
            ));
            historial.add(new RegistroHistorialMedico(
                LocalDate.of(2025, 10, 20),
                "Esterilización",
                "Cirugía realizada exitosamente",
                "Cirugía",
                "Exitosa"
            ));
        } else if (idGato == 3) {
            historial.add(new RegistroHistorialMedico(
                LocalDate.of(2025, 11, 10),
                "Gastroenteritis aguda",
                "Dieta blanda y medicación",
                "Análisis de sangre",
                "Infección detectada"
            ));
        }

        return historial;
    }

    /**
     * Registra un nuevo diagnóstico y tratamiento
     * 
     * @return ID del diagnóstico registrado, o null si falló
     */
    public Integer registrarDiagnostico(Integer idGato, LocalDate fecha, String hora, String diagnostico, String tratamiento) {
        // TODO: BASE DE DATOS - Guardar diagnóstico y tratamiento
       
        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Diagnóstico registrado: Gato " + idGato + " - " + fecha + " " + hora);
        System.out.println("Diagnóstico: " + diagnostico);
        System.out.println("Tratamiento: " + tratamiento);
        return new Random().nextInt(1000); // ID simulado
    }

        // ==================== CLASES DE DATOS (DTOs) ====================

        /**
         * DTO para ficha clínica completa de un gato
         */
        public static class FichaClinicaGato {
            public final Integer idGato;
            public final String nombre;
            public final String color;
            public final Integer edadAproximada;
            public final String estadoSalud;
            public final String caracteristicas;
            public final String ubicacionActual; // Nombre familia o "En colonia"
            public final String direccion;
            public List<RegistroHistorialMedico> historialMedico;

            public FichaClinicaGato(Integer id, String nombre, String color, Integer edad, String estado, String caracteristicas, String ubicacion, String direccion) {
                this.idGato = id;
                this.nombre = nombre;
                this.color = color;
                this.edadAproximada = edad;
                this.estadoSalud = estado;
                this.caracteristicas = caracteristicas;
                this.ubicacionActual = ubicacion;
                this.direccion = direccion;
                this.historialMedico = new ArrayList<>();
            }
        }

        /**
         * DTO para registro individual del historial médico
         */
        public static class RegistroHistorialMedico {
            public final LocalDate fecha;
            public final String diagnostico;
            public final String tratamiento;
            public final String tipoEstudio;
            public final String resultado;

            public RegistroHistorialMedico(LocalDate fecha, String diagnostico,
                String tratamiento, String tipoEstudio,
                String resultado) {
                    this.fecha = fecha;
                    this.diagnostico = diagnostico;
                    this.tratamiento = tratamiento;
                    this.tipoEstudio = tipoEstudio;
                    this.resultado = resultado;
                }
        }
    
            // ==================== MÓDULO: ADMINISTRADOR - REPORTES ====================

    /**
     * Obtiene las estadísticas de gatos para el reporte
     * 
     * @return Datos estadísticos de gatos, o null si hay error
     */
    public EstadisticasGatos obtenerEstadisticasGatos() {
        // TODO: BASE DE DATOS - Consultar estadísticas de gatos
       
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        int totalGatos = 45;
        int esterilizados = 28;
        int adoptados = 15;
        int transito = 8;

        System.out.println("Estadísticas de gatos generadas:");
        System.out.println("- Total de gatos: " + totalGatos);
        System.out.println("- Esterilizados: " + esterilizados);
        System.out.println("- Adoptados: " + adoptados);
        System.out.println("- En tránsito: " + transito);

        return new EstadisticasGatos(esterilizados, adoptados, transito, totalGatos);
    }

    /**
     * Genera un reporte de gatos en formato de texto para PDF
     * 
     * @return Contenido del reporte como String
     */
    public String generarContenidoReporteGatos(EstadisticasGatos stats) {
        StringBuilder reporte = new StringBuilder();

        // Encabezado
        reporte.append("═══════════════════════════════════════════════════════════\n");
        reporte.append("              REPORTE DE GATOS - COLONIA FELINA\n");
        reporte.append("═══════════════════════════════════════════════════════════\n\n");

        // Fecha de generación
        reporte.append("Fecha de generación: ").append(LocalDate.now()).append("\n");
        reporte.append("Hora: ").append(java.time.LocalTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
        )).append("\n\n");

        // Estadísticas principales
        reporte.append("───────────────────────────────────────────────────────────\n");
        reporte.append("                    ESTADÍSTICAS GENERALES\n");
        reporte.append("───────────────────────────────────────────────────────────\n\n");

        reporte.append(String.format("%-40s %5d\n", "Total de gatos registrados:", stats.totalGatos));
        reporte.append(String.format("%-40s %5d\n", "Gatos esterilizados:", stats.esterilizados));
        reporte.append(String.format("%-40s %5d\n", "Gatos adoptados:", stats.adoptados));
        reporte.append(String.format("%-40s %5d\n", "Gatos en tránsito:", stats.transito));

        // Gatos en colonia (sin asignar)
        int enColonia = stats.totalGatos - stats.adoptados - stats.transito;
        reporte.append(String.format("%-40s %5d\n", "Gatos en colonia:", enColonia));

        reporte.append("\n");

        // Porcentajes
        reporte.append("───────────────────────────────────────────────────────────\n");
        reporte.append("                        PORCENTAJES\n");
        reporte.append("───────────────────────────────────────────────────────────\n\n");

        if (stats.totalGatos > 0) {
            double porcEsterilizados = (stats.esterilizados * 100.0) / stats.totalGatos;
            double porcAdoptados = (stats.adoptados * 100.0) / stats.totalGatos;
            double porcTransito = (stats.transito * 100.0) / stats.totalGatos;
            double porcColonia = (enColonia * 100.0) / stats.totalGatos;

            reporte.append(String.format("%-40s %5.1f%%\n", "Porcentaje de esterilización:", porcEsterilizados));
            reporte.append(String.format("%-40s %5.1f%%\n", "Porcentaje de adopción:", porcAdoptados));
            reporte.append(String.format("%-40s %5.1f%%\n", "Porcentaje en tránsito:", porcTransito));
            reporte.append(String.format("%-40s %5.1f%%\n", "Porcentaje en colonia:", porcColonia));
        }

        reporte.append("\n");

        // Pie de reporte
        reporte.append("───────────────────────────────────────────────────────────\n");
        reporte.append("Reporte generado automáticamente por el Sistema de\n");
        reporte.append("Gestión de Colonia de Bichitos\n");
        reporte.append("───────────────────────────────────────────────────────────\n");

        return reporte.toString();
    }
    
        // ==================== MÓDULO: ADMINISTRADOR - GESTIÓN DE USUARIOS ====================

    /**
     * Obtiene la lista completa de usuarios del sistema
     * 
     * @return Lista de usuarios con su información básica
     */
    public List<UsuarioInfo> obtenerTodosLosUsuarios() {
        // TODO: BASE DE DATOS - Consultar todos los usuarios
        
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        List<UsuarioInfo> usuarios = new ArrayList<>();

        usuarios.add(new UsuarioInfo(1, "admin", "ADMINISTRADOR", "admin@colonia.com", "555-0001"));
        usuarios.add(new UsuarioInfo(2, "voluntario1", "VOLUNTARIO", "vol1@colonia.com", "555-0002"));
        usuarios.add(new UsuarioInfo(3, "voluntario2", "VOLUNTARIO", "vol2@colonia.com", "555-0003"));
        usuarios.add(new UsuarioInfo(4, "familia_gonzalez", "FAMILIA", "gonzalez@email.com", "555-0004"));
        usuarios.add(new UsuarioInfo(5, "familia_martinez", "FAMILIA", "martinez@email.com", "555-0005"));
        usuarios.add(new UsuarioInfo(6, "dr_veterinario", "VETERINARIO", "vet@colonia.com", "555-0006"));
        usuarios.add(new UsuarioInfo(7, "voluntario3", "VOLUNTARIO", "vol3@colonia.com", "555-0007"));
        usuarios.add(new UsuarioInfo(8, "familia_lopez", "FAMILIA", "lopez@email.com", "555-0008"));

        return usuarios;
    }

    /**
     * Obtiene información detallada de un usuario específico
     * 
     * @return Información completa del usuario, o null si no existe
     */
    public UsuarioDetalle obtenerDetalleUsuario(Integer idUsuario) {
        // TODO: BASE DE DATOS - Consultar usuario específico con todos sus datos
        
        // DATOS SIMULADOS
        for (UsuarioInfo info : obtenerTodosLosUsuarios()) {
            if (info.idUsuario.equals(idUsuario)) {
                return new UsuarioDetalle(
                    info.idUsuario,
                    info.nombreUsuario,
                    "password123",
                    info.rol,
                    info.correo,
                    info.telefono,
                    "Dirección de ejemplo " + idUsuario,
                    info.rol.equals("VOLUNTARIO") ? "Buena" : null
                );
            }
        }
        return null;
    }

    /**
     * Registra un nuevo usuario en el sistema
     * 
     * @return ID del usuario creado, o null si falló
     */
    public Integer registrarUsuario(String nombreUsuario, String contrasena, String rol, String correo, String telefono, String direccion) {
        // TODO: BASE DE DATOS - Insertar nuevo usuario
       
        // SIMULACIÓN
        System.out.println("Usuario registrado: " + nombreUsuario + " (" + rol + ")");
        return new Random().nextInt(1000) + 100;
    }

    /**
     * Actualiza la información de un usuario existente
     * 
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarUsuario(Integer idUsuario, String nombreUsuario, String contrasena, String correo, String telefono, String direccion) {
        // TODO: BASE DE DATOS - Actualizar usuario
       
        // SIMULACIÓN
        System.out.println("Usuario actualizado: ID " + idUsuario);
        return true;
    }

    /**
     * Elimina un usuario del sistema
     * 
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminarUsuario(Integer idUsuario) {
        // TODO: BASE DE DATOS - Eliminar usuario (con CASCADE para datos relacionados)
        
        // SIMULACIÓN
        System.out.println("Usuario eliminado: ID " + idUsuario);
        return true;
    }

    // ==================== CLASES DE DATOS (DTOs) ====================

    /**
     * DTO para información básica de usuario (para listado)
     */
    public static class UsuarioInfo {
        public final Integer idUsuario;
        public final String nombreUsuario;
        public final String rol;
        public final String correo;
        public final String telefono;

        public UsuarioInfo(Integer id, String nombre, String rol, String correo, String telefono) {
            this.idUsuario = id;
            this.nombreUsuario = nombre;
            this.rol = rol;
            this.correo = correo != null ? correo : "No especificado";
            this.telefono = telefono != null ? telefono : "No especificado";
        }
    }

    /**
     * DTO para información detallada de usuario (para edición)
     */
    public static class UsuarioDetalle {
        public final Integer idUsuario;
        public final String nombreUsuario;
        public final String contrasena;
        public final String rol;
        public final String correo;
        public final String telefono;
        public final String direccion;
        public final String datosAdicionales; // Reputación, estado, etc.

        public UsuarioDetalle(Integer id, String nombre, String contrasena, String rol,
                             String correo, String telefono, String direccion, String datosAdicionales) {
            this.idUsuario = id;
            this.nombreUsuario = nombre;
            this.contrasena = contrasena;
            this.rol = rol;
            this.correo = correo;
            this.telefono = telefono;
            this.direccion = direccion;
            this.datosAdicionales = datosAdicionales;
        }
    }

    // ==================== CLASES DE DATOS (DTOs) ====================

    /**
     * DTO para estadísticas de gatos
     */
    public static class EstadisticasGatos {
        public final int esterilizados;
        public final int adoptados;
        public final int transito;
        public final int totalGatos;

        public EstadisticasGatos(int esterilizados, int adoptados, int transito, int totalGatos) {
            this.esterilizados = esterilizados;
            this.adoptados = adoptados;
            this.transito = transito;
            this.totalGatos = totalGatos;
        }
    }
        
}

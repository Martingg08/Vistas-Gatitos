package com.gui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Controlador para la gestión de operaciones de voluntarios
 * Centraliza toda la lógica de negocio y acceso a datos
 * 
 * @author Tincho
 */
public class Controlador {
    
    // TODO: Inyectar conexión a BD o DAOs según tu arquitectura
    // private Connection conexion;
    // private GatoDAO gatoDAO;
    // private FamiliaDAO familiaDAO;
    // private VisitaDAO visitaDAO;
    // private TareaDAO tareaDAO;
    
    private Integer idVoluntarioActual;
    
    public Controlador(Integer idVoluntarioActual) {
        this.idVoluntarioActual = idVoluntarioActual;
        // TODO: Inicializar conexión a BD o DAOs
    }
    
    // ==================== MÓDULO: GATOS ====================
    
    /**
     * Registra un nuevo gato en el sistema
     * 
     * @return ID del gato registrado, o null si falló
     */
    public Integer registrarGato(String nombre, String color, String caracteristicas,
                                 String estadoSalud, Integer edadAproximada, 
                                 String rutaFoto, String codigoQR) {
        // TODO: BASE DE DATOS - Guardar gato
        // try {
        //     String sql = "INSERT INTO Gatos (nombre, color, caracteristicas, estado_salud, " +
        //                  "edad_aproximada, foto, codigo_qr) VALUES (?, ?, ?, ?, ?, ?, ?)";
        //     PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //     stmt.setString(1, nombre);
        //     stmt.setString(2, color);
        //     stmt.setString(3, caracteristicas);
        //     stmt.setString(4, estadoSalud);
        //     stmt.setObject(5, edadAproximada);
        //     stmt.setString(6, rutaFoto);
        //     stmt.setString(7, codigoQR);
        //     
        //     int filasAfectadas = stmt.executeUpdate();
        //     if (filasAfectadas > 0) {
        //         ResultSet rs = stmt.getGeneratedKeys();
        //         if (rs.next()) {
        //             return rs.getInt(1);
        //         }
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return null;
        // }
        
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
        // TODO: BASE DE DATOS - Consultar gato
        // try {
        //     String sql = "SELECT id, nombre, color, edad_aproximada, estado_salud " +
        //                  "FROM Gatos WHERE id = ?";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idGato);
        //     ResultSet rs = stmt.executeQuery();
        //     
        //     if (rs.next()) {
        //         return new DatosGatoResumen(
        //             rs.getInt("id"),
        //             rs.getString("nombre"),
        //             rs.getString("color"),
        //             (Integer) rs.getObject("edad_aproximada"),
        //             rs.getString("estado_salud")
        //         );
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        // return null;
        
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
        // try {
        //     String sql = "SELECT g.id, g.nombre as nombre_gato, " +
        //                  "f.id as id_familia, f.nombre as nombre_familia, " +
        //                  "f.direccion, f.telefono, f.reputacion, " +
        //                  "a.tipo_asignacion, a.fecha_asignacion, " +
        //                  "MAX(v.fecha_visita) as ultima_visita " +
        //                  "FROM Gatos g " +
        //                  "JOIN Asignaciones a ON g.id = a.id_gato " +
        //                  "JOIN Familias f ON a.id_familia = f.id " +
        //                  "LEFT JOIN Visitas v ON g.id = v.id_gato " +
        //                  "WHERE a.activa = true " +
        //                  "GROUP BY g.id, f.id, a.tipo_asignacion, a.fecha_asignacion";
        //     
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     ResultSet rs = stmt.executeQuery();
        //     
        //     List<GatoAsignadoInfo> lista = new ArrayList<>();
        //     while (rs.next()) {
        //         lista.add(new GatoAsignadoInfo(
        //             rs.getInt("id"),
        //             rs.getString("nombre_gato"),
        //             rs.getInt("id_familia"),
        //             rs.getString("nombre_familia"),
        //             rs.getString("direccion"),
        //             rs.getString("telefono"),
        //             rs.getString("reputacion"),
        //             rs.getString("tipo_asignacion"),
        //             rs.getDate("fecha_asignacion").toLocalDate(),
        //             rs.getDate("ultima_visita") != null ? 
        //                 rs.getDate("ultima_visita").toLocalDate() : null
        //         ));
        //     }
        //     return lista;
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return new ArrayList<>();
        // }
        
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        List<GatoAsignadoInfo> lista = new ArrayList<>();
        lista.add(new GatoAsignadoInfo(
            1, "Michi", 1, "Familia González", "Calle Principal 123",
            "123-456-7890", "Buena", "Adopción Definitiva",
            LocalDate.of(2025, 10, 15), LocalDate.of(2025, 11, 1)
        ));
        lista.add(new GatoAsignadoInfo(
            2, "Pelusa", 2, "Familia Martínez", "Av. Libertador 456",
            "098-765-4321", "Excelente", "Tránsito",
            LocalDate.of(2025, 11, 5), null
        ));
        lista.add(new GatoAsignadoInfo(
            3, "Garfield", 1, "Familia González", "Calle Principal 123",
            "123-456-7890", "Buena", "Tránsito",
            LocalDate.of(2025, 11, 10), null
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
        // try {
        //     String sql = "SELECT id, nombre, direccion, telefono, estado, disponibilidad " +
        //                  "FROM Familias WHERE id = ?";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idFamilia);
        //     ResultSet rs = stmt.executeQuery();
        //     
        //     if (rs.next()) {
        //         return new DatosFamiliaResumen(
        //             rs.getInt("id"),
        //             rs.getString("nombre"),
        //             rs.getString("direccion"),
        //             rs.getString("telefono"),
        //             rs.getString("estado"),
        //             rs.getString("disponibilidad")
        //         );
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        // return null;
        
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
    public boolean asignarGatoAFamilia(Integer idGato, Integer idFamilia, 
                                       LocalDate fechaAsignacion, String tipoAsignacion) {
        // TODO: BASE DE DATOS - Guardar asignación
        // try {
        //     String sql = "INSERT INTO Asignaciones (id_gato, id_familia, fecha_asignacion, " +
        //                  "tipo_asignacion, activa) VALUES (?, ?, ?, ?, true)";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idGato);
        //     stmt.setInt(2, idFamilia);
        //     stmt.setDate(3, java.sql.Date.valueOf(fechaAsignacion));
        //     stmt.setString(4, tipoAsignacion);
        //     
        //     int filasAfectadas = stmt.executeUpdate();
        //     return filasAfectadas > 0;
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return false;
        // }
        
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
    public boolean registrarVisita(Integer idGato, LocalDate fechaVisita, 
                                   String estadoGeneral, String observaciones, 
                                   String sugerencias) {
        // TODO: BASE DE DATOS - Guardar visita
        // try {
        //     String sql = "INSERT INTO Visitas (id_gato, id_voluntario, fecha_visita, " +
        //                  "estado_general, observaciones, sugerencias) VALUES (?, ?, ?, ?, ?, ?)";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idGato);
        //     stmt.setInt(2, idVoluntarioActual);
        //     stmt.setDate(3, java.sql.Date.valueOf(fechaVisita));
        //     stmt.setString(4, estadoGeneral);
        //     stmt.setString(5, observaciones);
        //     stmt.setString(6, sugerencias.isEmpty() ? null : sugerencias);
        //     
        //     int filasAfectadas = stmt.executeUpdate();
        //     return filasAfectadas > 0;
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return false;
        // }
        
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
        // try {
        //     String sql = "SELECT t.id_tarea, t.descripcion, t.tipo, t.estado, " +
        //                  "t.fecha_asignacion, g.nombre as nombre_gato " +
        //                  "FROM Tareas t " +
        //                  "JOIN Gatos g ON t.id_gato = g.id " +
        //                  "WHERE t.id_voluntario = ? " +
        //                  "AND MONTH(t.fecha_asignacion) = ? " +
        //                  "AND YEAR(t.fecha_asignacion) = ?";
        //     
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idVoluntarioActual);
        //     stmt.setInt(2, mesAnio.getMonthValue());
        //     stmt.setInt(3, mesAnio.getYear());
        //     ResultSet rs = stmt.executeQuery();
        //     
        //     Map<LocalDate, List<TareaInfo>> tareas = new HashMap<>();
        //     while (rs.next()) {
        //         LocalDate fecha = rs.getDate("fecha_asignacion").toLocalDate();
        //         TareaInfo tarea = new TareaInfo(
        //             rs.getInt("id_tarea"),
        //             rs.getString("descripcion"),
        //             rs.getString("tipo"),
        //             rs.getString("estado"),
        //             fecha,
        //             rs.getString("nombre_gato")
        //         );
        //         
        //         tareas.computeIfAbsent(fecha, k -> new ArrayList<>()).add(tarea);
        //     }
        //     return tareas;
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return new HashMap<>();
        // }
        
        // DATOS SIMULADOS - Eliminar cuando se implemente BD
        Map<LocalDate, List<TareaInfo>> tareas = new HashMap<>();
        
        LocalDate fecha1 = LocalDate.now().withDayOfMonth(5);
        if (fecha1.getMonth() == mesAnio.getMonth() && fecha1.getYear() == mesAnio.getYear()) {
            List<TareaInfo> lista1 = new ArrayList<>();
            lista1.add(new TareaInfo(1, "Revisar estado de salud de Michi", "Revisión", 
                                    "PENDIENTE", fecha1, "Michi"));
            lista1.add(new TareaInfo(2, "Llevar al veterinario", "Veterinario", 
                                    "PENDIENTE", fecha1, "Michi"));
            tareas.put(fecha1, lista1);
        }
        
        LocalDate fecha2 = LocalDate.now().withDayOfMonth(10);
        if (fecha2.getMonth() == mesAnio.getMonth() && fecha2.getYear() == mesAnio.getYear()) {
            List<TareaInfo> lista2 = new ArrayList<>();
            lista2.add(new TareaInfo(3, "Alimentación especial", "Alimentación", 
                                    "FINALIZADA", fecha2, "Pelusa"));
            tareas.put(fecha2, lista2);
        }
        
        LocalDate fecha3 = LocalDate.now().withDayOfMonth(15);
        if (fecha3.getMonth() == mesAnio.getMonth() && fecha3.getYear() == mesAnio.getYear()) {
            List<TareaInfo> lista3 = new ArrayList<>();
            lista3.add(new TareaInfo(4, "Control post-operatorio", "Seguimiento", 
                                    "PENDIENTE", fecha3, "Garfield"));
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
        // try {
        //     String sql = "UPDATE Tareas SET estado = 'FINALIZADA' WHERE id_tarea = ?";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idTarea);
        //     
        //     int filasAfectadas = stmt.executeUpdate();
        //     return filasAfectadas > 0;
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return false;
        // }
        
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
        
        public DatosFamiliaResumen(Integer id, String nombre, String direccion, 
                                   String telefono, String estado, String disponibilidad) {
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
        // try {
        //     String sql = "SELECT id, nombre, color, edad_aproximada, estado_salud, caracteristicas " +
        //                  "FROM Gatos WHERE id = ?";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setInt(1, idGato);
        //     ResultSet rs = stmt.executeQuery();
        //     
        //     if (rs.next()) {
        //         return new DatosGatoDetalle(
        //             rs.getInt("id"),
        //             rs.getString("nombre"),
        //             rs.getString("color"),
        //             (Integer) rs.getObject("edad_aproximada"),
        //             rs.getString("estado_salud"),
        //             rs.getString("caracteristicas")
        //         );
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        // return null;

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
        // try {
        //     // Actualizar el estado del gato
        //     String sql = "UPDATE Gatos SET estado_salud = ? WHERE id = ?";
        //     PreparedStatement stmt = conexion.prepareStatement(sql);
        //     stmt.setString(1, nuevoEstado);
        //     stmt.setInt(2, idGato);
        //     
        //     int filasAfectadas = stmt.executeUpdate();
        //     
        //     // Si hay observación, registrarla en un historial
        //     if (filasAfectadas > 0 && observacion != null && !observacion.trim().isEmpty()) {
        //         String sqlHistorial = "INSERT INTO HistorialSalud (id_gato, estado, observacion, fecha) " +
        //                              "VALUES (?, ?, ?, NOW())";
        //         PreparedStatement stmtHistorial = conexion.prepareStatement(sqlHistorial);
        //         stmtHistorial.setInt(1, idGato);
        //         stmtHistorial.setString(2, nuevoEstado);
        //         stmtHistorial.setString(3, observacion);
        //         stmtHistorial.executeUpdate();
        //     }
        //     
        //     return filasAfectadas > 0;
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return false;
        // }

        // SIMULACIÓN - Eliminar cuando se implemente BD
        System.out.println("Estado actualizado: Gato " + idGato + " -> " + nuevoEstado);
        if (observacion != null && !observacion.trim().isEmpty()) {
            System.out.println("Observación: " + observacion);
        }
        return true;
    }

    // ==================== CLASES DE DATOS (DTOs) - AGREGAR ====================

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
    // try {
    //     String sql = "SELECT g.id, g.nombre, g.color, g.estado_salud, g.caracteristicas, " +
    //                  "       g.edad_aproximada, f.nombre as familia, f.direccion " +
    //                  "FROM Gatos g " +
    //                  "LEFT JOIN Asignaciones a ON g.id = a.id_gato AND a.activa = true " +
    //                  "LEFT JOIN Familias f ON a.id_familia = f.id " +
    //                  "WHERE g.id = ?";
    //     PreparedStatement stmt = conexion.prepareStatement(sql);
    //     stmt.setInt(1, idGato);
    //     ResultSet rs = stmt.executeQuery();
    //     
    //     if (rs.next()) {
    //         FichaClinicaGato ficha = new FichaClinicaGato(
    //             rs.getInt("id"),
    //             rs.getString("nombre"),
    //             rs.getString("color"),
    //             (Integer) rs.getObject("edad_aproximada"),
    //             rs.getString("estado_salud"),
    //             rs.getString("caracteristicas"),
    //             rs.getString("familia"),
    //             rs.getString("direccion")
    //         );
    //         
    //         // Obtener historial médico
    //         ficha.historialMedico = obtenerHistorialMedico(idGato);
    //         
    //         return ficha;
    //     }
    // } catch (SQLException e) {
    //     e.printStackTrace();
    // }
    // return null;
    
    // DATOS SIMULADOS - Eliminar cuando se implemente BD
    if (idGato == 1) {
        FichaClinicaGato ficha = new FichaClinicaGato(
            1, "Michi", "Naranja", 12, "Sano", 
            "Gato tranquilo, muy sociable",
            "Familia González", "Calle Principal 123"
        );
        ficha.historialMedico = obtenerHistorialMedico(idGato);
        return ficha;
    } else if (idGato == 2) {
        FichaClinicaGato ficha = new FichaClinicaGato(
            2, "Pelusa", "Blanco", 8, "En tratamiento",
            "Recuperándose de cirugía",
            "Familia Martínez", "Av. Libertador 456"
        );
        ficha.historialMedico = obtenerHistorialMedico(idGato);
        return ficha;
    } else if (idGato == 3) {
        FichaClinicaGato ficha = new FichaClinicaGato(
            3, "Garfield", "Atigrado", 24, "Enfermo",
            "Problemas digestivos",
            "En colonia", "Sin asignación"
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
    // try {
    //     String sql = "SELECT dt.fecha, dt.diagnostico, dt.tratamiento, " +
    //                  "       e.tipo_estudio, e.resultado " +
    //                  "FROM DiagnosticoYTratamiento dt " +
    //                  "LEFT JOIN Estudios e ON dt.id_estudio = e.id " +
    //                  "WHERE dt.id_gato = ? " +
    //                  "ORDER BY dt.fecha DESC";
    //     PreparedStatement stmt = conexion.prepareStatement(sql);
    //     stmt.setInt(1, idGato);
    //     ResultSet rs = stmt.executeQuery();
    //     
    //     List<RegistroHistorialMedico> historial = new ArrayList<>();
    //     while (rs.next()) {
    //         historial.add(new RegistroHistorialMedico(
    //             rs.getDate("fecha").toLocalDate(),
    //             rs.getString("diagnostico"),
    //             rs.getString("tratamiento"),
    //             rs.getString("tipo_estudio"),
    //             rs.getString("resultado")
    //         ));
    //     }
    //     return historial;
    // } catch (SQLException e) {
    //     e.printStackTrace();
    //     return new ArrayList<>();
    // }
    
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
public Integer registrarDiagnostico(Integer idGato, LocalDate fecha, String hora,
                                   String diagnostico, String tratamiento) {
    // TODO: BASE DE DATOS - Guardar diagnóstico y tratamiento
    // try {
    //     String sql = "INSERT INTO DiagnosticoYTratamiento " +
    //                  "(id_gato, fecha, hora, diagnostico, tratamiento) " +
    //                  "VALUES (?, ?, ?, ?, ?)";
    //     PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    //     stmt.setInt(1, idGato);
    //     stmt.setDate(2, java.sql.Date.valueOf(fecha));
    //     stmt.setString(3, hora);
    //     stmt.setString(4, diagnostico);
    //     stmt.setString(5, tratamiento);
    //     
    //     int filasAfectadas = stmt.executeUpdate();
    //     if (filasAfectadas > 0) {
    //         ResultSet rs = stmt.getGeneratedKeys();
    //         if (rs.next()) {
    //             return rs.getInt(1);
    //         }
    //     }
    // } catch (SQLException e) {
    //     e.printStackTrace();
    //     return null;
    // }
    
    // SIMULACIÓN - Eliminar cuando se implemente BD
    System.out.println("Diagnóstico registrado: Gato " + idGato + " - " + fecha + " " + hora);
    System.out.println("Diagnóstico: " + diagnostico);
    System.out.println("Tratamiento: " + tratamiento);
    return new Random().nextInt(1000); // ID simulado
}

    // ==================== CLASES DE DATOS (DTOs) - AGREGAR ====================

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

        public FichaClinicaGato(Integer id, String nombre, String color, Integer edad,
                               String estado, String caracteristicas,
                               String ubicacion, String direccion) {
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
    
}

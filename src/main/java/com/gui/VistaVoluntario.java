package com.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

/**
 * Vista principal para el módulo de Voluntarios
 * Gestiona el registro de gatos, asignación a familias, visitas de seguimiento y calendario de tareas
 * 
 * @author Tincho
 */
public class VistaVoluntario extends javax.swing.JPanel {

    private Main mainFrame;
    private JPanel formularioRegistrarGato;
    private JPanel panelCalendario;
    private Controlador controlador;
    
    // TODO: REEMPLAZAR CON EL VOLUNTARIO REAL DE LA SESIÓN - Obtener desde sistema de autenticación
    private Integer idVoluntarioActual = 1;

    // ==================== CONSTRUCTORES ====================
    
    public VistaVoluntario(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.controlador = new Controlador(idVoluntarioActual);
        initComponents();
    }
    
    public VistaVoluntario() {
        this.controlador = new Controlador(idVoluntarioActual);
        initComponents();
    }
    
    // ==================== MÓDULO: REGISTRAR VISITA DE SEGUIMIENTO ====================
    
    /**
     * Muestra el formulario inicial para registrar una visita de seguimiento
     * Presenta una lista de gatos asignados a familias
     */
    private void mostrarFormularioRegistrarVisita() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Registrar Visita de Seguimiento");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel con la lista de gatos
        JPanel panelSeleccion = crearPanelSeleccionGato();
        panelPrincipal.add(panelSeleccion, BorderLayout.CENTER);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalVoluntario.removeAll();
        ContenedorPrincipalVoluntario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVoluntario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVoluntario.revalidate();
        ContenedorPrincipalVoluntario.repaint();
    }

    /**
     * Crea el panel de selección de gato para registrar visita
     * Muestra tabla con gatos asignados y permite seleccionar uno
     */
    private JPanel crearPanelSeleccionGato() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(102, 102, 102));
        
        // Instrucciones
        JLabel labelInstrucciones = new JLabel("<html><center>Seleccione un gato de la lista para registrar una visita de seguimiento.<br>" +
                                              "Solo se muestran gatos actualmente asignados a familias.</center></html>");
        labelInstrucciones.setFont(new Font("Aptos", Font.PLAIN, 16));
        labelInstrucciones.setForeground(Color.WHITE);
        labelInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        labelInstrucciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        panel.add(labelInstrucciones, BorderLayout.NORTH);
        
        // TODO: BASE DE DATOS - Obtener lista de gatos asignados
        // Query: SELECT g.*, f.*, a.tipo_asignacion, a.fecha_asignacion, MAX(v.fecha_visita) as ultima_visita
        //        FROM Gatos g JOIN Asignaciones a ON g.id = a.id_gato
        //        JOIN Familias f ON a.id_familia = f.id
        //        LEFT JOIN Visitas v ON g.id = v.id_gato
        //        WHERE a.activa = true
        //        GROUP BY g.id
        List<GatoAsignadoInfo> gatosAsignados = obtenerGatosAsignados();
        
        if (gatosAsignados.isEmpty()) {
            JLabel labelSinGatos = new JLabel("No hay gatos asignados a familias actualmente");
            labelSinGatos.setFont(new Font("Aptos", Font.BOLD, 18));
            labelSinGatos.setForeground(Color.YELLOW);
            labelSinGatos.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(labelSinGatos, BorderLayout.CENTER);
            return panel;
        }
        
        // Panel con la tabla de gatos
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(new Color(102, 102, 102));
        
        // Crear tabla
        String[] columnas = {"ID", "Nombre Gato", "Familia", "Tipo Asignación", "Fecha Asignación"};
        Object[][] datos = new Object[gatosAsignados.size()][5];
        
        for (int i = 0; i < gatosAsignados.size(); i++) {
            GatoAsignadoInfo gato = gatosAsignados.get(i);
            datos[i][0] = gato.idGato;
            datos[i][1] = gato.nombreGato;
            datos[i][2] = gato.nombreFamilia;
            datos[i][3] = gato.tipoAsignacion;
            datos[i][4] = gato.fechaAsignacion;
        }
        
        JTable tabla = new JTable(datos, columnas);
        tabla.setFont(new Font("Aptos", Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Aptos", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(0, 102, 153));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setBackground(new Color(204, 204, 204));
        
        JScrollPane scrollTabla = new JScrollPane(tabla);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        panel.add(panelTabla, BorderLayout.CENTER);
        
        // Panel con selector de ID y botón
        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelSelector.setBackground(new Color(102, 102, 102));
        panelSelector.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        JLabel labelId = new JLabel("ID del Gato:");
        labelId.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelId.setForeground(Color.WHITE);
        panelSelector.add(labelId);
        
        JTextField textFieldId = new JTextField(10);
        textFieldId.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldId.setBackground(new Color(204, 204, 204));
        textFieldId.setPreferredSize(new Dimension(150, 35));
        panelSelector.add(textFieldId);
        
        JButton botonContinuar = new JButton("Continuar");
        botonContinuar.setFont(new Font("Aptos", Font.BOLD, 16));
        botonContinuar.setBackground(new Color(0, 102, 153));
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setBorderPainted(false);
        botonContinuar.setPreferredSize(new Dimension(150, 40));
        botonContinuar.addActionListener(e -> {
            String idTexto = textFieldId.getText().trim();
            
            // Validar que no esté vacío
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "Debe ingresar el ID del gato",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que sea un número
            Integer idGato;
            try {
                idGato = Integer.parseInt(idTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel,
                    "El ID debe ser un número válido",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Buscar el gato en la lista
            GatoAsignadoInfo gatoSeleccionado = null;
            for (GatoAsignadoInfo gato : gatosAsignados) {
                if (gato.idGato.equals(idGato)) {
                    gatoSeleccionado = gato;
                    break;
                }
            }
            
            if (gatoSeleccionado == null) {
                JOptionPane.showMessageDialog(panel,
                    "El gato con ID " + idGato + " no está asignado a ninguna familia.\n" +
                    "No es posible registrar una visita.",
                    "Gato sin asignación vigente",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Continuar con el formulario de visita
            mostrarFormularioDetallesVisita(gatoSeleccionado);
        });
        panelSelector.add(botonContinuar);
        
        // Listener para selección en la tabla - autocompletar ID
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    Object idObj = tabla.getValueAt(filaSeleccionada, 0);
                    textFieldId.setText(idObj.toString());
                }
            }
        });
        
        panel.add(panelSelector, BorderLayout.SOUTH);
        
        return panel;
    }

    /**
     * Muestra el formulario de detalles de la visita
     * Incluye información del gato y familia, y campos para registrar la visita
     */
    private void mostrarFormularioDetallesVisita(GatoAsignadoInfo gato) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Registrar Visita de Seguimiento");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel central con información y formulario
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(102, 102, 102));
        
        // Panel de información del gato y familia
        JPanel panelInfo = crearPanelInformacionGatoFamilia(gato);
        panelCentral.add(panelInfo, BorderLayout.NORTH);
        
        // Panel del formulario
        JPanel panelFormulario = crearFormularioVisita(gato);
        panelCentral.add(panelFormulario, BorderLayout.CENTER);
        
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalVoluntario.removeAll();
        ContenedorPrincipalVoluntario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVoluntario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVoluntario.revalidate();
        ContenedorPrincipalVoluntario.repaint();
    }

    /**
     * Crea el panel con información del gato y la familia asignada
     */
    private JPanel crearPanelInformacionGatoFamilia(GatoAsignadoInfo gato) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(new Color(102, 102, 102));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // ========== Información del Gato ==========
        JPanel panelGato = new JPanel(new GridBagLayout());
        panelGato.setBackground(new Color(80, 80, 80));
        panelGato.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 200, 100), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        JLabel labelTituloGato = new JLabel("▸ GATO SELECCIONADO");
        labelTituloGato.setFont(new Font("Aptos", Font.BOLD, 18));
        labelTituloGato.setForeground(new Color(255, 200, 100));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelGato.add(labelTituloGato, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        agregarInfoLabel(panelGato, gbc, "ID:", String.valueOf(gato.idGato));
        gbc.gridy = 2;
        agregarInfoLabel(panelGato, gbc, "Nombre:", gato.nombreGato);
        gbc.gridy = 3;
        agregarInfoLabel(panelGato, gbc, "Tipo de asignación:", gato.tipoAsignacion);
        gbc.gridy = 4;
        agregarInfoLabel(panelGato, gbc, "Fecha de asignación:", gato.fechaAsignacion.toString());
        
        panel.add(panelGato);
        
        // ========== Información de la Familia ==========
        JPanel panelFamilia = new JPanel(new GridBagLayout());
        panelFamilia.setBackground(new Color(80, 80, 80));
        panelFamilia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(144, 238, 144), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        JLabel labelTituloFamilia = new JLabel("▸ FAMILIA ASIGNADA");
        labelTituloFamilia.setFont(new Font("Aptos", Font.BOLD, 18));
        labelTituloFamilia.setForeground(new Color(144, 238, 144));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelFamilia.add(labelTituloFamilia, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        agregarInfoLabel(panelFamilia, gbc, "Nombre:", gato.nombreFamilia);
        gbc.gridy = 2;
        agregarInfoLabel(panelFamilia, gbc, "Dirección:", gato.direccionFamilia);
        gbc.gridy = 3;
        agregarInfoLabel(panelFamilia, gbc, "Teléfono:", gato.telefonoFamilia);
        gbc.gridy = 4;
        agregarInfoLabel(panelFamilia, gbc, "Reputación:", gato.reputacionFamilia);
        
        // Mostrar última visita si existe
        if (gato.fechaUltimaVisita != null) {
            gbc.gridy = 5;
            JLabel labelUltima = new JLabel("Última visita:");
            labelUltima.setFont(new Font("Aptos", Font.BOLD, 14));
            labelUltima.setForeground(Color.YELLOW);
            gbc.gridx = 0;
            panelFamilia.add(labelUltima, gbc);
            
            JLabel labelFechaUltima = new JLabel(gato.fechaUltimaVisita.toString());
            labelFechaUltima.setFont(new Font("Aptos", Font.PLAIN, 14));
            labelFechaUltima.setForeground(Color.YELLOW);
            gbc.gridx = 1;
            panelFamilia.add(labelFechaUltima, gbc);
        } else {
            gbc.gridy = 5;
            JLabel labelSinVisitas = new JLabel("Sin visitas previas registradas");
            labelSinVisitas.setFont(new Font("Aptos", Font.ITALIC, 14));
            labelSinVisitas.setForeground(Color.LIGHT_GRAY);
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            panelFamilia.add(labelSinVisitas, gbc);
        }
        
        panel.add(panelFamilia);
        
        return panel;
    }

    /**
     * Método auxiliar para agregar etiquetas de información
     */
    private void agregarInfoLabel(JPanel panel, GridBagConstraints gbc, String etiqueta, String valor) {
        JLabel labelEtiqueta = new JLabel(etiqueta);
        labelEtiqueta.setFont(new Font("Aptos", Font.BOLD, 14));
        labelEtiqueta.setForeground(Color.WHITE);
        gbc.gridx = 0;
        panel.add(labelEtiqueta, gbc);
        
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelValor.setForeground(new Color(220, 220, 220));
        gbc.gridx = 1;
        panel.add(labelValor, gbc);
    }

    /**
     * Crea el formulario para registrar los detalles de la visita
     */
    private JPanel crearFormularioVisita(GatoAsignadoInfo gato) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int fila = 0;
        
        // ========== Fecha de la Visita ==========
        JLabel labelFecha = new JLabel("Fecha de la Visita: *");
        labelFecha.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelFecha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(labelFecha, gbc);
        
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setBackground(new Color(102, 102, 102));
        
        JComboBox<Integer> comboDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            comboDia.addItem(i);
        }
        comboDia.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboDia.setBackground(new Color(204, 204, 204));
        comboDia.setPreferredSize(new Dimension(70, 35));
        
        JComboBox<String> comboMes = new JComboBox<>(new String[]{
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        comboMes.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboMes.setBackground(new Color(204, 204, 204));
        comboMes.setPreferredSize(new Dimension(130, 35));
        
        JComboBox<Integer> comboAnio = new JComboBox<>();
        int anioActual = LocalDate.now().getYear();
        for (int i = anioActual - 1; i <= anioActual + 1; i++) {
            comboAnio.addItem(i);
        }
        comboAnio.setSelectedItem(anioActual);
        comboAnio.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboAnio.setBackground(new Color(204, 204, 204));
        comboAnio.setPreferredSize(new Dimension(90, 35));
        
        // Establecer fecha actual por defecto
        LocalDate hoy = LocalDate.now();
        comboDia.setSelectedItem(hoy.getDayOfMonth());
        comboMes.setSelectedIndex(hoy.getMonthValue() - 1);
        
        panelFecha.add(comboDia);
        panelFecha.add(comboMes);
        panelFecha.add(comboAnio);
        
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panel.add(panelFecha, gbc);
        
        // ========== Estado General del Gato ==========
        JLabel labelEstado = new JLabel("Estado General del Gato: *");
        labelEstado.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelEstado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(labelEstado, gbc);
        
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{
            "Excelente", "Bueno", "Regular", "Malo", "Crítico"
        });
        comboEstado.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboEstado.setBackground(new Color(204, 204, 204));
        comboEstado.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panel.add(comboEstado, gbc);
        
        // ========== Observaciones ==========
        JLabel labelObservaciones = new JLabel("Observaciones (Entorno y Trato): *");
        labelObservaciones.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelObservaciones.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(labelObservaciones, gbc);
        
        JTextArea textAreaObservaciones = new JTextArea(5, 30);
        textAreaObservaciones.setFont(new Font("Aptos", Font.PLAIN, 16));
        textAreaObservaciones.setBackground(new Color(204, 204, 204));
        textAreaObservaciones.setForeground(Color.BLACK);
        textAreaObservaciones.setLineWrap(true);
        textAreaObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObservaciones = new JScrollPane(textAreaObservaciones);
        scrollObservaciones.setPreferredSize(new Dimension(400, 120));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(scrollObservaciones, gbc);
        
        // ========== Sugerencias (Opcional) ==========
        JLabel labelSugerencias = new JLabel("Sugerencias (Opcional):");
        labelSugerencias.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelSugerencias.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(labelSugerencias, gbc);
        
        JTextArea textAreaSugerencias = new JTextArea(4, 30);
        textAreaSugerencias.setFont(new Font("Aptos", Font.PLAIN, 16));
        textAreaSugerencias.setBackground(new Color(204, 204, 204));
        textAreaSugerencias.setForeground(Color.BLACK);
        textAreaSugerencias.setLineWrap(true);
        textAreaSugerencias.setWrapStyleWord(true);
        JScrollPane scrollSugerencias = new JScrollPane(textAreaSugerencias);
        scrollSugerencias.setPreferredSize(new Dimension(400, 100));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(scrollSugerencias, gbc);
        
        // ========== Panel de Botones ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 45));
        botonCancelar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(panel,
                "¿Está seguro que desea cancelar? Se perderán los datos ingresados.",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                mostrarFormularioRegistrarVisita();
            }
        });
        panelBotones.add(botonCancelar);
        
        JButton botonRegistrar = new JButton("Registrar Visita");
        botonRegistrar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonRegistrar.setBackground(new Color(0, 150, 0));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setBorderPainted(false);
        botonRegistrar.setPreferredSize(new Dimension(180, 45));
        botonRegistrar.addActionListener(e -> {
            // Validar campos obligatorios
            if (textAreaObservaciones.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "El campo 'Observaciones sobre el entorno y trato' es obligatorio.",
                    "Falta de datos obligatorios",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener datos del formulario
            LocalDate fechaVisita = LocalDate.of(
                (Integer) comboAnio.getSelectedItem(),
                comboMes.getSelectedIndex() + 1,
                (Integer) comboDia.getSelectedItem()
            );
            String estadoGeneral = (String) comboEstado.getSelectedItem();
            String observaciones = textAreaObservaciones.getText().trim();
            String sugerencias = textAreaSugerencias.getText().trim();
            
            // TODO: BASE DE DATOS - Guardar visita en la base de datos
            // INSERT INTO Visitas (id_gato, id_voluntario, fecha_visita, estado_general, observaciones, sugerencias)
            // VALUES (?, ?, ?, ?, ?, ?)
            
            JOptionPane.showMessageDialog(panel,
                "¡Visita registrada exitosamente!\n\n" +
                "Gato: " + gato.nombreGato + "\n" +
                "Familia: " + gato.nombreFamilia + "\n" +
                "Fecha: " + fechaVisita + "\n" +
                "Estado: " + estadoGeneral + "\n\n" +
                "(Funcionalidad de guardado pendiente de implementación)",
                "Visita registrada",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Volver al formulario inicial
            mostrarFormularioRegistrarVisita();
        });
        panelBotones.add(botonRegistrar);
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);
        
        return panel;
    }

    // TODO: BASE DE DATOS - Reemplazar con consulta real
    // Query sugerida ver arriba en crearPanelSeleccionGato()
    private List<GatoAsignadoInfo> obtenerGatosAsignados() {
        List<GatoAsignadoInfo> lista = new ArrayList<>();
        
        // DATOS SIMULADOS PARA PRUEBAS - Reemplazar con consulta real
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

    /**
     * Clase auxiliar para información de gatos asignados a familias
     */
    private static class GatoAsignadoInfo {
        Integer idGato;
        String nombreGato;
        Integer idFamilia;
        String nombreFamilia;
        String direccionFamilia;
        String telefonoFamilia;
        String reputacionFamilia;
        String tipoAsignacion;
        LocalDate fechaAsignacion;
        LocalDate fechaUltimaVisita;
        
        GatoAsignadoInfo(Integer idGato, String nombreGato, Integer idFamilia,
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
    
    // ==================== MÓDULO: CALENDARIO DE TAREAS ====================
    
    /**
     * Muestra el calendario mensual de tareas del voluntario
     */
    private void mostrarCalendarioDeTareas() {
        panelCalendario = new JPanel(new BorderLayout(10, 10));
        panelCalendario.setBackground(new Color(102, 102, 102));
        panelCalendario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Calendario de Tareas");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelCalendario.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel central con el calendario
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(102, 102, 102));
        
        // Panel de navegación del mes
        JPanel panelNavegacion = crearPanelNavegacion();
        panelCentral.add(panelNavegacion, BorderLayout.NORTH);
        
        // Panel del calendario con los días
        JPanel panelDias = crearPanelCalendario(YearMonth.now());
        panelCentral.add(panelDias, BorderLayout.CENTER);
        
        panelCalendario.add(panelCentral, BorderLayout.CENTER);
        
        // Leyenda de colores
        JPanel panelLeyenda = crearPanelLeyenda();
        panelCalendario.add(panelLeyenda, BorderLayout.SOUTH);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalVoluntario.removeAll();
        ContenedorPrincipalVoluntario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelCalendario);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVoluntario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVoluntario.revalidate();
        ContenedorPrincipalVoluntario.repaint();
    }
    
    /**
     * Crea el panel de navegación entre meses
     */
    private JPanel crearPanelNavegacion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(102, 102, 102));
        
        YearMonth[] mesActual = {YearMonth.now()};
        
        JButton btnAnterior = new JButton("◀ Mes Anterior");
        btnAnterior.setFont(new Font("Aptos", Font.PLAIN, 16));
        btnAnterior.setBackground(new Color(0, 102, 153));
        btnAnterior.setForeground(Color.WHITE);
        btnAnterior.setBorderPainted(false);
        btnAnterior.setPreferredSize(new Dimension(150, 40));
        
        JLabel labelMes = new JLabel(obtenerNombreMesAnio(mesActual[0]));
        labelMes.setFont(new Font("Aptos", Font.BOLD, 24));
        labelMes.setForeground(Color.WHITE);
        labelMes.setPreferredSize(new Dimension(250, 40));
        labelMes.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton btnSiguiente = new JButton("Mes Siguiente ▶");
        btnSiguiente.setFont(new Font("Aptos", Font.PLAIN, 16));
        btnSiguiente.setBackground(new Color(0, 102, 153));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setPreferredSize(new Dimension(150, 40));
        
        btnAnterior.addActionListener(e -> {
            mesActual[0] = mesActual[0].minusMonths(1);
            labelMes.setText(obtenerNombreMesAnio(mesActual[0]));
            actualizarCalendario(mesActual[0]);
        });
        
        btnSiguiente.addActionListener(e -> {
            mesActual[0] = mesActual[0].plusMonths(1);
            labelMes.setText(obtenerNombreMesAnio(mesActual[0]));
            actualizarCalendario(mesActual[0]);
        });
        
        panel.add(btnAnterior);
        panel.add(labelMes);
        panel.add(btnSiguiente);
        
        return panel;
    }
    
    /**
     * Crea el panel del calendario con los días del mes
     */
    private JPanel crearPanelCalendario(YearMonth mesAnio) {
        JPanel panel = new JPanel(new GridLayout(0, 7, 5, 5));
        panel.setBackground(new Color(102, 102, 102));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Encabezado - Nombres de los días de la semana
        String[] diasSemana = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (String dia : diasSemana) {
            JLabel label = new JLabel(dia, SwingConstants.CENTER);
            label.setFont(new Font("Aptos", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBackground(new Color(51, 51, 51));
            label.setPreferredSize(new Dimension(100, 40));
            panel.add(label);
        }
        
        // TODO: BASE DE DATOS - Obtener tareas del mes
        // Query: SELECT t.*, g.nombre as nombre_gato
        //        FROM Tareas t JOIN Gatos g ON t.id_gato = g.id
        //        WHERE t.id_voluntario = ? AND MONTH(t.fecha_asignacion) = ? AND YEAR(t.fecha_asignacion) = ?
        Map<LocalDate, List<TareaInfo>> tareasDelMes = obtenerTareasDelMes(mesAnio);
        
        // Calcular primer día del mes y días totales
        LocalDate primerDia = mesAnio.atDay(1);
        int diaSemana = primerDia.getDayOfWeek().getValue() % 7; // 0=Dom, 6=Sáb
        int diasEnMes = mesAnio.lengthOfMonth();
        
        // Celdas vacías antes del primer día
        for (int i = 0; i < diaSemana; i++) {
            JPanel celdaVacia = new JPanel();
            celdaVacia.setBackground(new Color(102, 102, 102));
            panel.add(celdaVacia);
        }
        
        // Celdas con días
        for (int dia = 1; dia <= diasEnMes; dia++) {
            LocalDate fecha = mesAnio.atDay(dia);
            JPanel celdaDia = crearCeldaDia(fecha, tareasDelMes.get(fecha));
            panel.add(celdaDia);
        }
        
        return panel;
    }
    
    /**
     * Crea una celda individual del calendario para un día específico
     */
    private JPanel crearCeldaDia(LocalDate fecha, List<TareaInfo> tareas) {
        JPanel celda = new JPanel(new BorderLayout());
        celda.setPreferredSize(new Dimension(100, 80));
        celda.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        
        // Determinar color de fondo según el estado de las tareas
        Color colorFondo = new Color(204, 204, 204); // Gris claro por defecto (sin tareas)
        if (tareas != null && !tareas.isEmpty()) {
            boolean tienePendientes = tareas.stream().anyMatch(t -> "PENDIENTE".equals(t.estado));
            boolean tieneFinalizadas = tareas.stream().anyMatch(t -> "FINALIZADA".equals(t.estado));
            
            if (tienePendientes) {
                colorFondo = new Color(255, 200, 100); // Naranja para pendientes
            } else if (tieneFinalizadas) {
                colorFondo = new Color(144, 238, 144); // Verde claro para finalizadas
            }
        }
        
        // Resaltar el día actual
        if (fecha.equals(LocalDate.now())) {
            colorFondo = colorFondo.darker();
            celda.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 3));
        }
        
        celda.setBackground(colorFondo);
        
        // Número del día
        JLabel labelDia = new JLabel(String.valueOf(fecha.getDayOfMonth()), SwingConstants.CENTER);
        labelDia.setFont(new Font("Aptos", Font.BOLD, 18));
        labelDia.setForeground(Color.BLACK);
        celda.add(labelDia, BorderLayout.NORTH);
        
        // Indicador de cantidad de tareas
        if (tareas != null && !tareas.isEmpty()) {
            long pendientes = tareas.stream().filter(t -> "PENDIENTE".equals(t.estado)).count();
            long finalizadas = tareas.stream().filter(t -> "FINALIZADA".equals(t.estado)).count();
            
            JLabel labelTareas = new JLabel();
            labelTareas.setFont(new Font("Aptos", Font.PLAIN, 12));
            labelTareas.setForeground(Color.BLACK);
            labelTareas.setHorizontalAlignment(SwingConstants.CENTER);
            
            if (pendientes > 0) {
                labelTareas.setText(pendientes + " pendiente" + (pendientes > 1 ? "s" : ""));
            } else if (finalizadas > 0) {
                labelTareas.setText(finalizadas + " finalizada" + (finalizadas > 1 ? "s" : ""));
            }
            
            celda.add(labelTareas, BorderLayout.CENTER);
        }
        
        // Hacer clic para ver detalles
        celda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        celda.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tareas != null && !tareas.isEmpty()) {
                    mostrarDetallesTareas(fecha, tareas);
                }
            }
        });
        
        return celda;
    }
    
    /**
     * Muestra un diálogo con los detalles de las tareas de un día específico
     */
    private void mostrarDetallesTareas(LocalDate fecha, List<TareaInfo> tareas) {
        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        JDialog dialogo = new JDialog(ventanaPadre, "Tareas del " + fecha.toString(), Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(600, 400);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBackground(new Color(102, 102, 102));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("Tareas para el " + fecha.toString());
        titulo.setFont(new Font("Aptos", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenido.add(titulo, BorderLayout.NORTH);
        
        // Panel con lista de tareas
        JPanel panelTareas = new JPanel();
        panelTareas.setLayout(new BoxLayout(panelTareas, BoxLayout.Y_AXIS));
        panelTareas.setBackground(new Color(102, 102, 102));
        
        for (TareaInfo tarea : tareas) {
            JPanel panelTarea = crearPanelTarea(tarea, dialogo);
            panelTareas.add(panelTarea);
            panelTareas.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        JScrollPane scrollPane = new JScrollPane(panelTareas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        panelContenido.add(scrollPane, BorderLayout.CENTER);
        
        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Aptos", Font.PLAIN, 16));
        btnCerrar.setBackground(new Color(0, 102, 153));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setPreferredSize(new Dimension(100, 40));
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(102, 102, 102));
        panelBoton.add(btnCerrar);
        panelContenido.add(panelBoton, BorderLayout.SOUTH);
        
        dialogo.add(panelContenido);
        dialogo.setVisible(true);
    }
    
    /**
     * Crea el panel de una tarea individual dentro del diálogo de detalles
     */
    private JPanel crearPanelTarea(TareaInfo tarea, JDialog dialogoPadre) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(204, 204, 204));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Información de la tarea
        JPanel panelInfo = new JPanel(new GridLayout(0, 1, 5, 5));
        panelInfo.setBackground(new Color(204, 204, 204));
        
        JLabel labelDescripcion = new JLabel("Descripción: " + tarea.descripcion);
        labelDescripcion.setFont(new Font("Aptos", Font.BOLD, 14));
        labelDescripcion.setForeground(Color.BLACK);
        
        JLabel labelTipo = new JLabel("Tipo: " + tarea.tipo);
        labelTipo.setFont(new Font("Aptos", Font.PLAIN, 13));
        labelTipo.setForeground(Color.BLACK);
        
        JLabel labelGato = new JLabel("Gato: " + tarea.nombreGato);
        labelGato.setFont(new Font("Aptos", Font.PLAIN, 13));
        labelGato.setForeground(Color.BLACK);
        
        JLabel labelEstado = new JLabel("Estado: " + tarea.estado);
        labelEstado.setFont(new Font("Aptos", Font.BOLD, 13));
        labelEstado.setForeground("PENDIENTE".equals(tarea.estado) ? 
                                  new Color(200, 100, 0) : new Color(0, 150, 0));
        
        panelInfo.add(labelDescripcion);
        panelInfo.add(labelTipo);
        panelInfo.add(labelGato);
        panelInfo.add(labelEstado);
        
        panel.add(panelInfo, BorderLayout.CENTER);
        
        // Botón finalizar si está pendiente
        if ("PENDIENTE".equals(tarea.estado)) {
            JButton btnFinalizar = new JButton("Finalizar Tarea");
            btnFinalizar.setFont(new Font("Aptos", Font.BOLD, 14));
            btnFinalizar.setBackground(new Color(0, 102, 153));
            btnFinalizar.setForeground(Color.WHITE);
            btnFinalizar.setBorderPainted(false);
            btnFinalizar.setPreferredSize(new Dimension(150, 35));
            
            btnFinalizar.addActionListener(e -> {
                int confirmacion = JOptionPane.showConfirmDialog(dialogoPadre,
                    "¿Está seguro que desea finalizar esta tarea?",
                    "Confirmar finalización",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    // TODO: BASE DE DATOS - Actualizar estado de la tarea
                    // UPDATE Tareas SET estado = 'FINALIZADA' WHERE id_tarea = ?
                    tarea.estado = "FINALIZADA";
                    
                    JOptionPane.showMessageDialog(dialogoPadre,
                        "Tarea finalizada exitosamente\n(Funcionalidad de guardado pendiente de implementación)",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Cerrar diálogo y actualizar calendario
                    dialogoPadre.dispose();
                    actualizarCalendario(YearMonth.from(tarea.fechaAsignacion));
                }
            });
            
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBoton.setBackground(new Color(204, 204, 204));
            panelBoton.add(btnFinalizar);
            panel.add(panelBoton, BorderLayout.EAST);
        }
        
        return panel;
    }
    
    /**
     * Crea el panel de leyenda con los colores del calendario
     */
    private JPanel crearPanelLeyenda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setBackground(new Color(102, 102, 102));
        
        JLabel labelTitulo = new JLabel("Leyenda: ");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 16));
        labelTitulo.setForeground(Color.WHITE);
        panel.add(labelTitulo);
        
        // Tarea pendiente
        JPanel panelPendiente = new JPanel();
        panelPendiente.setBackground(new Color(255, 200, 100));
        panelPendiente.setPreferredSize(new Dimension(30, 30));
        panelPendiente.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        panel.add(panelPendiente);
        
        JLabel labelPendiente = new JLabel("Tarea Pendiente");
        labelPendiente.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelPendiente.setForeground(Color.WHITE);
        panel.add(labelPendiente);
        
        // Tarea finalizada
        JPanel panelFinalizada = new JPanel();
        panelFinalizada.setBackground(new Color(144, 238, 144));
        panelFinalizada.setPreferredSize(new Dimension(30, 30));
        panelFinalizada.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        panel.add(panelFinalizada);
        
        JLabel labelFinalizada = new JLabel("Tarea Finalizada");
        labelFinalizada.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelFinalizada.setForeground(Color.WHITE);
        panel.add(labelFinalizada);
        
        // Día actual
        JPanel panelHoy = new JPanel();
        panelHoy.setBackground(new Color(153, 153, 153));
        panelHoy.setPreferredSize(new Dimension(30, 30));
        panelHoy.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 3));
        panel.add(panelHoy);
        
        JLabel labelHoy = new JLabel("Día Actual");
        labelHoy.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelHoy.setForeground(Color.WHITE);
        panel.add(labelHoy);
        
        return panel;
    }
    
    /**
     * Actualiza el calendario para mostrar un mes diferente
     */
    private void actualizarCalendario(YearMonth mesAnio) {
        if (ContenedorPrincipalVoluntario.getComponentCount() == 0) {
            return;
        }
        
        Component comp = ContenedorPrincipalVoluntario.getComponent(0);
        if (!(comp instanceof JScrollPane)) {
            return;
        }
        
        JScrollPane scrollPane = (JScrollPane) comp;
        Component view = scrollPane.getViewport().getView();
        
        if (!(view instanceof Container)) {
            return;
        }
        
        Container contenedor = (Container) view;
        Component[] componentes = contenedor.getComponents();
        
        for (Component c : componentes) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                Component[] subComps = panel.getComponents();
                
                for (int i = 0; i < subComps.length; i++) {
                    if (subComps[i] instanceof JPanel) {
                        JPanel subPanel = (JPanel) subComps[i];
                        Component[] subSubComps = subPanel.getComponents();
                        
                        // Buscar el panel de días (segundo componente del panel central)
                        for (int j = 0; j < subSubComps.length; j++) {
                            if (subSubComps[j] instanceof JPanel && j == 1) {
                                JPanel panelDias = (JPanel) subSubComps[j];
                                
                                // Reemplazar con nuevo calendario
                                Container parent = panelDias.getParent();
                                parent.remove(panelDias);
                                parent.add(crearPanelCalendario(mesAnio), BorderLayout.CENTER);
                                parent.revalidate();
                                parent.repaint();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Obtiene el nombre del mes y año en español
     */
    private String obtenerNombreMesAnio(YearMonth mesAnio) {
        String mes = mesAnio.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        return mes.substring(0, 1).toUpperCase() + mes.substring(1) + " " + mesAnio.getYear();
    }
    
    // TODO: BASE DE DATOS - Reemplazar con consulta real
    // Query sugerida ver arriba en crearPanelCalendario()
    private Map<LocalDate, List<TareaInfo>> obtenerTareasDelMes(YearMonth mesAnio) {
        Map<LocalDate, List<TareaInfo>> tareas = new HashMap<>();
        
        // DATOS SIMULADOS PARA PRUEBAS - Reemplazar con consulta real
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
     * Clase auxiliar para información de tareas
     */
    private static class TareaInfo {
        Integer idTarea;
        String descripcion;
        String tipo;
        String estado;
        LocalDate fechaAsignacion;
        String nombreGato;
        
        TareaInfo(Integer idTarea, String descripcion, String tipo, String estado, 
                 LocalDate fechaAsignacion, String nombreGato) {
            this.idTarea = idTarea;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.estado = estado;
            this.fechaAsignacion = fechaAsignacion;
            this.nombreGato = nombreGato;
        }
    }
    
    // ==================== MÓDULO: REGISTRAR GATO ====================
    
    /**
     * Muestra el formulario para registrar un nuevo gato en el sistema
     */
    private void mostrarFormularioRegistrarGato() {
        formularioRegistrarGato = new JPanel();
        formularioRegistrarGato.setBackground(new Color(102, 102, 102));
        formularioRegistrarGato.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ========== Título ==========
        JLabel labelTitulo = new JLabel("Registrar Gato");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(30, 20, 30, 20);
        formularioRegistrarGato.add(labelTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 10, 20);
        
        // ========== Campo Nombre (obligatorio) ==========
        JLabel labelNombre = new JLabel("Nombre: *");
        labelNombre.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formularioRegistrarGato.add(labelNombre, gbc);
        
        JTextField textFieldNombre = new JTextField();
        textFieldNombre.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldNombre.setBackground(new Color(204, 204, 204));
        textFieldNombre.setForeground(Color.BLACK);
        textFieldNombre.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        formularioRegistrarGato.add(textFieldNombre, gbc);
        
        // ========== Campo Color ==========
        gbc.gridwidth = 1;
        JLabel labelColor = new JLabel("Color:");
        labelColor.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelColor.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formularioRegistrarGato.add(labelColor, gbc);
        
        JTextField textFieldColor = new JTextField();
        textFieldColor.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldColor.setBackground(new Color(204, 204, 204));
        textFieldColor.setForeground(Color.BLACK);
        textFieldColor.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        formularioRegistrarGato.add(textFieldColor, gbc);
        
        // ========== Campo Características ==========
        gbc.gridwidth = 1;
        JLabel labelCaracteristicas = new JLabel("Características:");
        labelCaracteristicas.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelCaracteristicas.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        formularioRegistrarGato.add(labelCaracteristicas, gbc);
        
        JTextArea textAreaCaracteristicas = new JTextArea(5, 20);
        textAreaCaracteristicas.setFont(new Font("Aptos", Font.PLAIN, 16));
        textAreaCaracteristicas.setBackground(new Color(204, 204, 204));
        textAreaCaracteristicas.setForeground(Color.BLACK);
        textAreaCaracteristicas.setLineWrap(true);
        textAreaCaracteristicas.setWrapStyleWord(true);
        JScrollPane scrollCaracteristicas = new JScrollPane(textAreaCaracteristicas);
        scrollCaracteristicas.setPreferredSize(new Dimension(300, 100));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        formularioRegistrarGato.add(scrollCaracteristicas, gbc);
        
        // ========== Campo Estado de Salud ==========
        gbc.gridwidth = 1;
        JLabel labelEstadoSalud = new JLabel("Estado de Salud:");
        labelEstadoSalud.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelEstadoSalud.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formularioRegistrarGato.add(labelEstadoSalud, gbc);
        
        JTextField textFieldEstadoSalud = new JTextField();
        textFieldEstadoSalud.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldEstadoSalud.setBackground(new Color(204, 204, 204));
        textFieldEstadoSalud.setForeground(Color.BLACK);
        textFieldEstadoSalud.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        formularioRegistrarGato.add(textFieldEstadoSalud, gbc);
        
        // ========== Campo Edad Aproximada ==========
        gbc.gridwidth = 1;
        JLabel labelEdad = new JLabel("Edad Aproximada (meses):");
        labelEdad.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelEdad.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        formularioRegistrarGato.add(labelEdad, gbc);
        
        JTextField textFieldEdad = new JTextField();
        textFieldEdad.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldEdad.setBackground(new Color(204, 204, 204));
        textFieldEdad.setForeground(Color.BLACK);
        textFieldEdad.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        formularioRegistrarGato.add(textFieldEdad, gbc);
        
        // ========== Campo Foto ==========
        gbc.gridwidth = 1;
        JLabel labelFoto = new JLabel("Foto (ruta):");
        labelFoto.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelFoto.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        formularioRegistrarGato.add(labelFoto, gbc);
        
        JTextField textFieldFoto = new JTextField();
        textFieldFoto.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldFoto.setBackground(new Color(204, 204, 204));
        textFieldFoto.setForeground(Color.BLACK);
        textFieldFoto.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 0.5;
        formularioRegistrarGato.add(textFieldFoto, gbc);
        
        JButton botonSeleccionarFoto = new JButton("Seleccionar");
        botonSeleccionarFoto.setFont(new Font("Aptos", Font.PLAIN, 14));
        botonSeleccionarFoto.setBackground(new Color(0, 102, 153));
        botonSeleccionarFoto.setForeground(Color.WHITE);
        botonSeleccionarFoto.setBorderPainted(false);
        botonSeleccionarFoto.setPreferredSize(new Dimension(100, 35));
        botonSeleccionarFoto.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Imágenes", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(formularioRegistrarGato);
            if (result == JFileChooser.APPROVE_OPTION) {
                textFieldFoto.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.weightx = 0.2;
        formularioRegistrarGato.add(botonSeleccionarFoto, gbc);
        
        // ========== Checkbox Generar Código QR ==========
        gbc.gridwidth = 1;
        JLabel labelCodigoQR = new JLabel("Generar Código QR:");
        labelCodigoQR.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelCodigoQR.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.3;
        formularioRegistrarGato.add(labelCodigoQR, gbc);

        JCheckBox checkBoxGenerarQR = new JCheckBox("Generar código QR automáticamente");
        checkBoxGenerarQR.setFont(new Font("Aptos", Font.PLAIN, 16));
        checkBoxGenerarQR.setForeground(Color.WHITE);
        checkBoxGenerarQR.setBackground(new Color(102, 102, 102));
        checkBoxGenerarQR.setSelected(true); // Marcado por defecto
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        formularioRegistrarGato.add(checkBoxGenerarQR, gbc);
        
        // ========== Panel de Botones ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 45));
        botonCancelar.addActionListener(e -> {
            // Limpiar todos los campos del formulario
            textFieldNombre.setText("");
            textFieldColor.setText("");
            textAreaCaracteristicas.setText("");
            textFieldEstadoSalud.setText("");
            textFieldEdad.setText("");
            textFieldFoto.setText("");
        });
        panelBotones.add(botonCancelar);
        
        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonRegistrar.setBackground(new Color(0, 102, 153));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setBorderPainted(false);
        botonRegistrar.setPreferredSize(new Dimension(150, 45));
        botonRegistrar.addActionListener(e -> {
            // Validar nombre obligatorio
            if (textFieldNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(formularioRegistrarGato,
                        "El nombre del gato es obligatorio",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar edad si se ingresó
            Integer edadMeses = null;
            if (!textFieldEdad.getText().trim().isEmpty()) {
                try {
                    edadMeses = Integer.parseInt(textFieldEdad.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formularioRegistrarGato,
                            "La edad debe ser un número válido",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String nombreGato = textFieldNombre.getText().trim();
            String colorGato = textFieldColor.getText().trim();
            String caracteristicas = textAreaCaracteristicas.getText().trim();
            String estadoSalud = textFieldEstadoSalud.getText().trim();
            String rutaFoto = textFieldFoto.getText().trim();
            boolean generarQR = checkBoxGenerarQR.isSelected();

            // TODO: BASE DE DATOS - Guardar gato en la base de datos
            // INSERT INTO Gatos (nombre, color, caracteristicas, estado_salud, edad_aproximada, foto, codigo_qr)
            // VALUES (?, ?, ?, ?, ?, ?, ?)
            // Integer idGatoRegistrado = guardarGatoEnBD(nombreGato, colorGato, caracteristicas, estadoSalud, edadMeses, rutaFoto);

            // Simular ID del gato registrado (en producción vendría de la BD)
            Integer idGatoRegistrado = new java.util.Random().nextInt(10000);

            JOptionPane.showMessageDialog(formularioRegistrarGato,
                    "¡Gato registrado exitosamente!\n\n" +
                    "Nombre: " + nombreGato + "\n" +
                    "ID asignado: " + idGatoRegistrado + "\n\n" +
                    "(Funcionalidad de guardado pendiente de implementación)",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Generar y descargar código QR si está marcado
            if (generarQR) {
                generarYDescargarCodigoQR(idGatoRegistrado, nombreGato);
            }

            // Limpiar formulario después de registrar
            textFieldNombre.setText("");
            textFieldColor.setText("");
            textAreaCaracteristicas.setText("");
            textFieldEstadoSalud.setText("");
            textFieldEdad.setText("");
            textFieldFoto.setText("");
            checkBoxGenerarQR.setSelected(true);
        });
        panelBotones.add(botonRegistrar);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(30, 20, 30, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        formularioRegistrarGato.add(panelBotones, gbc);
        
        // Mostrar el formulario en el contenedor principal
        ContenedorPrincipalVoluntario.removeAll();
        ContenedorPrincipalVoluntario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(formularioRegistrarGato);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVoluntario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVoluntario.revalidate();
        ContenedorPrincipalVoluntario.repaint();
    }
    
    // ==================== MÓDULO: GENERACIÓN DE CÓDIGO QR ====================

/**
 * Genera un código QR para un gato y permite descargarlo
 */
private void generarYDescargarCodigoQR(Integer idGato, String nombreGato) {
    try {
        // Contenido del código QR - Puedes personalizarlo según necesites
        // Por ejemplo, puede ser una URL, JSON con datos, o simplemente el ID
        String contenidoQR = String.format(
            "GATO_ID:%d\nNOMBRE:%s\nSISTEMA:Colonia Felina\nFECHA:%s",
            idGato,
            nombreGato,
            java.time.LocalDate.now()
        );
        
        // Generar el código QR
        int tamano = 400; // 400x400 pixels
        java.awt.image.BufferedImage imagenQR = generarImagenQR(contenidoQR, tamano, tamano);
        
        // Preguntar al usuario dónde guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Código QR");
        
        // Nombre sugerido para el archivo
        String nombreArchivo = "QR_" + nombreGato.replaceAll("\\s+", "_") + "_" + idGato + ".png";
        fileChooser.setSelectedFile(new java.io.File(nombreArchivo));
        
        // Filtro para PNG
        javax.swing.filechooser.FileNameExtensionFilter filter = 
            new javax.swing.filechooser.FileNameExtensionFilter("Imágenes PNG (*.png)", "png");
        fileChooser.setFileFilter(filter);
        
        int resultado = fileChooser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            
            // Asegurar extensión .png
            if (!archivo.getName().toLowerCase().endsWith(".png")) {
                archivo = new java.io.File(archivo.getAbsolutePath() + ".png");
            }
            
            // Guardar la imagen
            javax.imageio.ImageIO.write(imagenQR, "PNG", archivo);
            
            // TODO: BASE DE DATOS - Guardar referencia del QR en la base de datos
            // UPDATE Gatos SET codigo_qr = ? WHERE id = ?
            // String rutaQR = archivo.getAbsolutePath();
            // guardarRutaQREnBD(idGato, rutaQR);
            
            // Mostrar confirmación con opción de abrir
            int abrir = JOptionPane.showConfirmDialog(this,
                "✅ Código QR generado exitosamente\n\n" +
                "Ubicación: " + archivo.getAbsolutePath() + "\n\n" +
                "¿Desea abrir la imagen?",
                "QR Generado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            
            if (abrir == JOptionPane.YES_OPTION) {
                abrirArchivo(archivo);
            }
        }
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Error al generar el código QR:\n" + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

/**
 * Genera una imagen BufferedImage con el código QR
 */
private java.awt.image.BufferedImage generarImagenQR(String contenido, int ancho, int alto) 
        throws com.google.zxing.WriterException {
    
    // Configurar el generador de QR
    com.google.zxing.qrcode.QRCodeWriter qrCodeWriter = new com.google.zxing.qrcode.QRCodeWriter();
    
    // Configurar parámetros del QR
    java.util.Map<com.google.zxing.EncodeHintType, Object> hints = new java.util.HashMap<>();
    hints.put(com.google.zxing.EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION, 
              com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);
    hints.put(com.google.zxing.EncodeHintType.MARGIN, 2);
    
    // Generar la matriz de bits del QR
    com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(
        contenido, 
        com.google.zxing.BarcodeFormat.QR_CODE, 
        ancho, 
        alto, 
        hints
    );
    
    // Convertir la matriz de bits a imagen BufferedImage
    java.awt.image.BufferedImage imagen = new java.awt.image.BufferedImage(
        ancho, 
        alto, 
        java.awt.image.BufferedImage.TYPE_INT_RGB
    );
    
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            // Negro para los bits activos, blanco para los inactivos
            imagen.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        }
    }
    
    return imagen;
}

/**
 * Abre un archivo con la aplicación predeterminada del sistema
 */
private void abrirArchivo(java.io.File archivo) {
    try {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (archivo.exists()) {
                desktop.open(archivo);
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "No se pudo abrir el archivo automáticamente.\n" +
            "Por favor, ábralo manualmente desde: " + archivo.getAbsolutePath(),
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
}

    // ==================== MÓDULO: ASIGNAR GATO A FAMILIA ====================
    
    /**
     * Muestra el formulario inicial para asignar un gato a una familia
     */
    private void mostrarFormularioAsignarGatoAFamilia() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Asignar Gato a Familia");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel del formulario inicial
        JPanel panelFormulario = crearFormularioAsignacionInicial();
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalVoluntario.removeAll();
        ContenedorPrincipalVoluntario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVoluntario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVoluntario.revalidate();
        ContenedorPrincipalVoluntario.repaint();
    }

    /**
     * Crea el formulario inicial de asignación con campos ID gato, ID familia, fecha y tipo
     */
    private JPanel crearFormularioAsignacionInicial() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ========== ID del Gato ==========
        JLabel labelIdGato = new JLabel("ID del Gato: *");
        labelIdGato.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelIdGato.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(labelIdGato, gbc);
        
        JTextField textFieldIdGato = new JTextField();
        textFieldIdGato.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldIdGato.setBackground(new Color(204, 204, 204));
        textFieldIdGato.setForeground(Color.BLACK);
        textFieldIdGato.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panel.add(textFieldIdGato, gbc);
        
        // ========== ID de la Familia ==========
        JLabel labelIdFamilia = new JLabel("ID de la Familia: *");
        labelIdFamilia.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelIdFamilia.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panel.add(labelIdFamilia, gbc);
        
        JTextField textFieldIdFamilia = new JTextField();
        textFieldIdFamilia.setFont(new Font("Aptos", Font.PLAIN, 16));
        textFieldIdFamilia.setBackground(new Color(204, 204, 204));
        textFieldIdFamilia.setForeground(Color.BLACK);
        textFieldIdFamilia.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        panel.add(textFieldIdFamilia, gbc);
        
        // ========== Fecha de Asignación ==========
        JLabel labelFecha = new JLabel("Fecha de Asignación: *");
        labelFecha.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelFecha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panel.add(labelFecha, gbc);
        
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setBackground(new Color(102, 102, 102));
        
        // Selectores de fecha
        JComboBox<Integer> comboDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            comboDia.addItem(i);
        }
        comboDia.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboDia.setBackground(new Color(204, 204, 204));
        comboDia.setPreferredSize(new Dimension(70, 35));
        
        JComboBox<String> comboMes = new JComboBox<>(new String[]{
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        comboMes.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboMes.setBackground(new Color(204, 204, 204));
        comboMes.setPreferredSize(new Dimension(130, 35));
        
        JComboBox<Integer> comboAnio = new JComboBox<>();
        int anioActual = LocalDate.now().getYear();
        for (int i = anioActual - 1; i <= anioActual + 1; i++) {
            comboAnio.addItem(i);
        }
        comboAnio.setSelectedItem(anioActual);
        comboAnio.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboAnio.setBackground(new Color(204, 204, 204));
        comboAnio.setPreferredSize(new Dimension(90, 35));
        
        // Establecer fecha actual por defecto
        LocalDate hoy = LocalDate.now();
        comboDia.setSelectedItem(hoy.getDayOfMonth());
        comboMes.setSelectedIndex(hoy.getMonthValue() - 1);
        
        panelFecha.add(comboDia);
        panelFecha.add(comboMes);
        panelFecha.add(comboAnio);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        panel.add(panelFecha, gbc);
        
        // ========== Tipo de Asignación ==========
        JLabel labelTipo = new JLabel("Tipo de Asignación: *");
        labelTipo.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelTipo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panel.add(labelTipo, gbc);
        
        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelTipo.setBackground(new Color(102, 102, 102));
        
        ButtonGroup grupoTipo = new ButtonGroup();
        JRadioButton radioTransito = new JRadioButton("Tránsito");
        radioTransito.setFont(new Font("Aptos", Font.PLAIN, 16));
        radioTransito.setForeground(Color.WHITE);
        radioTransito.setBackground(new Color(102, 102, 102));
        radioTransito.setSelected(true);
        
        JRadioButton radioAdopcion = new JRadioButton("Adopción Definitiva");
        radioAdopcion.setFont(new Font("Aptos", Font.PLAIN, 16));
        radioAdopcion.setForeground(Color.WHITE);
        radioAdopcion.setBackground(new Color(102, 102, 102));
        
        grupoTipo.add(radioTransito);
        grupoTipo.add(radioAdopcion);
        panelTipo.add(radioTransito);
        panelTipo.add(radioAdopcion);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        panel.add(panelTipo, gbc);
        
        // ========== Panel de Botones ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 45));
        botonCancelar.addActionListener(e -> {
            textFieldIdGato.setText("");
            textFieldIdFamilia.setText("");
            comboDia.setSelectedItem(hoy.getDayOfMonth());
            comboMes.setSelectedIndex(hoy.getMonthValue() - 1);
            comboAnio.setSelectedItem(anioActual);
            radioTransito.setSelected(true);
        });
        panelBotones.add(botonCancelar);
        
        JButton botonAsignar = new JButton("Asignar");
        botonAsignar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonAsignar.setBackground(new Color(0, 102, 153));
        botonAsignar.setForeground(Color.WHITE);
        botonAsignar.setBorderPainted(false);
        botonAsignar.setPreferredSize(new Dimension(150, 45));
        botonAsignar.addActionListener(e -> {
            // Validaciones
            if (textFieldIdGato.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "El ID del gato es obligatorio",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (textFieldIdFamilia.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "El ID de la familia es obligatorio",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Integer idGato;
            Integer idFamilia;
            try {
                idGato = Integer.parseInt(textFieldIdGato.getText().trim());
                idFamilia = Integer.parseInt(textFieldIdFamilia.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel,
                    "Los IDs deben ser números válidos",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener fecha seleccionada
            LocalDate fechaAsignacion = LocalDate.of(
                (Integer) comboAnio.getSelectedItem(),
                comboMes.getSelectedIndex() + 1,
                (Integer) comboDia.getSelectedItem()
            );
            
            // Obtener tipo de asignación
            String tipoAsignacion = radioTransito.isSelected() ? "Tránsito" : "Adopción Definitiva";
            
            // Mostrar resumen
            mostrarResumenAsignacion(idGato, idFamilia, fechaAsignacion, tipoAsignacion);
        });
        panelBotones.add(botonAsignar);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 20, 20, 20);
        panel.add(panelBotones, gbc);
        
        return panel;
    }

    /**
     * Muestra el resumen de la asignación con los datos del gato y la familia
     */
    private void mostrarResumenAsignacion(Integer idGato, Integer idFamilia, 
                                          LocalDate fechaAsignacion, String tipoAsignacion) {
        // TODO: BASE DE DATOS - Obtener datos reales del gato y la familia
        // Query Gato: SELECT * FROM Gatos WHERE id = ?
        // Query Familia: SELECT * FROM Familias WHERE id = ?
        DatosGatoResumen gato = obtenerDatosGato(idGato);
        DatosFamiliaResumen familia = obtenerDatosFamilia(idFamilia);
        
        if (gato == null) {
            JOptionPane.showMessageDialog(ContenedorPrincipalVoluntario,
                "No se encontró el gato con ID: " + idGato,
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (familia == null) {
            JOptionPane.showMessageDialog(ContenedorPrincipalVoluntario,
                "No se encontró la familia con ID: " + idFamilia,
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear panel de resumen
        JPanel panelResumen = new JPanel(new BorderLayout(10, 10));
        panelResumen.setBackground(new Color(102, 102, 102));
        panelResumen.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Confirmar Asignación");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelResumen.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel central con la información
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        int fila = 0;
        
        // ========== Información del Gato ==========
        JLabel labelSeccionGato = new JLabel("═══ INFORMACIÓN DEL GATO ═══");
        labelSeccionGato.setFont(new Font("Aptos", Font.BOLD, 20));
        labelSeccionGato.setForeground(new Color(255, 200, 100));
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        panelInfo.add(labelSeccionGato, gbc);
        
        gbc.gridwidth = 1;
        
        agregarCampoResumen(panelInfo, gbc, fila++, "ID:", String.valueOf(gato.idGato));
        agregarCampoResumen(panelInfo, gbc, fila++, "Nombre:", gato.nombre);
        agregarCampoResumen(panelInfo, gbc, fila++, "Color:", gato.color);
        agregarCampoResumen(panelInfo, gbc, fila++, "Edad aproximada:", 
                           gato.edadAproximada != null ? gato.edadAproximada + " meses" : "No especificada");
        agregarCampoResumen(panelInfo, gbc, fila++, "Estado de salud:", gato.estadoDeSalud);
        
        // Espacio
        gbc.gridy = fila++;
        panelInfo.add(Box.createVerticalStrut(20), gbc);
        
        // ========== Información de la Familia ==========
        JLabel labelSeccionFamilia = new JLabel("═══ INFORMACIÓN DE LA FAMILIA ═══");
        labelSeccionFamilia.setFont(new Font("Aptos", Font.BOLD, 20));
        labelSeccionFamilia.setForeground(new Color(144, 238, 144));
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        panelInfo.add(labelSeccionFamilia, gbc);
        
        gbc.gridwidth = 1;
        
        agregarCampoResumen(panelInfo, gbc, fila++, "ID:", String.valueOf(familia.idFamilia));
        agregarCampoResumen(panelInfo, gbc, fila++, "Nombre:", familia.nombre);
        agregarCampoResumen(panelInfo, gbc, fila++, "Dirección:", familia.direccion);
        agregarCampoResumen(panelInfo, gbc, fila++, "Teléfono:", familia.telefono);
        agregarCampoResumen(panelInfo, gbc, fila++, "Estado:", familia.estado);
        agregarCampoResumen(panelInfo, gbc, fila++, "Disponibilidad:", familia.disponibilidad);
        
        // Espacio
        gbc.gridy = fila++;
        panelInfo.add(Box.createVerticalStrut(20), gbc);
        
        // ========== Información de la Asignación ==========
        JLabel labelSeccionAsignacion = new JLabel("═══ DETALLES DE LA ASIGNACIÓN ═══");
        labelSeccionAsignacion.setFont(new Font("Aptos", Font.BOLD, 20));
        labelSeccionAsignacion.setForeground(new Color(100, 180, 255));
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        panelInfo.add(labelSeccionAsignacion, gbc);
        
        gbc.gridwidth = 1;
        
        agregarCampoResumen(panelInfo, gbc, fila++, "Fecha:", fechaAsignacion.toString());
        agregarCampoResumen(panelInfo, gbc, fila++, "Tipo:", tipoAsignacion);
        
        JScrollPane scrollInfo = new JScrollPane(panelInfo);
        scrollInfo.setBorder(null);
        scrollInfo.setBackground(new Color(102, 102, 102));
        panelResumen.add(scrollInfo, BorderLayout.CENTER);
        
        // ========== Panel de Botones ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(150, 45));
        botonVolver.addActionListener(e -> mostrarFormularioAsignarGatoAFamilia());
        panelBotones.add(botonVolver);
        
        JButton botonConfirmar = new JButton("Confirmar Asignación");
        botonConfirmar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonConfirmar.setBackground(new Color(0, 150, 0));
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setBorderPainted(false);
        botonConfirmar.setPreferredSize(new Dimension(200, 45));
        botonConfirmar.addActionListener(e -> {
            // TODO: BASE DE DATOS - Guardar asignación en la base de datos
            // INSERT INTO Asignaciones (id_gato, id_familia, fecha_asignacion, tipo_asignacion, activa)
            // VALUES (?, ?, ?, ?, true)
            
            JOptionPane.showMessageDialog(ContenedorPrincipalVoluntario,
                "¡Gato asignado con éxito!\n\n" +
                "Gato: " + gato.nombre + "\n" +
                "Familia: " + familia.nombre + "\n" +
                "Tipo: " + tipoAsignacion + "\n" +
                "Fecha: " + fechaAsignacion + "\n\n" +
                "(Funcionalidad de guardado pendiente de implementación)",
                "Asignación exitosa",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Volver al formulario inicial limpio
            mostrarFormularioAsignarGatoAFamilia();
        });
        panelBotones.add(botonConfirmar);
        
        panelResumen.add(panelBotones, BorderLayout.SOUTH);
        
        // Mostrar el resumen
        ContenedorPrincipalVoluntario.removeAll();
        ContenedorPrincipalVoluntario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelResumen);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVoluntario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVoluntario.revalidate();
        ContenedorPrincipalVoluntario.repaint();
    }

    /**
     * Método auxiliar para agregar campos en el resumen de asignación
     */
    private void agregarCampoResumen(JPanel panel, GridBagConstraints gbc, int fila, 
                                    String etiqueta, String valor) {
        JLabel labelCampo = new JLabel(etiqueta);
        labelCampo.setFont(new Font("Aptos", Font.BOLD, 16));
        labelCampo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(labelCampo, gbc);
        
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Aptos", Font.PLAIN, 16));
        labelValor.setForeground(new Color(220, 220, 220));
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.weightx = 0.7;
        panel.add(labelValor, gbc);
    }

    // TODO: BASE DE DATOS - Reemplazar con consulta real
    // Query: SELECT * FROM Gatos WHERE id = ?
    private DatosGatoResumen obtenerDatosGato(Integer idGato) {
        // DATOS SIMULADOS PARA PRUEBAS
        if (idGato == 1) {
            return new DatosGatoResumen(1, "Michi", "Naranja", 12, "Saludable");
        } else if (idGato == 2) {
            return new DatosGatoResumen(2, "Pelusa", "Blanco", 8, "En recuperación");
        } else if (idGato == 3) {
            return new DatosGatoResumen(3, "Garfield", "Atigrado", 24, "Saludable");
        }
        return null;
    }

    // TODO: BASE DE DATOS - Reemplazar con consulta real
    // Query: SELECT * FROM Familias WHERE id = ?
    private DatosFamiliaResumen obtenerDatosFamilia(Integer idFamilia) {
        // DATOS SIMULADOS PARA PRUEBAS
        if (idFamilia == 1) {
            return new DatosFamiliaResumen(1, "Familia González", "Calle Principal 123", 
                                           "123-456-7890", "Activa", "Disponible");
        } else if (idFamilia == 2) {
            return new DatosFamiliaResumen(2, "Familia Martínez", "Av. Libertador 456", 
                                           "098-765-4321", "Activa", "Ocupada temporalmente");
        }
        return null;
    }

    /**
     * Clase auxiliar para datos de resumen de gato
     */
    private static class DatosGatoResumen {
        Integer idGato;
        String nombre;
        String color;
        Integer edadAproximada;
        String estadoDeSalud;
        
        DatosGatoResumen(Integer id, String nombre, String color, Integer edad, String estado) {
            this.idGato = id;
            this.nombre = nombre;
            this.color = color;
            this.edadAproximada = edad;
            this.estadoDeSalud = estado;
        }
    }

    /**
     * Clase auxiliar para datos de resumen de familia
     */
    private static class DatosFamiliaResumen {
        Integer idFamilia;
        String nombre;
        String direccion;
        String telefono;
        String estado;
        String disponibilidad;
        
        DatosFamiliaResumen(Integer id, String nombre, String direccion, 
                           String telefono, String estado, String disponibilidad) {
            this.idFamilia = id;
            this.nombre = nombre;
            this.direccion = direccion;
            this.telefono = telefono;
            this.estado = estado;
            this.disponibilidad = disponibilidad;
        }
    }
    
    // ==================== COMPONENTES GENERADOS POR NETBEANS ====================
    
    /**
     * Inicializa los componentes de la interfaz gráfica
     * ADVERTENCIA: Este código es generado automáticamente por NetBeans
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        OpcionesVoluntario = new javax.swing.JPanel();
        LabelUsuarioVoluntario = new javax.swing.JLabel();
        BotonCerrarSesionVoluntario = new javax.swing.JButton();
        BotonRegistrarPuntoDeAvistamiento = new javax.swing.JButton();
        BotonRegistarGato = new javax.swing.JButton();
        BotonAsignarGatoAFamilia = new javax.swing.JButton();
        BotonRegistrarVisitaDeSeguimiento = new javax.swing.JButton();
        BotonVerCalendarioDeTareas = new javax.swing.JButton();
        ContenedorPrincipalVoluntario = new javax.swing.JPanel();

        setBackground(new java.awt.Color(51, 51, 51));

        OpcionesVoluntario.setBackground(new java.awt.Color(51, 51, 51));
        OpcionesVoluntario.setPreferredSize(new java.awt.Dimension(272, 800));

        LabelUsuarioVoluntario.setFont(new java.awt.Font("Aptos", 0, 24));
        LabelUsuarioVoluntario.setForeground(new java.awt.Color(255, 255, 255));
        LabelUsuarioVoluntario.setText("Usuario:");
        LabelUsuarioVoluntario.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        BotonCerrarSesionVoluntario.setBackground(new java.awt.Color(0, 102, 153));
        BotonCerrarSesionVoluntario.setFont(new java.awt.Font("Aptos", 0, 14));
        BotonCerrarSesionVoluntario.setForeground(new java.awt.Color(255, 255, 255));
        BotonCerrarSesionVoluntario.setText("Cerrar sesión");
        BotonCerrarSesionVoluntario.setBorderPainted(false);
        BotonCerrarSesionVoluntario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesionVoluntarioActionPerformed(evt);
            }
        });

        BotonRegistrarPuntoDeAvistamiento.setBackground(new java.awt.Color(0, 102, 153));
        BotonRegistrarPuntoDeAvistamiento.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonRegistrarPuntoDeAvistamiento.setForeground(new java.awt.Color(255, 255, 255));
        BotonRegistrarPuntoDeAvistamiento.setText("<HTML>Registrar punto de avistamiento</HTML>");
        BotonRegistrarPuntoDeAvistamiento.setBorderPainted(false);

        BotonRegistarGato.setBackground(new java.awt.Color(0, 102, 153));
        BotonRegistarGato.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonRegistarGato.setForeground(new java.awt.Color(255, 255, 255));
        BotonRegistarGato.setText("Registrar gato");
        BotonRegistarGato.setBorderPainted(false);
        BotonRegistarGato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegistarGatoActionPerformed(evt);
            }
        });

        BotonAsignarGatoAFamilia.setBackground(new java.awt.Color(0, 102, 153));
        BotonAsignarGatoAFamilia.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonAsignarGatoAFamilia.setForeground(new java.awt.Color(255, 255, 255));
        BotonAsignarGatoAFamilia.setText("Asignar gato a familia");
        BotonAsignarGatoAFamilia.setBorderPainted(false);
        BotonAsignarGatoAFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAsignarGatoAFamiliaActionPerformed(evt);
            }
        });

        BotonRegistrarVisitaDeSeguimiento.setBackground(new java.awt.Color(0, 102, 153));
        BotonRegistrarVisitaDeSeguimiento.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonRegistrarVisitaDeSeguimiento.setForeground(new java.awt.Color(255, 255, 255));
        BotonRegistrarVisitaDeSeguimiento.setText("<HTML>Registrar visita de seguimiento</HTML>");
        BotonRegistrarVisitaDeSeguimiento.setBorderPainted(false);
        BotonRegistrarVisitaDeSeguimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegistrarVisitaDeSeguimientoActionPerformed(evt);
            }
        });

        BotonVerCalendarioDeTareas.setBackground(new java.awt.Color(0, 102, 153));
        BotonVerCalendarioDeTareas.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonVerCalendarioDeTareas.setForeground(new java.awt.Color(255, 255, 255));
        BotonVerCalendarioDeTareas.setText("<HTML>Ver calendario de tareas</HTML>");
        BotonVerCalendarioDeTareas.setBorderPainted(false);
        BotonVerCalendarioDeTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonVerCalendarioDeTareasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OpcionesVoluntarioLayout = new javax.swing.GroupLayout(OpcionesVoluntario);
        OpcionesVoluntario.setLayout(OpcionesVoluntarioLayout);
        OpcionesVoluntarioLayout.setHorizontalGroup(
            OpcionesVoluntarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OpcionesVoluntarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OpcionesVoluntarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelUsuarioVoluntario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OpcionesVoluntarioLayout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addGroup(OpcionesVoluntarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BotonCerrarSesionVoluntario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotonAsignarGatoAFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonRegistarGato, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonVerCalendarioDeTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonRegistrarVisitaDeSeguimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonRegistrarPuntoDeAvistamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)))
                .addContainerGap())
        );
        OpcionesVoluntarioLayout.setVerticalGroup(
            OpcionesVoluntarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpcionesVoluntarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelUsuarioVoluntario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(BotonVerCalendarioDeTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(BotonRegistrarVisitaDeSeguimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(BotonAsignarGatoAFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(BotonRegistarGato, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(BotonRegistrarPuntoDeAvistamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(BotonCerrarSesionVoluntario)
                .addGap(50, 50, 50))
        );

        ContenedorPrincipalVoluntario.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout ContenedorPrincipalVoluntarioLayout = new javax.swing.GroupLayout(ContenedorPrincipalVoluntario);
        ContenedorPrincipalVoluntario.setLayout(ContenedorPrincipalVoluntarioLayout);
        ContenedorPrincipalVoluntarioLayout.setHorizontalGroup(
            ContenedorPrincipalVoluntarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 922, Short.MAX_VALUE)
        );
        ContenedorPrincipalVoluntarioLayout.setVerticalGroup(
            ContenedorPrincipalVoluntarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(OpcionesVoluntario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContenedorPrincipalVoluntario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OpcionesVoluntario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ContenedorPrincipalVoluntario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    // ==================== MANEJADORES DE EVENTOS ====================

    private void BotonVerCalendarioDeTareasActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarCalendarioDeTareas();
    }

    private void BotonCerrarSesionVoluntarioActionPerformed(java.awt.event.ActionEvent evt) {
        mainFrame.setInicioSesion();
    }

    private void BotonRegistarGatoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarFormularioRegistrarGato();
    }

    private void BotonRegistrarVisitaDeSeguimientoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarFormularioRegistrarVisita();
    }

    private void BotonAsignarGatoAFamiliaActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarFormularioAsignarGatoAFamilia();
    }

    // ==================== VARIABLES DE COMPONENTES ====================
    
    private javax.swing.JButton BotonAsignarGatoAFamilia;
    private javax.swing.JButton BotonCerrarSesionVoluntario;
    private javax.swing.JButton BotonRegistarGato;
    private javax.swing.JButton BotonRegistrarPuntoDeAvistamiento;
    private javax.swing.JButton BotonRegistrarVisitaDeSeguimiento;
    private javax.swing.JButton BotonVerCalendarioDeTareas;
    private javax.swing.JPanel ContenedorPrincipalVoluntario;
    private javax.swing.JLabel LabelUsuarioVoluntario;
    private javax.swing.JPanel OpcionesVoluntario;
}
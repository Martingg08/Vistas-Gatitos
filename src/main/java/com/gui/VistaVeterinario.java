package com.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Vista principal para el módulo de Veterinarios
 * Gestiona actualizaciones de estado de salud, diagnósticos y historial médico
 * 
 * @author Tincho
 */
public class VistaVeterinario extends javax.swing.JPanel {

    private Main mainFrame;
    private Controlador controlador;
    
    // TODO: REEMPLAZAR CON EL VETERINARIO REAL DE LA SESIÓN
    private Integer idVeterinarioActual = 1;

    // ==================== CONSTRUCTORES ====================
    
    public VistaVeterinario(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.controlador = new Controlador(idVeterinarioActual);
        initComponents();
    }
    
    public VistaVeterinario() {
        this.controlador = new Controlador(idVeterinarioActual);
        initComponents();
    }
    
    // ==================== MÓDULO: ACTUALIZAR ESTADO DE SALUD ====================
    
    /**
     * Muestra el formulario para actualizar el estado de salud de un gato
     * Paso 1: Solicitar ID del gato
     */
    private void mostrarFormularioActualizarEstadoSalud() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Actualizar Estado de Salud");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel del formulario
        JPanel panelFormulario = crearPanelSolicitarIdGato();
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }
    
    /**
     * Crea el panel inicial que solicita el ID del gato
     */
    private JPanel crearPanelSolicitarIdGato() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // Instrucciones
        JLabel labelInstrucciones = new JLabel("<html><center>Ingrese el ID del gato para actualizar su estado de salud.<br>" +
                                               "El sistema mostrará el estado actual y las opciones disponibles.</center></html>");
        labelInstrucciones.setFont(new Font("Aptos", Font.PLAIN, 16));
        labelInstrucciones.setForeground(Color.WHITE);
        labelInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 40, 20);
        panel.add(labelInstrucciones, gbc);
        
        // Label ID
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 20, 20, 20);
        JLabel labelId = new JLabel("ID del Gato: *");
        labelId.setFont(new Font("Aptos", Font.PLAIN, 20));
        labelId.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(labelId, gbc);
        
        // TextField ID
        JTextField textFieldId = new JTextField(15);
        textFieldId.setFont(new Font("Aptos", Font.PLAIN, 18));
        textFieldId.setBackground(new Color(204, 204, 204));
        textFieldId.setForeground(Color.BLACK);
        textFieldId.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(textFieldId, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 45));
        botonCancelar.addActionListener(e -> {
            textFieldId.setText("");
        });
        panelBotones.add(botonCancelar);
        
        JButton botonContinuar = new JButton("Continuar");
        botonContinuar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonContinuar.setBackground(new Color(0, 102, 153));
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setBorderPainted(false);
        botonContinuar.setPreferredSize(new Dimension(150, 45));
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
            
            // TODO: BASE DE DATOS - Obtener datos del gato
            Controlador.DatosGatoDetalle gato = controlador.obtenerDatosGatoDetalle(idGato);
            
            if (gato == null) {
                JOptionPane.showMessageDialog(panel,
                    "No se encontró un gato con el ID: " + idGato,
                    "Gato no válido",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Mostrar formulario de actualización
            mostrarFormularioSeleccionEstado(gato);
        });
        panelBotones.add(botonContinuar);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 20, 20, 20);
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    
    /**
     * Muestra el formulario de selección de nuevo estado con la información del gato
     */
    private void mostrarFormularioSeleccionEstado(Controlador.DatosGatoDetalle gato) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Actualizar Estado de Salud");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(102, 102, 102));
        
        // Información del gato
        JPanel panelInfoGato = crearPanelInformacionGato(gato);
        panelCentral.add(panelInfoGato, BorderLayout.NORTH);
        
        // Formulario de selección de estado
        JPanel panelFormulario = crearPanelFormularioEstado(gato);
        panelCentral.add(panelFormulario, BorderLayout.CENTER);
        
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }
    
    /**
     * Crea el panel con la información del gato seleccionado
     */
    private JPanel crearPanelInformacionGato(Controlador.DatosGatoDetalle gato) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(80, 80, 80));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 200, 100), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        // Título
        JLabel labelTituloGato = new JLabel("▸ INFORMACIÓN DEL GATO");
        labelTituloGato.setFont(new Font("Aptos", Font.BOLD, 18));
        labelTituloGato.setForeground(new Color(255, 200, 100));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(labelTituloGato, gbc);
        
        gbc.gridwidth = 1;
        
        // Información
        int fila = 1;
        agregarInfoLabel(panel, gbc, fila++, "ID:", String.valueOf(gato.idGato));
        agregarInfoLabel(panel, gbc, fila++, "Nombre:", gato.nombre);
        agregarInfoLabel(panel, gbc, fila++, "Color:", gato.color);
        agregarInfoLabel(panel, gbc, fila++, "Edad aproximada:", 
                        gato.edadAproximada != null ? gato.edadAproximada + " meses" : "No especificada");
        agregarInfoLabel(panel, gbc, fila++, "Características:", gato.caracteristicas);
        
        // Estado actual destacado
        gbc.gridy = fila;
        gbc.gridx = 0;
        JLabel labelEstadoActual = new JLabel("Estado actual:");
        labelEstadoActual.setFont(new Font("Aptos", Font.BOLD, 16));
        labelEstadoActual.setForeground(Color.YELLOW);
        panel.add(labelEstadoActual, gbc);
        
        gbc.gridx = 1;
        JLabel labelValorEstado = new JLabel(gato.estadoSalud);
        labelValorEstado.setFont(new Font("Aptos", Font.BOLD, 16));
        labelValorEstado.setForeground(Color.YELLOW);
        panel.add(labelValorEstado, gbc);
        
        return panel;
    }
    
    /**
     * Método auxiliar para agregar etiquetas de información
     */
    private void agregarInfoLabel(JPanel panel, GridBagConstraints gbc, int fila, 
                                  String etiqueta, String valor) {
        JLabel labelEtiqueta = new JLabel(etiqueta);
        labelEtiqueta.setFont(new Font("Aptos", Font.BOLD, 14));
        labelEtiqueta.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(labelEtiqueta, gbc);
        
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelValor.setForeground(new Color(220, 220, 220));
        gbc.gridx = 1;
        panel.add(labelValor, gbc);
    }
    
    /**
     * Crea el formulario con los radio buttons de estados y campo de observación
     */
    private JPanel crearPanelFormularioEstado(Controlador.DatosGatoDetalle gato) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ========== Nuevo Estado ==========
        JLabel labelNuevoEstado = new JLabel("Seleccione el nuevo estado: *");
        labelNuevoEstado.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelNuevoEstado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelNuevoEstado, gbc);
        
        // Panel con radio buttons
        JPanel panelEstados = new JPanel(new GridLayout(2, 2, 10, 10));
        panelEstados.setBackground(new Color(102, 102, 102));
        
        ButtonGroup grupoEstados = new ButtonGroup();
        String[] estados = {"Sano", "Enfermo", "En tratamiento", "Esterilizado"};
        JRadioButton[] radioButtons = new JRadioButton[estados.length];
        
        for (int i = 0; i < estados.length; i++) {
            radioButtons[i] = new JRadioButton(estados[i]);
            radioButtons[i].setFont(new Font("Aptos", Font.PLAIN, 16));
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setBackground(new Color(102, 102, 102));
            radioButtons[i].setActionCommand(estados[i]);
            grupoEstados.add(radioButtons[i]);
            panelEstados.add(radioButtons[i]);
            
            // Marcar el estado actual
            if (estados[i].equals(gato.estadoSalud)) {
                radioButtons[i].setSelected(true);
            }
        }
        
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 40, 15, 20);
        panel.add(panelEstados, gbc);
        
        // ========== Observación (opcional) ==========
        gbc.insets = new Insets(20, 20, 10, 20);
        JLabel labelObservacion = new JLabel("Observación (opcional):");
        labelObservacion.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelObservacion.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(labelObservacion, gbc);
        
        JTextArea textAreaObservacion = new JTextArea(4, 40);
        textAreaObservacion.setFont(new Font("Aptos", Font.PLAIN, 16));
        textAreaObservacion.setBackground(new Color(204, 204, 204));
        textAreaObservacion.setForeground(Color.BLACK);
        textAreaObservacion.setLineWrap(true);
        textAreaObservacion.setWrapStyleWord(true);
        JScrollPane scrollObservacion = new JScrollPane(textAreaObservacion);
        scrollObservacion.setPreferredSize(new Dimension(500, 100));
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(scrollObservacion, gbc);
        
        // ========== Panel de Botones ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(150, 45));
        botonVolver.addActionListener(e -> mostrarFormularioActualizarEstadoSalud());
        panelBotones.add(botonVolver);
        
        JButton botonConfirmar = new JButton("Confirmar Actualización");
        botonConfirmar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonConfirmar.setBackground(new Color(0, 150, 0));
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setBorderPainted(false);
        botonConfirmar.setPreferredSize(new Dimension(230, 45));
        botonConfirmar.addActionListener(e -> {
            // Obtener estado seleccionado
            String nuevoEstado = grupoEstados.getSelection().getActionCommand();
            String observacion = textAreaObservacion.getText().trim();
            
            // Validar que el estado sea diferente al actual
            if (nuevoEstado.equals(gato.estadoSalud)) {
                int confirmacion = JOptionPane.showConfirmDialog(panel,
                    "El estado seleccionado es el mismo que el actual.\n" +
                    "¿Desea registrar esta actualización de todas formas?",
                    "Estado idéntico al actual",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            // Confirmar la operación
            int confirmacion = JOptionPane.showConfirmDialog(panel,
                "¿Está seguro que desea actualizar el estado de salud?\n\n" +
                "Gato: " + gato.nombre + "\n" +
                "Estado actual: " + gato.estadoSalud + "\n" +
                "Nuevo estado: " + nuevoEstado,
                "Confirmar actualización",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                // TODO: BASE DE DATOS - Actualizar estado de salud
                boolean exito = controlador.actualizarEstadoDeSalud(
                    gato.idGato, 
                    nuevoEstado, 
                    observacion
                );
                
                if (exito) {
                    JOptionPane.showMessageDialog(panel,
                        "¡Estado de salud actualizado exitosamente!\n\n" +
                        "Gato: " + gato.nombre + "\n" +
                        "Nuevo estado: " + nuevoEstado + "\n\n" +
                        "(Funcionalidad de guardado pendiente de implementación)",
                        "Actualización exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Volver al formulario inicial
                    mostrarFormularioActualizarEstadoSalud();
                } else {
                    JOptionPane.showMessageDialog(panel,
                        "Error al actualizar el estado de salud.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBotones.add(botonConfirmar);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    
     /**
     * Muestra el buscador de gatos para emitir diagnóstico
     */
    private void mostrarBuscadorGatos() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Emitir Diagnóstico y Tratamiento");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Panel buscador
        JPanel panelBuscador = crearPanelBuscador();
        panelPrincipal.add(panelBuscador, BorderLayout.CENTER);

        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }

    /**
     * Crea el panel buscador de gatos
     */
    private JPanel crearPanelBuscador() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Instrucciones
        JLabel labelInstrucciones = new JLabel("<html><center>Ingrese el ID del gato para cargar su ficha clínica.<br>" +
                                               "Podrá agregar diagnósticos, tratamientos y certificados.</center></html>");
        labelInstrucciones.setFont(new Font("Aptos", Font.PLAIN, 16));
        labelInstrucciones.setForeground(Color.WHITE);
        labelInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 20, 40, 20);
        panel.add(labelInstrucciones, gbc);

        // Label ID
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 20, 20, 10);
        JLabel labelId = new JLabel("ID del Gato:");
        labelId.setFont(new Font("Aptos", Font.PLAIN, 20));
        labelId.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(labelId, gbc);

        // TextField ID
        JTextField textFieldId = new JTextField(15);
        textFieldId.setFont(new Font("Aptos", Font.PLAIN, 18));
        textFieldId.setBackground(new Color(204, 204, 204));
        textFieldId.setForeground(Color.BLACK);
        textFieldId.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 20, 10);
        panel.add(textFieldId, gbc);

        // Botón Buscar
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonBuscar.setBackground(new Color(0, 102, 153));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setBorderPainted(false);
        botonBuscar.setPreferredSize(new Dimension(120, 40));
        botonBuscar.addActionListener(e -> {
            String idTexto = textFieldId.getText().trim();

            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "Debe ingresar el ID del gato",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

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

            // TODO: BASE DE DATOS - Buscar gato y cargar ficha clínica
            Controlador.FichaClinicaGato ficha = controlador.obtenerFichaClinica(idGato);

            if (ficha == null) {
                JOptionPane.showMessageDialog(panel,
                    "No se encontró un gato con el ID: " + idGato + "\n\n" +
                    "Verifique el ID e intente nuevamente.",
                    "Gato no encontrado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Mostrar ficha clínica
            mostrarFichaClinica(ficha);
        });
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 20, 20);
        panel.add(botonBuscar, gbc);

        // Botón Cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 16));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(120, 40));
        botonCancelar.addActionListener(e -> {
            ContenedorPrincipalVeterinario.removeAll();
            ContenedorPrincipalVeterinario.revalidate();
            ContenedorPrincipalVeterinario.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 20, 20, 20);
        panel.add(botonCancelar, gbc);

        return panel;
    }

    /**
     * Muestra la ficha clínica completa del gato
     */
    private void mostrarFichaClinica(Controlador.FichaClinicaGato ficha) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Ficha Clínica - " + ficha.nombre);
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Panel central dividido
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(102, 102, 102));

        // Datos generales del gato
        JPanel panelDatosGenerales = crearPanelDatosGenerales(ficha);
        panelCentral.add(panelDatosGenerales, BorderLayout.NORTH);

        // Botones de acción y historial
        JPanel panelAccionesHistorial = crearPanelAccionesHistorial(ficha);
        panelCentral.add(panelAccionesHistorial, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Botón volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(102, 102, 102));

        JButton botonVolver = new JButton("◀ Volver al Buscador");
        botonVolver.setFont(new Font("Aptos", Font.PLAIN, 16));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(200, 40));
        botonVolver.addActionListener(e -> mostrarBuscadorGatos());
        panelInferior.add(botonVolver);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }

    /**
     * Crea el panel con los datos generales del gato
     */
    private JPanel crearPanelDatosGenerales(Controlador.FichaClinicaGato ficha) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(80, 80, 80));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 180, 255), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Título
        JLabel labelTituloSeccion = new JLabel("▸ DATOS GENERALES");
        labelTituloSeccion.setFont(new Font("Aptos", Font.BOLD, 18));
        labelTituloSeccion.setForeground(new Color(100, 180, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        panel.add(labelTituloSeccion, gbc);

        gbc.gridwidth = 1;

        // Primera columna
        int fila = 1;
        agregarInfoLabel(panel, gbc, fila++, "ID:", String.valueOf(ficha.idGato));
        agregarInfoLabel(panel, gbc, fila++, "Nombre:", ficha.nombre);
        agregarInfoLabel(panel, gbc, fila++, "Color:", ficha.color);

        // Segunda columna
        fila = 1;
        gbc.gridx = 2;
        agregarInfoLabel(panel, gbc, fila++, "Edad:", 
                        ficha.edadAproximada != null ? ficha.edadAproximada + " meses" : "No especificada");
        agregarInfoLabel(panel, gbc, fila++, "Estado de salud:", ficha.estadoSalud);
        agregarInfoLabel(panel, gbc, fila++, "Ubicación actual:", ficha.ubicacionActual);

        // Características (ocupa toda la fila)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        JLabel labelCaract = new JLabel("Características:");
        labelCaract.setFont(new Font("Aptos", Font.BOLD, 14));
        labelCaract.setForeground(Color.WHITE);
        panel.add(labelCaract, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 5, 10);
        JLabel labelValorCaract = new JLabel("<html>" + ficha.caracteristicas + "</html>");
        labelValorCaract.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelValorCaract.setForeground(new Color(220, 220, 220));
        panel.add(labelValorCaract, gbc);

        return panel;
    }

    /**
     * Crea el panel con botones de acción e historial médico
     */
    private JPanel crearPanelAccionesHistorial(Controlador.FichaClinicaGato ficha) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(102, 102, 102));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelBotones.setBackground(new Color(102, 102, 102));

        JButton botonAgregarDiagnostico = new JButton("➕ Agregar Diagnóstico");
        botonAgregarDiagnostico.setFont(new Font("Aptos", Font.BOLD, 16));
        botonAgregarDiagnostico.setBackground(new Color(0, 150, 0));
        botonAgregarDiagnostico.setForeground(Color.WHITE);
        botonAgregarDiagnostico.setBorderPainted(false);
        botonAgregarDiagnostico.setPreferredSize(new Dimension(220, 45));
        botonAgregarDiagnostico.addActionListener(e -> mostrarFormularioDiagnostico(ficha));
        panelBotones.add(botonAgregarDiagnostico);

        JButton botonCertificado = new JButton("📋 Certificado de Aptitud");
        botonCertificado.setFont(new Font("Aptos", Font.PLAIN, 16));
        botonCertificado.setBackground(new Color(0, 102, 153));
        botonCertificado.setForeground(Color.WHITE);
        botonCertificado.setBorderPainted(false);
        botonCertificado.setPreferredSize(new Dimension(220, 45));
        botonCertificado.addActionListener(e -> {
            // TODO: Implementar emisión de certificado (AA2)
            JOptionPane.showMessageDialog(panel,
                "Funcionalidad de certificado de aptitud\n" +
                "pendiente de implementación.",
                "Próximamente",
                JOptionPane.INFORMATION_MESSAGE);
        });
        panelBotones.add(botonCertificado);

        panel.add(panelBotones, BorderLayout.NORTH);

        // Panel de historial médico
        JPanel panelHistorial = crearPanelHistorialMedico(ficha);
        panel.add(panelHistorial, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel con el historial médico del gato
     */
    private JPanel crearPanelHistorialMedico(Controlador.FichaClinicaGato ficha) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(102, 102, 102));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Título
        JLabel labelTitulo = new JLabel("═══ HISTORIAL MÉDICO ═══");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(255, 200, 100));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelTitulo, BorderLayout.NORTH);

        // Panel con registros
        JPanel panelRegistros = new JPanel();
        panelRegistros.setLayout(new BoxLayout(panelRegistros, BoxLayout.Y_AXIS));
        panelRegistros.setBackground(new Color(102, 102, 102));

        if (ficha.historialMedico.isEmpty()) {
            JLabel labelVacio = new JLabel("No hay registros médicos previos");
            labelVacio.setFont(new Font("Aptos", Font.ITALIC, 16));
            labelVacio.setForeground(Color.LIGHT_GRAY);
            labelVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelRegistros.add(Box.createVerticalStrut(20));
            panelRegistros.add(labelVacio);
        } else {
            for (Controlador.RegistroHistorialMedico registro : ficha.historialMedico) {
                JPanel panelRegistro = crearPanelRegistroHistorial(registro);
                panelRegistros.add(panelRegistro);
                panelRegistros.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelRegistros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        scrollPane.setPreferredSize(new Dimension(0, 250));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea un panel individual para un registro del historial
     */
    private JPanel crearPanelRegistroHistorial(Controlador.RegistroHistorialMedico registro) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(new Color(204, 204, 204));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Fecha
        JLabel labelFecha = new JLabel(registro.fecha.toString());
        labelFecha.setFont(new Font("Aptos", Font.BOLD, 14));
        labelFecha.setForeground(new Color(0, 102, 153));
        panel.add(labelFecha, BorderLayout.NORTH);

        // Información
        JPanel panelInfo = new JPanel(new GridLayout(3, 1, 5, 5));
        panelInfo.setBackground(new Color(204, 204, 204));

        JLabel labelDiagnostico = new JLabel("• Diagnóstico: " + registro.diagnostico);
        labelDiagnostico.setFont(new Font("Aptos", Font.PLAIN, 13));
        labelDiagnostico.setForeground(Color.BLACK);

        JLabel labelTratamiento = new JLabel("• Tratamiento: " + registro.tratamiento);
        labelTratamiento.setFont(new Font("Aptos", Font.PLAIN, 13));
        labelTratamiento.setForeground(Color.BLACK);

        JLabel labelEstudio = new JLabel("• Estudio: " + registro.tipoEstudio + 
                                        " | Resultado: " + registro.resultado);
        labelEstudio.setFont(new Font("Aptos", Font.ITALIC, 12));
        labelEstudio.setForeground(Color.DARK_GRAY);

        panelInfo.add(labelDiagnostico);
        panelInfo.add(labelTratamiento);
        panelInfo.add(labelEstudio);

        panel.add(panelInfo, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Muestra el formulario para agregar un nuevo diagnóstico
     */
    private void mostrarFormularioDiagnostico(Controlador.FichaClinicaGato ficha) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Agregar Diagnóstico - " + ficha.nombre);
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // ========== Fecha ==========
        JLabel labelFecha = new JLabel("Fecha: *");
        labelFecha.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelFecha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelFecha, gbc);

        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setBackground(new Color(102, 102, 102));

        JComboBox<Integer> comboDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDia.addItem(i);
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
        for (int i = anioActual - 1; i <= anioActual + 1; i++) comboAnio.addItem(i);
        comboAnio.setSelectedItem(anioActual);
        comboAnio.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboAnio.setBackground(new Color(204, 204, 204));
        comboAnio.setPreferredSize(new Dimension(90, 35));

        LocalDate hoy = LocalDate.now();
        comboDia.setSelectedItem(hoy.getDayOfMonth());
        comboMes.setSelectedIndex(hoy.getMonthValue() - 1);

        panelFecha.add(comboDia);
        panelFecha.add(comboMes);
        panelFecha.add(comboAnio);

        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(panelFecha, gbc);

        // ========== Hora ==========
        JLabel labelHora = new JLabel("Hora: *");
        labelHora.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelHora.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelHora, gbc);

        JPanel panelHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelHora.setBackground(new Color(102, 102, 102));

        JComboBox<String> comboHora = new JComboBox<>();
        for (int i = 0; i <= 23; i++) comboHora.addItem(String.format("%02d", i));
        comboHora.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboHora.setBackground(new Color(204, 204, 204));
        comboHora.setPreferredSize(new Dimension(70, 35));

        JLabel labelSeparador = new JLabel(":");
        labelSeparador.setFont(new Font("Aptos", Font.BOLD, 20));
        labelSeparador.setForeground(Color.WHITE);

        JComboBox<String> comboMinuto = new JComboBox<>();
        for (int i = 0; i <= 59; i++) comboMinuto.addItem(String.format("%02d", i));
        comboMinuto.setFont(new Font("Aptos", Font.PLAIN, 16));
        comboMinuto.setBackground(new Color(204, 204, 204));
        comboMinuto.setPreferredSize(new Dimension(70, 35));

        // Establecer hora actual
        int horaActual = java.time.LocalTime.now().getHour();
        int minutoActual = java.time.LocalTime.now().getMinute();
        comboHora.setSelectedItem(String.format("%02d", horaActual));
        comboMinuto.setSelectedItem(String.format("%02d", minutoActual));

        panelHora.add(comboHora);
        panelHora.add(labelSeparador);
        panelHora.add(comboMinuto);

        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(panelHora, gbc);

        // ========== Detalle de Diagnóstico ==========
        JLabel labelDiagnostico = new JLabel("Detalle de Diagnóstico: *");
        labelDiagnostico.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelDiagnostico.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        panelFormulario.add(labelDiagnostico, gbc);

        JTextArea textAreaDiagnostico = new JTextArea(5, 30);
        textAreaDiagnostico.setFont(new Font("Aptos", Font.PLAIN, 16));
        textAreaDiagnostico.setBackground(new Color(204, 204, 204));
        textAreaDiagnostico.setForeground(Color.BLACK);
        textAreaDiagnostico.setLineWrap(true);
        textAreaDiagnostico.setWrapStyleWord(true);
        JScrollPane scrollDiagnostico = new JScrollPane(textAreaDiagnostico);
        scrollDiagnostico.setPreferredSize(new Dimension(400, 120));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(scrollDiagnostico, gbc);

        // ========== Detalle de Tratamiento ==========
        JLabel labelTratamiento = new JLabel("Detalle de Tratamiento: *");
        labelTratamiento.setFont(new Font("Aptos", Font.PLAIN, 18));
        labelTratamiento.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        panelFormulario.add(labelTratamiento, gbc);

        JTextArea textAreaTratamiento = new JTextArea(5, 30);
        textAreaTratamiento.setFont(new Font("Aptos", Font.PLAIN, 16));
        textAreaTratamiento.setBackground(new Color(204, 204, 204));
        textAreaTratamiento.setForeground(Color.BLACK);
        textAreaTratamiento.setLineWrap(true);
        textAreaTratamiento.setWrapStyleWord(true);
        JScrollPane scrollTratamiento = new JScrollPane(textAreaTratamiento);
        scrollTratamiento.setPreferredSize(new Dimension(400, 120));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(scrollTratamiento, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // ========== Panel de Botones ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(102, 102, 102));

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 18));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 45));
        botonCancelar.addActionListener(e -> mostrarFichaClinica(ficha));
        panelBotones.add(botonCancelar);

        JButton botonConfirmar = new JButton("Confirmar y Agregar");
        botonConfirmar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonConfirmar.setBackground(new Color(0, 150, 0));
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setBorderPainted(false);
        botonConfirmar.setPreferredSize(new Dimension(220, 45));
        botonConfirmar.addActionListener(e -> {
            // Validar campos obligatorios
            String diagnostico = textAreaDiagnostico.getText().trim();
            String tratamiento = textAreaTratamiento.getText().trim();

            if (diagnostico.isEmpty() || tratamiento.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal,
                    "Debe completar todos los campos obligatorios para registrar el diagnóstico.\n\n" +
                    "Campos requeridos:\n" +
                    "- Fecha\n" +
                    "- Hora\n" +
                    "- Detalle de Diagnóstico\n" +
                    "- Detalle de Tratamiento",
                    "Formulario incompleto",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener datos
            LocalDate fecha = LocalDate.of(
                (Integer) comboAnio.getSelectedItem(),
                comboMes.getSelectedIndex() + 1,
                (Integer) comboDia.getSelectedItem()
            );
            String hora = comboHora.getSelectedItem() + ":" + comboMinuto.getSelectedItem();

            // Confirmar operación
            int confirmacion = JOptionPane.showConfirmDialog(panelPrincipal,
                "¿Está seguro que desea registrar este diagnóstico?\n\n" +
                "Gato: " + ficha.nombre + "\n" +
                "Fecha: " + fecha + " " + hora + "\n" +
                "Diagnóstico: " + (diagnostico.length() > 50 ? 
                                 diagnostico.substring(0, 47) + "..." : diagnostico),
                "Confirmar registro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                // TODO: BASE DE DATOS - Registrar diagnóstico y tratamiento
                Integer idDiagnostico = controlador.registrarDiagnostico(
                    ficha.idGato, fecha, hora, diagnostico, tratamiento
                );

                if (idDiagnostico != null) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "¡Diagnóstico registrado exitosamente!\n\n" +
                        "Gato: " + ficha.nombre + "\n" +
                        "Fecha: " + fecha + " " + hora + "\n\n" +
                        "(Funcionalidad de guardado pendiente de implementación)",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

                    // Recargar ficha clínica con historial actualizado
                    Controlador.FichaClinicaGato fichaActualizada = 
                        controlador.obtenerFichaClinica(ficha.idGato);
                    mostrarFichaClinica(fichaActualizada);
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Error al registrar el diagnóstico.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBotones.add(botonConfirmar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }
    
    // ==================== COMPONENTES GENERADOS POR NETBEANS ====================

    @SuppressWarnings("unchecked")
    private void initComponents() {

        OpcionesVistaVeterinario = new javax.swing.JPanel();
        LabelUsuarioVeterinario = new javax.swing.JLabel();
        BotonActualizarEstadoDeSaludDeUnGato = new javax.swing.JButton();
        BotonEmitirdiagnosticoYTratamiento = new javax.swing.JButton();
        BotonVerHistorialMedicoDeUnGato = new javax.swing.JButton();
        BotonCerrarSesiónAdministrador = new javax.swing.JButton();
        ContenedorPrincipalVeterinario = new javax.swing.JPanel();

        setBackground(new java.awt.Color(51, 51, 51));
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(1200, 800));

        OpcionesVistaVeterinario.setBackground(new java.awt.Color(51, 51, 51));

        LabelUsuarioVeterinario.setFont(new java.awt.Font("Aptos", 0, 24));
        LabelUsuarioVeterinario.setForeground(new java.awt.Color(255, 255, 255));
        LabelUsuarioVeterinario.setText("Usuario:");
        LabelUsuarioVeterinario.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        BotonActualizarEstadoDeSaludDeUnGato.setBackground(new java.awt.Color(0, 102, 153));
        BotonActualizarEstadoDeSaludDeUnGato.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonActualizarEstadoDeSaludDeUnGato.setForeground(new java.awt.Color(255, 255, 255));
        BotonActualizarEstadoDeSaludDeUnGato.setText("<HTML>Actualizar estado de salud de un gato</HTML>");
        BotonActualizarEstadoDeSaludDeUnGato.setBorderPainted(false);
        BotonActualizarEstadoDeSaludDeUnGato.setMaximumSize(new java.awt.Dimension(200, 50));
        BotonActualizarEstadoDeSaludDeUnGato.setMinimumSize(new java.awt.Dimension(200, 50));
        BotonActualizarEstadoDeSaludDeUnGato.setPreferredSize(new java.awt.Dimension(200, 50));
        BotonActualizarEstadoDeSaludDeUnGato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonActualizarEstadoDeSaludDeUnGatoActionPerformed(evt);
            }
        });

        BotonEmitirdiagnosticoYTratamiento.setBackground(new java.awt.Color(0, 102, 153));
        BotonEmitirdiagnosticoYTratamiento.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonEmitirdiagnosticoYTratamiento.setForeground(new java.awt.Color(255, 255, 255));
        BotonEmitirdiagnosticoYTratamiento.setText("<HTML>Emitir diagnóstico y tratamiento</HTML>");
        BotonEmitirdiagnosticoYTratamiento.setBorderPainted(false);
        BotonEmitirdiagnosticoYTratamiento.setMaximumSize(new java.awt.Dimension(200, 50));
        BotonEmitirdiagnosticoYTratamiento.setMinimumSize(new java.awt.Dimension(200, 50));
        BotonEmitirdiagnosticoYTratamiento.setPreferredSize(new java.awt.Dimension(200, 50));
        BotonEmitirdiagnosticoYTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEmitirdiagnosticoYTratamientoActionPerformed(evt);
            }            
        });

        BotonVerHistorialMedicoDeUnGato.setBackground(new java.awt.Color(0, 102, 153));
        BotonVerHistorialMedicoDeUnGato.setFont(new java.awt.Font("Aptos", 0, 18));
        BotonVerHistorialMedicoDeUnGato.setForeground(new java.awt.Color(255, 255, 255));
        BotonVerHistorialMedicoDeUnGato.setText("<HTML>Ver historial médico de un gato</HTML>");
        BotonVerHistorialMedicoDeUnGato.setBorderPainted(false);
        BotonVerHistorialMedicoDeUnGato.setMaximumSize(new java.awt.Dimension(200, 50));
        BotonVerHistorialMedicoDeUnGato.setMinimumSize(new java.awt.Dimension(200, 50));
        BotonVerHistorialMedicoDeUnGato.setPreferredSize(new java.awt.Dimension(200, 50));
        BotonVerHistorialMedicoDeUnGato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonVerHistorialMedicoDeUnGatoActionPerformed(evt);
            }
        });

        BotonCerrarSesiónAdministrador.setBackground(new java.awt.Color(0, 102, 153));
        BotonCerrarSesiónAdministrador.setFont(new java.awt.Font("Aptos", 0, 14));
        BotonCerrarSesiónAdministrador.setForeground(new java.awt.Color(255, 255, 255));
        BotonCerrarSesiónAdministrador.setText("Cerrar Sesión");
        BotonCerrarSesiónAdministrador.setBorderPainted(false);
        BotonCerrarSesiónAdministrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesiónAdministradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OpcionesVistaVeterinarioLayout = new javax.swing.GroupLayout(OpcionesVistaVeterinario);
        OpcionesVistaVeterinario.setLayout(OpcionesVistaVeterinarioLayout);
        OpcionesVistaVeterinarioLayout.setHorizontalGroup(
            OpcionesVistaVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpcionesVistaVeterinarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OpcionesVistaVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelUsuarioVeterinario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(OpcionesVistaVeterinarioLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(OpcionesVistaVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(BotonCerrarSesiónAdministrador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotonEmitirdiagnosticoYTratamiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotonVerHistorialMedicoDeUnGato, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotonActualizarEstadoDeSaludDeUnGato, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)))
                .addContainerGap())
        );
        OpcionesVistaVeterinarioLayout.setVerticalGroup(
            OpcionesVistaVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpcionesVistaVeterinarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelUsuarioVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215)
                .addComponent(BotonActualizarEstadoDeSaludDeUnGato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(BotonEmitirdiagnosticoYTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(BotonVerHistorialMedicoDeUnGato, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                .addComponent(BotonCerrarSesiónAdministrador)
                .addGap(50, 50, 50))
        );

        ContenedorPrincipalVeterinario.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout ContenedorPrincipalVeterinarioLayout = new javax.swing.GroupLayout(ContenedorPrincipalVeterinario);
        ContenedorPrincipalVeterinario.setLayout(ContenedorPrincipalVeterinarioLayout);
        ContenedorPrincipalVeterinarioLayout.setHorizontalGroup(
            ContenedorPrincipalVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 922, Short.MAX_VALUE)
        );
        ContenedorPrincipalVeterinarioLayout.setVerticalGroup(
            ContenedorPrincipalVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(OpcionesVistaVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContenedorPrincipalVeterinario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OpcionesVistaVeterinario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ContenedorPrincipalVeterinario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
    
    // ==================== MÓDULO: VER HISTORIAL MÉDICO ====================

    /**
     * Muestra el buscador de gatos para ver su historial médico
     */
    private void mostrarBuscadorHistorialMedico() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Ver Historial Médico");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Panel buscador
        JPanel panelBuscador = crearPanelBuscadorHistorial();
        panelPrincipal.add(panelBuscador, BorderLayout.CENTER);

        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }

    /**
     * Crea el panel buscador para historial médico
     */
    private JPanel crearPanelBuscadorHistorial() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Instrucciones
        JLabel labelInstrucciones = new JLabel("<html><center>Ingrese el ID del gato para consultar su historial médico completo.<br>" +
                                               "Podrá ver todos los diagnósticos, tratamientos y estudios realizados.</center></html>");
        labelInstrucciones.setFont(new Font("Aptos", Font.PLAIN, 16));
        labelInstrucciones.setForeground(Color.WHITE);
        labelInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 20, 40, 20);
        panel.add(labelInstrucciones, gbc);

        // Label ID
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 20, 20, 10);
        JLabel labelId = new JLabel("ID del Gato:");
        labelId.setFont(new Font("Aptos", Font.PLAIN, 20));
        labelId.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(labelId, gbc);

        // TextField ID
        JTextField textFieldId = new JTextField(15);
        textFieldId.setFont(new Font("Aptos", Font.PLAIN, 18));
        textFieldId.setBackground(new Color(204, 204, 204));
        textFieldId.setForeground(Color.BLACK);
        textFieldId.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 20, 10);
        panel.add(textFieldId, gbc);

        // Botón Buscar
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(new Font("Aptos", Font.BOLD, 18));
        botonBuscar.setBackground(new Color(0, 102, 153));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setBorderPainted(false);
        botonBuscar.setPreferredSize(new Dimension(120, 40));
        botonBuscar.addActionListener(e -> {
            String idTexto = textFieldId.getText().trim();

            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "Debe ingresar el ID del gato",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

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

            // TODO: BASE DE DATOS - Buscar gato y cargar historial
            Controlador.FichaClinicaGato ficha = controlador.obtenerFichaClinica(idGato);

            if (ficha == null) {
                JOptionPane.showMessageDialog(panel,
                    "No se encontró un gato con el ID: " + idGato + "\n\n" +
                    "Verifique el ID e intente nuevamente.",
                    "Gato no encontrado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Mostrar historial médico completo
            mostrarVistaHistorialMedico(ficha);
        });
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 20, 20);
        panel.add(botonBuscar, gbc);

        // Botón Cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Aptos", Font.PLAIN, 16));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(120, 40));
        botonCancelar.addActionListener(e -> {
            ContenedorPrincipalVeterinario.removeAll();
            ContenedorPrincipalVeterinario.revalidate();
            ContenedorPrincipalVeterinario.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 20, 20, 20);
        panel.add(botonCancelar, gbc);

        return panel;
    }

    /**
     * Muestra la vista completa del historial médico del gato
     */
    private void mostrarVistaHistorialMedico(Controlador.FichaClinicaGato ficha) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Historial Médico - " + ficha.nombre);
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(102, 102, 102));

        // Datos generales del gato (compacto)
        JPanel panelDatosCompactos = crearPanelDatosCompactos(ficha);
        panelCentral.add(panelDatosCompactos, BorderLayout.NORTH);

        // Historial médico detallado
        JPanel panelHistorialDetallado = crearPanelHistorialMedicoDetallado(ficha);
        panelCentral.add(panelHistorialDetallado, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Botón volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(102, 102, 102));

        JButton botonVolver = new JButton("◀ Volver al Buscador");
        botonVolver.setFont(new Font("Aptos", Font.PLAIN, 16));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(200, 40));
        botonVolver.addActionListener(e -> mostrarBuscadorHistorialMedico());
        panelInferior.add(botonVolver);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Mostrar en el contenedor principal
        ContenedorPrincipalVeterinario.removeAll();
        ContenedorPrincipalVeterinario.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalVeterinario.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalVeterinario.revalidate();
        ContenedorPrincipalVeterinario.repaint();
    }

    /**
     * Crea un panel compacto con los datos básicos del gato
     */
    private JPanel crearPanelDatosCompactos(Controlador.FichaClinicaGato ficha) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(new Color(80, 80, 80));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 180, 255), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Información compacta en una línea
        JLabel labelInfo = new JLabel(String.format(
            "ID: %d  |  Color: %s  |  Edad: %s  |  Estado: %s  |  Ubicación: %s",
            ficha.idGato,
            ficha.color,
            ficha.edadAproximada != null ? ficha.edadAproximada + " meses" : "No especificada",
            ficha.estadoSalud,
            ficha.ubicacionActual
        ));
        labelInfo.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelInfo.setForeground(Color.WHITE);

        panel.add(labelInfo);

        return panel;
    }

    /**
     * Crea el panel con el historial médico detallado
     */
    private JPanel crearPanelHistorialMedicoDetallado(Controlador.FichaClinicaGato ficha) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(102, 102, 102));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Encabezado con título e información
        JPanel panelEncabezado = new JPanel(new BorderLayout(10, 5));
        panelEncabezado.setBackground(new Color(102, 102, 102));

        JLabel labelTitulo = new JLabel("═══ HISTORIAL MÉDICO COMPLETO ═══");
        labelTitulo.setFont(new Font("Aptos", Font.BOLD, 22));
        labelTitulo.setForeground(new Color(255, 200, 100));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelEncabezado.add(labelTitulo, BorderLayout.NORTH);

        // Contador de registros
        JLabel labelContador = new JLabel(
            ficha.historialMedico.size() + " registro(s) médico(s) encontrado(s)"
        );
        labelContador.setFont(new Font("Aptos", Font.ITALIC, 14));
        labelContador.setForeground(Color.LIGHT_GRAY);
        labelContador.setHorizontalAlignment(SwingConstants.CENTER);
        panelEncabezado.add(labelContador, BorderLayout.CENTER);

        panel.add(panelEncabezado, BorderLayout.NORTH);

        // Panel con registros del historial
        JPanel panelRegistros = new JPanel();
        panelRegistros.setLayout(new BoxLayout(panelRegistros, BoxLayout.Y_AXIS));
        panelRegistros.setBackground(new Color(102, 102, 102));
        panelRegistros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (ficha.historialMedico.isEmpty()) {
            // Mensaje cuando no hay registros
            JPanel panelVacio = new JPanel(new BorderLayout());
            panelVacio.setBackground(new Color(102, 102, 102));
            panelVacio.setPreferredSize(new Dimension(0, 200));

            JLabel labelVacio = new JLabel("<html><center>📋<br><br>" +
                                           "No hay registros médicos previos para este gato<br><br>" +
                                           "El historial se creará cuando se registre el primer diagnóstico</center></html>");
            labelVacio.setFont(new Font("Aptos", Font.PLAIN, 16));
            labelVacio.setForeground(Color.LIGHT_GRAY);
            labelVacio.setHorizontalAlignment(SwingConstants.CENTER);
            panelVacio.add(labelVacio, BorderLayout.CENTER);

            panelRegistros.add(panelVacio);
        } else {
            // Mostrar todos los registros
            for (int i = 0; i < ficha.historialMedico.size(); i++) {
                Controlador.RegistroHistorialMedico registro = ficha.historialMedico.get(i);

                // Número de registro (más reciente = 1)
                JPanel panelRegistroCompleto = crearPanelRegistroHistorialDetallado(registro, i + 1);
                panelRegistros.add(panelRegistroCompleto);
                panelRegistros.add(Box.createVerticalStrut(15));
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelRegistros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea un panel detallado para un registro individual del historial
     */
    private JPanel crearPanelRegistroHistorialDetallado(Controlador.RegistroHistorialMedico registro, int numeroRegistro) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(204, 204, 204));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 153), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Encabezado con número y fecha
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBackground(new Color(204, 204, 204));

        JLabel labelNumero = new JLabel("Registro #" + numeroRegistro);
        labelNumero.setFont(new Font("Aptos", Font.BOLD, 14));
        labelNumero.setForeground(new Color(0, 102, 153));
        panelEncabezado.add(labelNumero, BorderLayout.WEST);

        JLabel labelFecha = new JLabel("📅 " + registro.fecha.toString());
        labelFecha.setFont(new Font("Aptos", Font.BOLD, 16));
        labelFecha.setForeground(new Color(0, 102, 153));
        panelEncabezado.add(labelFecha, BorderLayout.EAST);

        panel.add(panelEncabezado, BorderLayout.NORTH);

        // Información detallada
        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 5, 5));
        panelInfo.setBackground(new Color(204, 204, 204));

        JLabel labelDiagnostico = new JLabel("🔍 Diagnóstico: " + registro.diagnostico);
        labelDiagnostico.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelDiagnostico.setForeground(Color.BLACK);

        JLabel labelTratamiento = new JLabel("💊 Tratamiento: " + registro.tratamiento);
        labelTratamiento.setFont(new Font("Aptos", Font.PLAIN, 14));
        labelTratamiento.setForeground(Color.BLACK);

        JLabel labelEstudio = new JLabel("📊 Tipo de estudio: " + registro.tipoEstudio);
        labelEstudio.setFont(new Font("Aptos", Font.PLAIN, 13));
        labelEstudio.setForeground(Color.DARK_GRAY);

        JLabel labelResultado = new JLabel("✓ Resultado: " + registro.resultado);
        labelResultado.setFont(new Font("Aptos", Font.PLAIN, 13));
        labelResultado.setForeground(Color.DARK_GRAY);

        panelInfo.add(labelDiagnostico);
        panelInfo.add(labelTratamiento);
        panelInfo.add(labelEstudio);
        panelInfo.add(labelResultado);

        panel.add(panelInfo, BorderLayout.CENTER);

        return panel;
    }

    // ==================== MANEJADORES DE EVENTOS ====================

    private void BotonActualizarEstadoDeSaludDeUnGatoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarFormularioActualizarEstadoSalud();
    }

    private void BotonEmitirdiagnosticoYTratamientoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarBuscadorGatos();
    }

     private void BotonVerHistorialMedicoDeUnGatoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarBuscadorHistorialMedico();
    }

    private void BotonCerrarSesiónAdministradorActionPerformed(java.awt.event.ActionEvent evt) {
        mainFrame.setInicioSesion();
    }

    // ==================== VARIABLES DE COMPONENTES ====================
    
    private javax.swing.JButton BotonActualizarEstadoDeSaludDeUnGato;
    private javax.swing.JButton BotonCerrarSesiónAdministrador;
    private javax.swing.JButton BotonEmitirdiagnosticoYTratamiento;
    private javax.swing.JButton BotonVerHistorialMedicoDeUnGato;
    private javax.swing.JPanel ContenedorPrincipalVeterinario;
    private javax.swing.JLabel LabelUsuarioVeterinario;
    private javax.swing.JPanel OpcionesVistaVeterinario;
}
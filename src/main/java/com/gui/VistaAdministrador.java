package com.gui;

// Imports de iText (PDF) - Especificar clases exactas
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Imports de Swing
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

// Imports de AWT - Especificar clases exactas para evitar conflicto con List
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

// Imports de IO y File
import java.io.File;
import java.io.FileOutputStream;

// Imports de tiempo
import java.time.LocalDate;

// Import de java.util.List EXPLÍCITAMENTE
import java.util.ArrayList;
import java.util.List;

/**
 * Vista principal para el módulo de Administradores
 * Gestiona la generación de reportes del sistema
 * 
 * @author Tincho
 */
public class VistaAdministrador extends javax.swing.JPanel {

    private Main mainFrame;
    private Controlador controlador;
    
    // TODO: REEMPLAZAR CON EL ADMINISTRADOR REAL DE LA SESIÓN
    private Integer idAdministradorActual = 1;

    // ==================== CONSTRUCTORES ====================
    
    public VistaAdministrador(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.controlador = new Controlador(idAdministradorActual);
        initComponents();
    }
    
    public VistaAdministrador() {
        this.controlador = new Controlador(idAdministradorActual);
        initComponents();
    }
    
    // ==================== MÓDULO: GENERAR REPORTES ====================
    
    /**
     * Muestra el selector de tipo de reporte
     */
    private void mostrarSelectorReportes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel labelTitulo = new JLabel("Generar Reportes");
        labelTitulo.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel central con los botones
        JPanel panelCentral = crearPanelBotonesReportes();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalAdministrador.removeAll();
        ContenedorPrincipalAdministrador.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalAdministrador.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalAdministrador.revalidate();
        ContenedorPrincipalAdministrador.repaint();
    }
    
    /**
     * Crea el panel con los botones de tipos de reportes
     */
    private JPanel crearPanelBotonesReportes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // Instrucciones
        JLabel labelInstrucciones = new JLabel("<html><center>Seleccione el tipo de reporte que desea generar.<br>" +
                                               "Los reportes incluyen estadísticas y datos relevantes del sistema.</center></html>");
        labelInstrucciones.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelInstrucciones.setForeground(Color.WHITE);
        labelInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 50, 20);
        panel.add(labelInstrucciones, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // ========== Botón Reporte de Gatos ==========
        JPanel panelReporteGatos = new JPanel(new BorderLayout(10, 10));
        panelReporteGatos.setBackground(new Color(80, 80, 80));
        panelReporteGatos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 0), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panelReporteGatos.setPreferredSize(new Dimension(350, 200));
        panelReporteGatos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel labelIconoGatos = new JLabel("🐱", SwingConstants.CENTER);
        labelIconoGatos.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 60));
        panelReporteGatos.add(labelIconoGatos, BorderLayout.NORTH);
        
        JLabel labelTituloGatos = new JLabel("Reporte de Gatos", SwingConstants.CENTER);
        labelTituloGatos.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 22));
        labelTituloGatos.setForeground(new Color(0, 255, 0));
        
        JLabel labelDescGatos = new JLabel("<html><center>Estadísticas de gatos:<br>" +
                                          "esterilizados, adoptados y en tránsito</center></html>", 
                                          SwingConstants.CENTER);
        labelDescGatos.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 14));
        labelDescGatos.setForeground(Color.LIGHT_GRAY);
        
        JPanel panelTextoGatos = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTextoGatos.setBackground(new Color(80, 80, 80));
        panelTextoGatos.add(labelTituloGatos);
        panelTextoGatos.add(labelDescGatos);
        
        panelReporteGatos.add(panelTextoGatos, BorderLayout.CENTER);
        
        panelReporteGatos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generarReporteGatos();
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelReporteGatos.setBackground(new Color(90, 90, 90));
                panelTextoGatos.setBackground(new Color(90, 90, 90));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelReporteGatos.setBackground(new Color(80, 80, 80));
                panelTextoGatos.setBackground(new Color(80, 80, 80));
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(panelReporteGatos, gbc);
        
        // ========== Botón Reporte de Zonas ==========
        JPanel panelReporteZonas = new JPanel(new BorderLayout(10, 10));
        panelReporteZonas.setBackground(new Color(80, 80, 80));
        panelReporteZonas.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 153), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panelReporteZonas.setPreferredSize(new Dimension(350, 200));
        panelReporteZonas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel labelIconoZonas = new JLabel("📍", SwingConstants.CENTER);
        labelIconoZonas.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 60));
        panelReporteZonas.add(labelIconoZonas, BorderLayout.NORTH);
        
        JLabel labelTituloZonas = new JLabel("Reporte de Zonas", SwingConstants.CENTER);
        labelTituloZonas.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 22));
        labelTituloZonas.setForeground(new Color(100, 180, 255));
        
        JLabel labelDescZonas = new JLabel("<html><center>Información de puntos<br>" +
                                          "de avistamiento y zonas activas</center></html>", 
                                          SwingConstants.CENTER);
        labelDescZonas.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 14));
        labelDescZonas.setForeground(Color.LIGHT_GRAY);
        
        JPanel panelTextoZonas = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTextoZonas.setBackground(new Color(80, 80, 80));
        panelTextoZonas.add(labelTituloZonas);
        panelTextoZonas.add(labelDescZonas);
        
        panelReporteZonas.add(panelTextoZonas, BorderLayout.CENTER);
        
        panelReporteZonas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Reporte de zonas pendiente de implementación
                JOptionPane.showMessageDialog(panel,
                    "Reporte de Zonas\n\n" +
                    "Esta funcionalidad está pendiente de implementación.\n" +
                    "Próximamente incluirá estadísticas sobre puntos de avistamiento,\n" +
                    "zonas de mayor actividad y distribución geográfica de la colonia.",
                    "Próximamente",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelReporteZonas.setBackground(new Color(90, 90, 90));
                panelTextoZonas.setBackground(new Color(90, 90, 90));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelReporteZonas.setBackground(new Color(80, 80, 80));
                panelTextoZonas.setBackground(new Color(80, 80, 80));
            }
        });
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(panelReporteZonas, gbc);
        
        // Botón Cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 40));
        botonCancelar.addActionListener(e -> {
            ContenedorPrincipalAdministrador.removeAll();
            ContenedorPrincipalAdministrador.revalidate();
            ContenedorPrincipalAdministrador.repaint();
        });
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 20, 20, 20);
        panel.add(botonCancelar, gbc);
        
        return panel;
    }
    
    /**
     * Genera el reporte de gatos
     */
    private void generarReporteGatos() {
        // Mostrar diálogo de carga
        JDialog dialogoCargando = crearDialogoCargando();
        
        // Ejecutar en un hilo separado para no bloquear la UI
        SwingWorker<Controlador.EstadisticasGatos, Void> worker = new SwingWorker<>() {
            @Override
            protected Controlador.EstadisticasGatos doInBackground() throws Exception {
                // Simular tiempo de procesamiento
                Thread.sleep(1500);
                
                // TODO: BASE DE DATOS - Obtener estadísticas reales
                return controlador.obtenerEstadisticasGatos();
            }
            
            @Override
            protected void done() {
                dialogoCargando.dispose();
                
                try {
                    Controlador.EstadisticasGatos stats = get();
                    
                    if (stats != null) {
                        mostrarReporteGenerado(stats);
                    } else {
                        JOptionPane.showMessageDialog(VistaAdministrador.this,
                            "Error al generar el reporte.\n" +
                            "No se pudieron obtener las estadísticas.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(VistaAdministrador.this,
                        "Error al generar el reporte: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        worker.execute();
        dialogoCargando.setVisible(true);
    }
    
    /**
     * Crea un diálogo de carga mientras se genera el reporte
     */
    private JDialog crearDialogoCargando() {
        JDialog dialogo = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), 
                                      "Generando reporte...", true);
        dialogo.setSize(400, 150);
        dialogo.setLocationRelativeTo(this);
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(102, 102, 102));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel labelMensaje = new JLabel("Generando reporte de gatos...", SwingConstants.CENTER);
        labelMensaje.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelMensaje.setForeground(Color.WHITE);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        panel.add(labelMensaje, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        
        dialogo.add(panel);
        
        return dialogo;
    }
    
    /**
     * Muestra el reporte generado con opción de descarga
     */
    private void mostrarReporteGenerado(Controlador.EstadisticasGatos stats) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título con icono de éxito
        JLabel labelTitulo = new JLabel("✅ Reporte Generado Exitosamente");
        labelTitulo.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 32));
        labelTitulo.setForeground(new Color(0, 255, 0));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel central con estadísticas
        JPanel panelEstadisticas = crearPanelEstadisticas(stats);
        panelPrincipal.add(panelEstadisticas, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(102, 102, 102));
        
        JButton botonDescargar = new JButton("📥 Descargar PDF");
        botonDescargar.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 18));
        botonDescargar.setBackground(new Color(0, 150, 0));
        botonDescargar.setForeground(Color.WHITE);
        botonDescargar.setBorderPainted(false);
        botonDescargar.setPreferredSize(new Dimension(200, 50));
        botonDescargar.addActionListener(e -> descargarReportePDF(stats));
        panelBotones.add(botonDescargar);
        
        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 18));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(150, 50));
        botonVolver.addActionListener(e -> mostrarSelectorReportes());
        panelBotones.add(botonVolver);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Mostrar en el contenedor principal
        ContenedorPrincipalAdministrador.removeAll();
        ContenedorPrincipalAdministrador.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        ContenedorPrincipalAdministrador.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalAdministrador.revalidate();
        ContenedorPrincipalAdministrador.repaint();
    }
    
    /**
     * Crea el panel con las estadísticas visuales
     */
    private JPanel crearPanelEstadisticas(Controlador.EstadisticasGatos stats) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Calcular gatos en colonia
        int enColonia = stats.totalGatos - stats.adoptados - stats.transito;
        
        // Tarjeta Total
        JPanel tarjetaTotal = crearTarjetaEstadistica("Total de Gatos", 
                                                       String.valueOf(stats.totalGatos),
                                                       new Color(100, 180, 255),
                                                       "📊");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        panel.add(tarjetaTotal, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        // Tarjeta Esterilizados
        JPanel tarjetaEsterilizados = crearTarjetaEstadistica("Esterilizados", 
                                                              String.valueOf(stats.esterilizados),
                                                              new Color(255, 165, 0),
                                                              "💉");
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.25;
        panel.add(tarjetaEsterilizados, gbc);
        
        // Tarjeta Adoptados
        JPanel tarjetaAdoptados = crearTarjetaEstadistica("Adoptados", 
                                                          String.valueOf(stats.adoptados),
                                                          new Color(0, 200, 0),
                                                          "🏠");
        gbc.gridx = 1;
        panel.add(tarjetaAdoptados, gbc);
        
        gbc.gridy = 2;
        
        // Tarjeta En Tránsito
        JPanel tarjetaTransito = crearTarjetaEstadistica("En Tránsito", 
                                                         String.valueOf(stats.transito),
                                                         new Color(200, 150, 0),
                                                         "🚶");
        gbc.gridx = 0;
        panel.add(tarjetaTransito, gbc);
        
        // Tarjeta En Colonia
        JPanel tarjetaColonia = crearTarjetaEstadistica("En Colonia", 
                                                        String.valueOf(enColonia),
                                                        new Color(150, 75, 200),
                                                        "🏕️");
        gbc.gridx = 1;
        panel.add(tarjetaColonia, gbc);
        
        return panel;
    }
    
    /**
     * Crea una tarjeta visual para mostrar una estadística
     */
    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color, String icono) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBackground(new Color(80, 80, 80));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel labelIcono = new JLabel(icono, SwingConstants.CENTER);
        labelIcono.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 40));
        tarjeta.add(labelIcono, BorderLayout.NORTH);
        
        JLabel labelValor = new JLabel(valor, SwingConstants.CENTER);
        labelValor.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 48));
        labelValor.setForeground(color);
        tarjeta.add(labelValor, BorderLayout.CENTER);
        
        JLabel labelTitulo = new JLabel(titulo, SwingConstants.CENTER);
        labelTitulo.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 18));
        labelTitulo.setForeground(Color.LIGHT_GRAY);
        tarjeta.add(labelTitulo, BorderLayout.SOUTH);
        
        return tarjeta;
    }
    
    /**
     * Descarga el reporte como PDF
     */
    private void descargarReportePDF(Controlador.EstadisticasGatos stats) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Gatos");
        
        // Nombre por defecto
        String nombreArchivo = "Reporte_Gatos_" + LocalDate.now() + ".pdf";
        fileChooser.setSelectedFile(new File(nombreArchivo));
        
        // Filtro para PDF
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
        fileChooser.setFileFilter(filter);
        
        int resultado = fileChooser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            // Asegurar extensión .pdf
            if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
                archivo = new File(archivo.getAbsolutePath() + ".pdf");
            }
            
            try {
                generarPDF(archivo, stats);
                
                JOptionPane.showMessageDialog(this,
                    "✅ Reporte descargado exitosamente\n\n" +
                    "Ubicación: " + archivo.getAbsolutePath(),
                    "Descarga exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al generar el archivo PDF:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Genera el archivo PDF del reporte
     */
    private void generarPDF(File archivo, Controlador.EstadisticasGatos stats) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(archivo));
        
        document.open();
        
        // Fuentes
        com.itextpdf.text.Font fontTitulo = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font fontSubtitulo = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 11, com.itextpdf.text.Font.NORMAL);
        
        // Título
        Paragraph titulo = new Paragraph("REPORTE DE GATOS - COLONIA FELINA", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10);
        document.add(titulo);
        
        // Línea separadora
        document.add(new Paragraph("_____________________________________________________________________"));
        document.add(Chunk.NEWLINE);
        
        // Fecha
        Paragraph fecha = new Paragraph(
            "Fecha de generación: " + LocalDate.now() + " | " + 
            java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")),
            fontNormal
        );
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingAfter(20);
        document.add(fecha);
        
        // Estadísticas Generales
        Paragraph subtitulo1 = new Paragraph("ESTADÍSTICAS GENERALES", fontSubtitulo);
        subtitulo1.setSpacingBefore(10);
        subtitulo1.setSpacingAfter(10);
        document.add(subtitulo1);
        
        // Tabla de estadísticas
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setSpacingAfter(20);
        
        // Encabezados
        PdfPCell celdaEncabezado1 = new PdfPCell(new Phrase("Categoría", fontSubtitulo));
        celdaEncabezado1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        celdaEncabezado1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celdaEncabezado1);
        
        PdfPCell celdaEncabezado2 = new PdfPCell(new Phrase("Cantidad", fontSubtitulo));
        celdaEncabezado2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        celdaEncabezado2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celdaEncabezado2);
        
        // Datos
        int enColonia = stats.totalGatos - stats.adoptados - stats.transito;
        
        agregarFilaTabla(tabla, "Total de gatos registrados", String.valueOf(stats.totalGatos), fontNormal);
        agregarFilaTabla(tabla, "Gatos esterilizados", String.valueOf(stats.esterilizados), fontNormal);
        agregarFilaTabla(tabla, "Gatos adoptados", String.valueOf(stats.adoptados), fontNormal);
        agregarFilaTabla(tabla, "Gatos en tránsito", String.valueOf(stats.transito), fontNormal);
        agregarFilaTabla(tabla, "Gatos en colonia", String.valueOf(enColonia), fontNormal);
        
        document.add(tabla);
        
        // Porcentajes
        if (stats.totalGatos > 0) {
            Paragraph subtitulo2 = new Paragraph("PORCENTAJES", fontSubtitulo);
            subtitulo2.setSpacingBefore(10);
            subtitulo2.setSpacingAfter(10);
            document.add(subtitulo2);
            
            double porcEsterilizados = (stats.esterilizados * 100.0) / stats.totalGatos;
            double porcAdoptados = (stats.adoptados * 100.0) / stats.totalGatos;
            double porcTransito = (stats.transito * 100.0) / stats.totalGatos;
            double porcColonia = (enColonia * 100.0) / stats.totalGatos;
            
            PdfPTable tablaPorcentajes = new PdfPTable(2);
            tablaPorcentajes.setWidthPercentage(100);
            
            agregarFilaTabla(tablaPorcentajes, "Porcentaje de esterilización", 
                           String.format("%.1f%%", porcEsterilizados), fontNormal);
            agregarFilaTabla(tablaPorcentajes, "Porcentaje de adopción", 
                           String.format("%.1f%%", porcAdoptados), fontNormal);
            agregarFilaTabla(tablaPorcentajes, "Porcentaje en tránsito", 
                           String.format("%.1f%%", porcTransito), fontNormal);
            agregarFilaTabla(tablaPorcentajes, "Porcentaje en colonia", 
                           String.format("%.1f%%", porcColonia), fontNormal);
            
            document.add(tablaPorcentajes);
        }
        
        // Pie de página
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        Paragraph pie = new Paragraph(
            "_____________________________________________________________________\n" +
            "Reporte generado automáticamente por el Sistema de Gestión de Colonia Felina",
            fontNormal
        );
        pie.setAlignment(Element.ALIGN_CENTER);
        document.add(pie);
        
        document.close();
    }
    
    /**
     * Método auxiliar para agregar filas a la tabla del PDF
     */
    private void agregarFilaTabla(PdfPTable tabla, String columna1, String columna2, 
                                  com.itextpdf.text.Font font) {
        PdfPCell celda1 = new PdfPCell(new Phrase(columna1, font));
        celda1.setPadding(8);
        tabla.addCell(celda1);
        
        PdfPCell celda2 = new PdfPCell(new Phrase(columna2, font));
        celda2.setPadding(8);
        celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda2);
    }

    // ==================== COMPONENTES GENERADOS POR NETBEANS ====================

    @SuppressWarnings("unchecked")
private void initComponents() {

    OpcionesVistaAdministrador = new javax.swing.JPanel();
    LabelUsuarioAdministrador = new javax.swing.JLabel();
    BotonGenerarReporte = new javax.swing.JButton();
    BotonAdministrarUsuarios = new javax.swing.JButton();
    BotonVerVoluntarios = new javax.swing.JButton(); // ← Debe estar aquí
    BotonCerrarSesiónAdministrador = new javax.swing.JButton();
    ContenedorPrincipalAdministrador = new javax.swing.JPanel();

    setBackground(new java.awt.Color(51, 51, 51));

    OpcionesVistaAdministrador.setBackground(new java.awt.Color(51, 51, 51));

    LabelUsuarioAdministrador.setFont(new java.awt.Font("Aptos", 0, 24));
    LabelUsuarioAdministrador.setForeground(new java.awt.Color(255, 255, 255));
    LabelUsuarioAdministrador.setText("Usuario:");
    LabelUsuarioAdministrador.setVerticalAlignment(javax.swing.SwingConstants.TOP);

    BotonGenerarReporte.setBackground(new java.awt.Color(0, 102, 153));
    BotonGenerarReporte.setFont(new java.awt.Font("Aptos", 0, 18));
    BotonGenerarReporte.setForeground(new java.awt.Color(255, 255, 255));
    BotonGenerarReporte.setText("Generar reporte");
    BotonGenerarReporte.setBorderPainted(false);
    BotonGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BotonGenerarReporteActionPerformed(evt);
        }
    });

    BotonAdministrarUsuarios.setBackground(new java.awt.Color(0, 102, 153));
    BotonAdministrarUsuarios.setFont(new java.awt.Font("Aptos", 0, 18));
    BotonAdministrarUsuarios.setForeground(new java.awt.Color(255, 255, 255));
    BotonAdministrarUsuarios.setText("Administrar usuarios");
    BotonAdministrarUsuarios.setBorderPainted(false);
    BotonAdministrarUsuarios.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BotonAdministrarUsuariosActionPerformed(evt);
        }
    });

    BotonVerVoluntarios.setBackground(new java.awt.Color(0, 102, 153));
    BotonVerVoluntarios.setFont(new java.awt.Font("Aptos", 0, 18));
    BotonVerVoluntarios.setForeground(new java.awt.Color(255, 255, 255));
    BotonVerVoluntarios.setText("Ver voluntarios");
    BotonVerVoluntarios.setBorderPainted(false);
    BotonVerVoluntarios.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BotonVerVoluntariosActionPerformed(evt);
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

    javax.swing.GroupLayout OpcionesVistaAdministradorLayout = new javax.swing.GroupLayout(OpcionesVistaAdministrador);
    OpcionesVistaAdministrador.setLayout(OpcionesVistaAdministradorLayout);
    OpcionesVistaAdministradorLayout.setHorizontalGroup(
        OpcionesVistaAdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(OpcionesVistaAdministradorLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(OpcionesVistaAdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(LabelUsuarioAdministrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(OpcionesVistaAdministradorLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(OpcionesVistaAdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BotonCerrarSesiónAdministrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonVerVoluntarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonAdministrarUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30, 30, 30)))
            .addContainerGap())
    );
    OpcionesVistaAdministradorLayout.setVerticalGroup(
        OpcionesVistaAdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(OpcionesVistaAdministradorLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(LabelUsuarioAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(200, 200, 200)
            .addComponent(BotonGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(50, 50, 50)
            .addComponent(BotonAdministrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(50, 50, 50)
            .addComponent(BotonVerVoluntarios, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
            .addComponent(BotonCerrarSesiónAdministrador)
            .addGap(50, 50, 50))
    );

    ContenedorPrincipalAdministrador.setBackground(new java.awt.Color(102, 102, 102));

    javax.swing.GroupLayout ContenedorPrincipalAdministradorLayout = new javax.swing.GroupLayout(ContenedorPrincipalAdministrador);
    ContenedorPrincipalAdministrador.setLayout(ContenedorPrincipalAdministradorLayout);
    ContenedorPrincipalAdministradorLayout.setHorizontalGroup(
        ContenedorPrincipalAdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 922, Short.MAX_VALUE)
    );
    ContenedorPrincipalAdministradorLayout.setVerticalGroup(
        ContenedorPrincipalAdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(OpcionesVistaAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(ContenedorPrincipalAdministrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(OpcionesVistaAdministrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(ContenedorPrincipalAdministrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
}
    
    // ==================== MÓDULO: ADMINISTRAR USUARIOS ====================

/**
 * Muestra la vista de administración de usuarios con tabla y opciones
 */
private void mostrarAdministracionUsuarios() {
    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
    panelPrincipal.setBackground(new Color(102, 102, 102));
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Título
    JLabel labelTitulo = new JLabel("Administración de Usuarios");
    labelTitulo.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 32));
    labelTitulo.setForeground(Color.WHITE);
    labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
    
    // Panel central con tabla
    JPanel panelCentral = crearPanelTablaUsuarios();
    panelPrincipal.add(panelCentral, BorderLayout.CENTER);
    
    // Mostrar en el contenedor principal
    ContenedorPrincipalAdministrador.removeAll();
    ContenedorPrincipalAdministrador.setLayout(new BorderLayout());
    
    JScrollPane scrollPane = new JScrollPane(panelPrincipal);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.setBorder(null);
    
    ContenedorPrincipalAdministrador.add(scrollPane, BorderLayout.CENTER);
    ContenedorPrincipalAdministrador.revalidate();
    ContenedorPrincipalAdministrador.repaint();
}

    /**
     * Crea el panel con la tabla de usuarios y botones de acción
     */
    private JPanel crearPanelTablaUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(102, 102, 102));

        // TODO: BASE DE DATOS - Obtener lista de usuarios
        List<Controlador.UsuarioInfo> usuarios = controlador.obtenerTodosLosUsuarios();

        // Panel superior con botón agregar
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(new Color(102, 102, 102));

        JLabel labelInfo = new JLabel(usuarios.size() + " usuario(s) registrado(s)");
        labelInfo.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 14));
        labelInfo.setForeground(Color.LIGHT_GRAY);
        panelSuperior.add(labelInfo, BorderLayout.WEST);

        JButton botonAgregar = new JButton("➕ Agregar Usuario");
        botonAgregar.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 16));
        botonAgregar.setBackground(new Color(0, 150, 0));
        botonAgregar.setForeground(Color.WHITE);
        botonAgregar.setBorderPainted(false);
        botonAgregar.setPreferredSize(new Dimension(200, 40));
        botonAgregar.addActionListener(e -> mostrarFormularioAgregarUsuario());
        panelSuperior.add(botonAgregar, BorderLayout.EAST);

        panel.add(panelSuperior, BorderLayout.NORTH);

        // Crear tabla
        String[] columnas = {"ID", "Nombre de Usuario", "Rol", "Correo Electrónico", "Teléfono", "Acciones"};
        Object[][] datos = new Object[usuarios.size()][6];

        for (int i = 0; i < usuarios.size(); i++) {
            Controlador.UsuarioInfo usuario = usuarios.get(i);
            datos[i][0] = usuario.idUsuario;
            datos[i][1] = usuario.nombreUsuario;
            datos[i][2] = usuario.rol;
            datos[i][3] = usuario.correo;
            datos[i][4] = usuario.telefono;
            datos[i][5] = "Acciones"; // Placeholder
        }

        JTable tabla = new JTable(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };

        tabla.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 13));
        tabla.setRowHeight(35);
        tabla.getTableHeader().setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(0, 102, 153));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setBackground(new Color(204, 204, 204));

        // Ajustar anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120); // Rol
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200); // Correo
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120); // Teléfono
        tabla.getColumnModel().getColumn(5).setPreferredWidth(150); // Acciones

        JScrollPane scrollTabla = new JScrollPane(tabla);
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Panel inferior con botones de acción
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelAcciones.setBackground(new Color(102, 102, 102));

        JButton botonEditar = new JButton("✏️ Editar");
        botonEditar.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        botonEditar.setBackground(new Color(0, 102, 153));
        botonEditar.setForeground(Color.WHITE);
        botonEditar.setBorderPainted(false);
        botonEditar.setPreferredSize(new Dimension(150, 40));
        botonEditar.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Integer idUsuario = (Integer) tabla.getValueAt(filaSeleccionada, 0);
                mostrarFormularioEditarUsuario(idUsuario);
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Debe seleccionar un usuario de la tabla",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        panelAcciones.add(botonEditar);

        JButton botonEliminar = new JButton("🗑️ Eliminar");
        botonEliminar.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        botonEliminar.setBackground(new Color(200, 0, 0));
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setBorderPainted(false);
        botonEliminar.setPreferredSize(new Dimension(150, 40));
        botonEliminar.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Integer idUsuario = (Integer) tabla.getValueAt(filaSeleccionada, 0);
                String nombreUsuario = (String) tabla.getValueAt(filaSeleccionada, 1);

                int confirmacion = JOptionPane.showConfirmDialog(panel,
                    "¿Está seguro que desea eliminar el usuario?\n\n" +
                    "Usuario: " + nombreUsuario + " (ID: " + idUsuario + ")\n\n" +
                    "Esta acción NO se puede deshacer.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // TODO: BASE DE DATOS - Eliminar usuario
                    boolean exito = controlador.eliminarUsuario(idUsuario);

                    if (exito) {
                        JOptionPane.showMessageDialog(panel,
                            "Usuario eliminado exitosamente\n(Funcionalidad de guardado pendiente de implementación)",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                        // Recargar tabla
                        mostrarAdministracionUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(panel,
                            "Error al eliminar el usuario",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Debe seleccionar un usuario de la tabla",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        panelAcciones.add(botonEliminar);

        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(150, 40));
        botonVolver.addActionListener(e -> {
            ContenedorPrincipalAdministrador.removeAll();
            ContenedorPrincipalAdministrador.revalidate();
            ContenedorPrincipalAdministrador.repaint();
        });
        panelAcciones.add(botonVolver);

        panel.add(panelAcciones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Muestra el formulario para agregar un nuevo usuario
     */
    private void mostrarFormularioAgregarUsuario() {
        mostrarFormularioUsuario(null, "Agregar Nuevo Usuario");
    }

    /**
     * Muestra el formulario para editar un usuario existente
     */
    private void mostrarFormularioEditarUsuario(Integer idUsuario) {
        // TODO: BASE DE DATOS - Obtener datos del usuario
        Controlador.UsuarioDetalle usuario = controlador.obtenerDetalleUsuario(idUsuario);

        if (usuario != null) {
            mostrarFormularioUsuario(usuario, "Editar Usuario");
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo cargar la información del usuario",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el formulario genérico para agregar o editar usuario
     */
    private void mostrarFormularioUsuario(Controlador.UsuarioDetalle usuarioExistente, String titulo) {
        boolean esEdicion = usuarioExistente != null;

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(102, 102, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Nombre de Usuario
        JLabel labelNombre = new JLabel("Nombre de Usuario: *");
        labelNombre.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelNombre, gbc);

        JTextField textFieldNombre = new JTextField(esEdicion ? usuarioExistente.nombreUsuario : "");
        textFieldNombre.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        textFieldNombre.setBackground(new Color(204, 204, 204));
        textFieldNombre.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(textFieldNombre, gbc);

        // Contraseña
        JLabel labelPassword = new JLabel("Contraseña: *");
        labelPassword.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelPassword.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelPassword, gbc);

        JPasswordField textFieldPassword = new JPasswordField(esEdicion ? usuarioExistente.contrasena : "");
        textFieldPassword.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        textFieldPassword.setBackground(new Color(204, 204, 204));
        textFieldPassword.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(textFieldPassword, gbc);

        // Rol (solo en modo agregar)
        JComboBox<String> comboRol = null;
        if (!esEdicion) {
            JLabel labelRol = new JLabel("Rol: *");
            labelRol.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
            labelRol.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = fila;
            gbc.weightx = 0.3;
            panelFormulario.add(labelRol, gbc);

            comboRol = new JComboBox<>(new String[]{"VOLUNTARIO", "FAMILIA", "VETERINARIO", "ADMINISTRADOR"});
            comboRol.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
            comboRol.setBackground(new Color(204, 204, 204));
            comboRol.setPreferredSize(new Dimension(300, 35));
            gbc.gridx = 1;
            gbc.gridy = fila++;
            gbc.weightx = 0.7;
            panelFormulario.add(comboRol, gbc);
        }

        // Correo Electrónico
        JLabel labelCorreo = new JLabel("Correo Electrónico:");
        labelCorreo.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelCorreo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelCorreo, gbc);

        JTextField textFieldCorreo = new JTextField(esEdicion ? usuarioExistente.correo : "");
        textFieldCorreo.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        textFieldCorreo.setBackground(new Color(204, 204, 204));
        textFieldCorreo.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(textFieldCorreo, gbc);

        // Teléfono
        JLabel labelTelefono = new JLabel("Teléfono:");
        labelTelefono.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelTelefono.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelTelefono, gbc);

        JTextField textFieldTelefono = new JTextField(esEdicion ? usuarioExistente.telefono : "");
        textFieldTelefono.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        textFieldTelefono.setBackground(new Color(204, 204, 204));
        textFieldTelefono.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(textFieldTelefono, gbc);

        // Dirección
        JLabel labelDireccion = new JLabel("Dirección:");
        labelDireccion.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        labelDireccion.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panelFormulario.add(labelDireccion, gbc);

        JTextField textFieldDireccion = new JTextField(esEdicion ? usuarioExistente.direccion : "");
        textFieldDireccion.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        textFieldDireccion.setBackground(new Color(204, 204, 204));
        textFieldDireccion.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.weightx = 0.7;
        panelFormulario.add(textFieldDireccion, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(102, 102, 102));

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        botonCancelar.setBackground(new Color(0, 102, 153));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setPreferredSize(new Dimension(150, 45));
        botonCancelar.addActionListener(e -> mostrarAdministracionUsuarios());
        panelBotones.add(botonCancelar);

        JComboBox<String> finalComboRol = comboRol;
        JButton botonGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        botonGuardar.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 16));
        botonGuardar.setBackground(new Color(0, 150, 0));
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setBorderPainted(false);
        botonGuardar.setPreferredSize(new Dimension(150, 45));
        botonGuardar.addActionListener(e -> {
            // Validar campos obligatorios
            String nombre = textFieldNombre.getText().trim();
            String password = new String(textFieldPassword.getPassword()).trim();

            if (nombre.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal,
                    "Los campos Nombre de Usuario y Contraseña son obligatorios",
                    "Campos requeridos",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String correo = textFieldCorreo.getText().trim();
            String telefono = textFieldTelefono.getText().trim();
            String direccion = textFieldDireccion.getText().trim();

            if (esEdicion) {
                // Actualizar usuario
                boolean exito = controlador.actualizarUsuario(
                    usuarioExistente.idUsuario, nombre, password, correo, telefono, direccion
                );

                if (exito) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Usuario actualizado exitosamente\n(Funcionalidad de guardado pendiente de implementación)",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    mostrarAdministracionUsuarios();
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Error al actualizar el usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Registrar nuevo usuario
                String rol = (String) finalComboRol.getSelectedItem();
                Integer idNuevo = controlador.registrarUsuario(nombre, password, rol, correo, telefono, direccion);

                if (idNuevo != null) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Usuario registrado exitosamente\nID: " + idNuevo + "\n(Funcionalidad de guardado pendiente de implementación)",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    mostrarAdministracionUsuarios();
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal,
                        "Error al registrar el usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBotones.add(botonGuardar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar en contenedor
        ContenedorPrincipalAdministrador.removeAll();
        ContenedorPrincipalAdministrador.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalAdministrador.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalAdministrador.revalidate();
        ContenedorPrincipalAdministrador.repaint();
    }
    
        // ==================== MÓDULO: VER VOLUNTARIOS ====================

    /**
     * Muestra la lista simple de voluntarios
     */
    private void mostrarListaVoluntarios() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(102, 102, 102));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Lista de Voluntarios");
        labelTitulo.setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 32));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        // Obtener voluntarios
        List<Controlador.VoluntarioSimple> voluntarios = controlador.obtenerVoluntarios();

        // Panel con la tabla
        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setBackground(new Color(102, 102, 102));

        // Info
        JLabel labelInfo = new JLabel(voluntarios.size() + " voluntario(s) encontrado(s)");
        labelInfo.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 14));
        labelInfo.setForeground(Color.LIGHT_GRAY);
        panelTabla.add(labelInfo, BorderLayout.NORTH);

        // Crear tabla
        String[] columnas = {"ID", "Nombre Usuario", "Correo", "Teléfono", "Dirección", "Reputación", "Detalles"};
        Object[][] datos = new Object[voluntarios.size()][7];

        for (int i = 0; i < voluntarios.size(); i++) {
            Controlador.VoluntarioSimple vol = voluntarios.get(i);
            datos[i][0] = vol.idUsuario;
            datos[i][1] = vol.nombreUsuario;
            datos[i][2] = vol.correoElectronico;
            datos[i][3] = vol.numeroTelefono;
            datos[i][4] = vol.direccion;
            datos[i][5] = vol.reputacion != null ? String.format("%.1f", vol.reputacion) : "Sin evaluar";
            datos[i][6] = vol.detallesReputacion != null ? vol.detallesReputacion : "-";
        }

        JTable tabla = new JTable(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 13));
        tabla.setRowHeight(35);
        tabla.getTableHeader().setFont(new java.awt.Font("Aptos", java.awt.Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(0, 102, 153));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setBackground(new Color(204, 204, 204));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Ajustar anchos
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);  // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);  // Correo
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);  // Teléfono
        tabla.getColumnModel().getColumn(4).setPreferredWidth(250);  // Dirección
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100);  // Reputación
        tabla.getColumnModel().getColumn(6).setPreferredWidth(200);  // Detalles

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        // Botón volver
        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new java.awt.Font("Aptos", java.awt.Font.PLAIN, 16));
        botonVolver.setBackground(new Color(0, 102, 153));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(150, 40));
        botonVolver.addActionListener(e -> {
            ContenedorPrincipalAdministrador.removeAll();
            ContenedorPrincipalAdministrador.revalidate();
            ContenedorPrincipalAdministrador.repaint();
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(102, 102, 102));
        panelBoton.add(botonVolver);

        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Mostrar
        ContenedorPrincipalAdministrador.removeAll();
        ContenedorPrincipalAdministrador.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        ContenedorPrincipalAdministrador.add(scrollPane, BorderLayout.CENTER);
        ContenedorPrincipalAdministrador.revalidate();
        ContenedorPrincipalAdministrador.repaint();
    }

    // ==================== MANEJADORES DE EVENTOS ====================

    private void BotonGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarSelectorReportes();
    }

    private void BotonCerrarSesiónAdministradorActionPerformed(java.awt.event.ActionEvent evt) {
        mainFrame.setInicioSesion();
    }
    
    private void BotonAdministrarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarAdministracionUsuarios();
    }
    
    private void BotonVerVoluntariosActionPerformed(java.awt.event.ActionEvent evt) {
    mostrarListaVoluntarios();
}

    // ==================== VARIABLES DE COMPONENTES ====================
    
    private javax.swing.JButton BotonAdministrarUsuarios;
    private javax.swing.JButton BotonCerrarSesiónAdministrador;
    private javax.swing.JButton BotonGenerarReporte;
    private javax.swing.JButton BotonVerVoluntarios;
    private javax.swing.JPanel ContenedorPrincipalAdministrador;
    private javax.swing.JLabel LabelUsuarioAdministrador;
    private javax.swing.JPanel OpcionesVistaAdministrador;
}
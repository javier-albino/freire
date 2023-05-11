package com.example.demo.controller;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.SolicitudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.dto.ProductosSeleccionadosDTO;
import com.example.demo.dto.SolicitudUsuarioXDTO;
import com.example.demo.dto.SolicitudesDTO;
import com.example.demo.model.OrdenDeCompraSolicitud;

import java.io.IOException;
import java.sql.Timestamp;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/solicitudes")
public class SolicitudResource {
    private final SolicitudService solicitudService;

    public SolicitudResource(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping(value = "/addOrden", consumes = { "multipart/form-data" })
    public ResponseEntity<OrdenDeCompraSolicitud> processOrdenDeCompra(
            @RequestParam(name = "file", required = false) MultipartFile[] file,
            @RequestPart("productosSeleccionados") String productosSeleccionadosJSON) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<ProductosSeleccionadosDTO> productosSeleccionados = mapper.readValue(productosSeleccionadosJSON,
                new TypeReference<List<ProductosSeleccionadosDTO>>() {
                });

        OrdenDeCompraSolicitud savedOrdenDeCompra = solicitudService.processOrdenDeCompra(productosSeleccionados);
        if (file != null) {
            for (MultipartFile f : file) {
                if (f != null && !f.isEmpty()) {
                    solicitudService.uploadFile(f);
                }
            }
        }
        return new ResponseEntity<>(savedOrdenDeCompra, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<SolicitudesDTO>> getSolicitudes() {

        List<Object[]> solicitudes = solicitudService.getAllSolicitudes();

        List<SolicitudesDTO> solicitudesList = new ArrayList<>();
        for (Object[] result : solicitudes) {
            SolicitudesDTO dto = new SolicitudesDTO();
            dto.setId(((Number) result[0]).longValue());
            dto.setFecha_solicitud((Timestamp) result[1]);
            dto.setNombre(((String) result[2]));
            solicitudesList.add(dto);
        }
        return new ResponseEntity<>(solicitudesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SolicitudUsuarioXDTO>> getOrdenDeCompraById(@PathVariable("id") Long id) {
        List<SolicitudUsuarioXDTO> solicitudUsuarioXDTO = solicitudService.findOrdenDeCompraById(id);
        return new ResponseEntity<List<SolicitudUsuarioXDTO>>(solicitudUsuarioXDTO, HttpStatus.OK);
    }
}
package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrdenDeCompra;
import com.example.demo.model.OrdenDeCompraSolicitud;
import com.example.demo.model.SolicitudProducto;

import java.util.*;

@Repository
public interface SolicitudRepo extends JpaRepository<OrdenDeCompra, Long> {
        OrdenDeCompraSolicitud save(OrdenDeCompraSolicitud ordenDeCompra);

        OrdenDeCompra save(OrdenDeCompra ordenDeCompra);

        @Query(nativeQuery = true, value = "SELECT u.nombre, sp.*, p.descripcion, p.precio_unitario " +
                        "FROM datos_usuario.usuario u " +
                        "JOIN orden_de_compras.orden_de_compra o ON u.id = o.usuario_solicitud " +
                        "JOIN orden_de_compras.solicitud_producto sp ON sp.solicitud_id = o.orden_de_compra_producto_solicitud_id "
                        +
                        "JOIN orden_de_compras.producto p ON p.id = sp.producto_id " +
                        "WHERE o.orden_de_compra_producto_solicitud_id = :id")
        List<Object[]> findOrdenDeCompraById(@Param("id") Long id);

        SolicitudProducto save(SolicitudProducto solicitudProducto);

        @Query(value = "SELECT ocps.id, ocps.fecha_solicitud, u.nombre " +
                        "FROM orden_de_compras.orden_de_compra oc " +
                        "JOIN orden_de_compras.orden_de_compra_producto_solicitud ocps " +
                        "   ON oc.orden_de_compra_producto_solicitud_id = ocps.id " +
                        "JOIN datos_usuario.usuario u " +
                        "   ON oc.usuario_solicitud = u.id", nativeQuery = true)
        List<Object[]> getSolicitudes();

}
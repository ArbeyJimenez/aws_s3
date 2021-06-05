package com.awss3.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.awss3.app.models.AdjuntoPoder;

public interface AdjuntoPoderRepository extends JpaRepository<AdjuntoPoder, Integer> {

	@Query(value = "SELECT sap.* FROM sga_adjunto_poder sap WHERE sap.cod_ee_operacion = :cod_asamblea", nativeQuery = true)
	public List<AdjuntoPoder> findByCodAsamblea(Integer cod_asamblea);
}

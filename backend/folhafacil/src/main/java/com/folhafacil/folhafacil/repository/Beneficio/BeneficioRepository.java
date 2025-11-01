package com.folhafacil.folhafacil.repository.Beneficio;

import com.folhafacil.folhafacil.entity.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio,Long> {
}

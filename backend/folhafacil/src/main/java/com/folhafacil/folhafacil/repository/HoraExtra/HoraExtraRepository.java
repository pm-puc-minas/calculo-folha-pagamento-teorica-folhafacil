package com.folhafacil.folhafacil.repository.HoraExtra;

import com.folhafacil.folhafacil.entity.HoraExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoraExtraRepository extends JpaRepository<HoraExtra,Long> {
    @Query("""
    SELECT h FROM HoraExtra h
    WHERE h.idFuncionario.id = :idFuncionario
      AND YEAR(h.dataInicio) = :ano
      AND MONTH(h.dataInicio) = :mes
      AND h.status = 'CONCLUIDA'
    """)
    List<HoraExtra> findByFuncionarioAndMesAno(
            @Param("idFuncionario") String idFuncionario,
            @Param("mes") int mes,
            @Param("ano") int ano
    );
}

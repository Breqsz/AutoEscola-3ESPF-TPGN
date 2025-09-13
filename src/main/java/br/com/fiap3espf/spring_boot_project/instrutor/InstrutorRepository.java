package br.com.fiap3espf.spring_boot_project.instrutor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Page<Instrutor> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
           SELECT i FROM Instrutor i
           WHERE i.ativo = true
             AND (SELECT COUNT(a) FROM Aula a
                  WHERE a.instrutor = i AND a.dataHoraInicio = :inicio) = 0
           """)
    List<Instrutor> findDisponiveis(@Param("inicio") LocalDateTime inicio);
}

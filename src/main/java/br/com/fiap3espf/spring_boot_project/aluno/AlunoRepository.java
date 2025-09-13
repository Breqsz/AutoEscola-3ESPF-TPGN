package br.com.fiap3espf.spring_boot_project.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Page<Aluno> findAllByAtivoTrue(Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}

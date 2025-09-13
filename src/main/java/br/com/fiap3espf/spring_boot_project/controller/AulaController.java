package br.com.fiap3espf.spring_boot_project.controller;

import br.com.fiap3espf.spring_boot_project.aula.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired AulaService service;

    @PostMapping
    public void agendar(@RequestBody @Valid DadosAgendamentoAula dados) {
        service.agendar(dados);
    }

    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable Long id, @RequestBody @Valid DadosCancelamentoAula dados) {
        service.cancelar(id, dados);
    }
}

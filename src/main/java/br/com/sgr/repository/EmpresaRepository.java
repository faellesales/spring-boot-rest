package br.com.sgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sgr.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}

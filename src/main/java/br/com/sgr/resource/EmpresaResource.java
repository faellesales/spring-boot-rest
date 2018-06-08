package br.com.sgr.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgr.model.Empresa;
import br.com.sgr.repository.EmpresaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value="API REST Empresas", description="Dados da empresa")
@RestController
@RequestMapping("/empresa")
public class EmpresaResource {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@ApiResponses(
		value= {
				@ApiResponse(code=200, message="OK"),
				@ApiResponse(code=404, message="Não encontrado")
		}
	)
	
	@ApiOperation(value="Retorna uma lista de Empresas")
	@GetMapping()
	public List<Empresa> listaEmpresas() {
		return  empresaRepository.findAll();
	}
	
	@ApiOperation(value="Retorna uma empresa")
	@GetMapping("/{id}")
	public ResponseEntity<Empresa> buscar(@PathVariable Long id) {
		Empresa empresa = empresaRepository.findById(id).get();
		
		if (empresa == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(empresa);
	}
	
	
	@ApiOperation(value="Salva uma empresa")
	@PostMapping
	public Empresa cadastraEmpresa(@Valid @RequestBody Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	@ApiOperation(value="Atualiza os dados da empresa")
	@PutMapping("/{id}")
	public ResponseEntity<Empresa> atualizaEmpresa(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
		
		Empresa empresaExistente = empresaRepository.findById(id).get();
		
		if(empresaExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(empresa, empresaExistente, "id");
		
		empresaExistente = empresaRepository.save(empresaExistente);
		
		return ResponseEntity.ok(empresaExistente);
	}

	@ApiOperation(value="Deleta uma empresa")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletaEmpresa(@PathVariable Long id) {
		Empresa empresa = empresaRepository.findById(id).get();
		
		if (empresa == null) {
			return ResponseEntity.notFound().build();
		}
		
		empresaRepository.delete(empresa);
		
		return ResponseEntity.noContent().build();
	}
	
}

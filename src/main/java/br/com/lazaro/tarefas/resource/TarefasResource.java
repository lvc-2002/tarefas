package br.com.lazaro.tarefas.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lazaro.tarefas.service.TarefaForm;
import br.com.lazaro.tarefas.service.TarefaService;
import br.com.lazaro.tarefas.service.TarefaView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/tarefas", produces = "application/json")
@AllArgsConstructor
public class TarefasResource {
	
	private final TarefaService tarefaService;
	
	@ApiOperation("Salva uma tarefa")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Criou uma tarefa"),
			@ApiResponse(code = 400, message = "Tarefa inválida"),
			@ApiResponse(code = 500, message = "Ocorreu um erro na api ao criar uma tarefa")
	})
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody @Valid TarefaForm tarefaForm) {
		TarefaView tarefaView = tarefaService.save(tarefaForm);
		URI uri = URI.create("/tarefas/").resolve(tarefaView.getId().toString());
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TarefaForm tarefaForm) {
		tarefaService.update(id, tarefaForm);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public void remove(@PathVariable Long id) {
		tarefaService.remove(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TarefaView> findById(@PathVariable Long id) {
		TarefaView tarefaView = tarefaService.findById(id);
		return ResponseEntity.ok(tarefaView);
	}
	
	@GetMapping
	public ResponseEntity<List<TarefaView>> findByName(@RequestParam(value = "nome", required = false) String nome) {
		List<TarefaView> tarefasView = tarefaService.findByName(nome);
		return ResponseEntity.ok(tarefasView);
	}

}

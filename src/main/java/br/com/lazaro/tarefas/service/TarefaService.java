package br.com.lazaro.tarefas.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.lazaro.tarefas.model.Tarefa;
import br.com.lazaro.tarefas.repository.TarefaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TarefaService {
	
	private final TarefaFormMapper tarefaFormMapper;
	
	private final TarefaViewMapper tarefaViewMapper;
	
	private final TarefaRepository tarefaRepository;
	
	public TarefaView save(TarefaForm tarefaForm) {
		Tarefa tarefa = tarefaFormMapper.map(tarefaForm);
		tarefa.setId(null);
		tarefa.setAtivo(true);
		tarefa.setDataCriacao(Calendar.getInstance());
		tarefa = tarefaRepository.save(tarefa);
		return new TarefaView(tarefa);
	}
	
	public TarefaView findById(Long id) {
		Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
		if (tarefaOptional.isPresent()) {
			return tarefaViewMapper.map(tarefaOptional.get());
		}
		return null;
	}

}
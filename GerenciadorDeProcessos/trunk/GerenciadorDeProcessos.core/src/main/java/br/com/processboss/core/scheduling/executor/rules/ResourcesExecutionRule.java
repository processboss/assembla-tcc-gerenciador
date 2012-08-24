package br.com.processboss.core.scheduling.executor.rules;

import br.com.processboss.core.bean.ValidationRulesVO;

/**
 * Classe com regra que verifica os recursos de hardware
 */
public class ResourcesExecutionRule extends ExecutionRule {

	public ResourcesExecutionRule(ExecutionRule next) {
		super(next);
	}

	@Override
	public boolean validate(ValidationRulesVO validationRulesVO) {
		if(!validationRulesVO.getServerStateService().canExecute(validationRulesVO.getProcessInTask())){
			return false;
		}
		
		return true;
	}

}

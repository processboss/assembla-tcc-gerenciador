package br.com.processboss;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.service.IServerStateService;



@ContextConfiguration(value="classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ServerStateReaderTests {

	@Autowired
	private IServerStateService serverStateService;

	@Test
	public void testRead() throws Exception {
		
		while(true){
			ServerState serverState = serverStateService.read();
			assertNotNull(serverState);
			System.out.println("### " + serverState.getMemory().getUsed() 
					+ "/" + serverState.getMemory().getTotal()
					+ " (" + serverState.getMemory().getUsedPercent() + "%)");

			Thread.sleep(1000);
		}
	}


}

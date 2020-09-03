package camix.service;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
;

@RunWith(EasyMockRunner.class)
public class CanalChatTestEx1EasyMock {
	@Mock
	private ClientChat clientchatmock ;
	
	@Before
	public void setUp() throws Exception 
	{
		
		//this.clientchatmock = EasyMock.createMock(CanalChat.class);
	}

	@After
	public void tearDown() throws Exception 
	{
	}
	
	@Test
	public void ajouteClientTest_v1() {
		final CanalChat canal = new CanalChat ("Canal : ");
		
		final String id = "111111"; 
		
		EasyMock.expect(
				this.clientchatmock.donneId()
			).andReturn(
				id
			).times(3);
			EasyMock.replay(this.clientchatmock);
			
			canal.ajouteClient (this.clientchatmock);
			
			Assert.assertEquals("nombre clients " , 1 , (int) canal.donneNombreClients());
			Assert.assertTrue("client présent", canal.estPresent(clientchatmock));
			
			EasyMock.verify(this.clientchatmock);
	}
	@Test
	public void ajouteClientTest_v2() {
		final CanalChat canal = new CanalChat ("Canal : ");
		
		final String id = "111111"; 
		
		EasyMock.expect(
				this.clientchatmock.donneId()
			).andReturn(
				id
			).times(3);
			EasyMock.replay(this.clientchatmock);
			
			canal.ajouteClient (this.clientchatmock);
			
			
			Assert.assertEquals("nombre clients " , 1 , (int) canal.donneNombreClients());
			Assert.assertTrue("client présent", canal.estPresent(clientchatmock));
			
			EasyMock.verify(this.clientchatmock);
	}
}

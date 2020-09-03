package camix.service;


import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import camix.communication.ProtocoleChat;


@RunWith(EasyMockRunner.class)
public class ServiceChatTestEx2EasyMock  {
	
	private ServiceChat ServiceChatMock ;
	
	@Mock
	private ClientChat clientchat ;
	
	@Test (timeout = 2000)
	public void informeDepartClientTest() throws IOException {
		final ServiceChat serviceChat = new ServiceChat ("");
		
		final String surnom = "surnom du client"; 
		
		EasyMock.expect(
			this.clientchat.donneSurnom()
		).andReturn(
			surnom
		);
		
		this.clientchat.envoieContacts(
				String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, surnom)
		);
		
		EasyMock.replay(this.clientchat);
		
		serviceChat.informeDepartClient(this.clientchat);
		
		EasyMock.verify(this.clientchat);
	
	}
}

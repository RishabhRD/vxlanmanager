package net.floodlightcontroller.vxlanmanager.messages;
public abstract class AgentDataPacket implements IAgentDataPacket{

	IAgentDataPacket payload;

	@Override
	public IAgentDataPacket getPayload() {
		return payload;
	}

	@Override
	public void setPayload(IAgentDataPacket packet) {
		payload = packet;
	}

}

package net.floodlightcontroller.vxlanmanager.messages;

public interface IAgentDataPacket extends IAgentPacket{
	byte[] serialize();
	void deserialize(byte[] array);
	IAgentDataPacket getPayload();
	void setPayload(IAgentDataPacket packet);
}

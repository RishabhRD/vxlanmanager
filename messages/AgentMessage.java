package net.floodlightcontroller.vxlanmanager.messages;

import java.nio.ByteBuffer;

public class AgentMessage{
	private static final char maxSeqNumber = 20;
	private char seqNumber;
	private MessageType type;
	private IAgentPacket encapsulatedPacket;

	AgentMessage(MessageType type, IAgentPacket encapsulatedPacket){
		this.type = type;
		this.encapsulatedPacket = encapsulatedPacket;
	}

	AgentMessage(MessageType type, IAgentPacket encapsulatedPacket,char seqNumber){
		this.type = type;
		this.encapsulatedPacket = encapsulatedPacket;
		this.seqNumber = seqNumber;
	}

	public short getSize() {
		return encapsulatedPacket.getSize();
	}

	public MessageType getType(){
		return type;
	}

	public void incrementSequenceNumber() throws IndexOutOfBoundsException{
		if(seqNumber == maxSeqNumber)
			throw new IndexOutOfBoundsException("Bad function call");
		seqNumber++;
	}

	public void resetSequenceNumber(){
		seqNumber = 0;
	}

	public IAgentPacket getPacket(){
		return encapsulatedPacket;
	}

	public byte[] serialize(){
		byte array[] = new byte[4];
		ByteBuffer buffer = ByteBuffer.wrap(array);
		buffer.putChar(seqNumber);
		buffer.putChar(type.asChar());
		buffer.putShort(getSize());
		return array;
	}
}

package net.floodlightcontroller.vxlanmanager.messages;

import java.nio.ByteBuffer;

public class VxlanDel extends AgentDataPacket {

	private VxlanId id;
	private char req;
	private char succeded;
	private static final short headerSize = 8;

	public VxlanDel() {
		id = new VxlanId();
		req = (char)0;
		succeded = (char)0;
	}

	public VxlanId getVxlanId() {
		return id;
	}

	public void setVxlanId(VxlanId id) {
		this.id = id;
	}

	public boolean isRequest() {
		return req == (char)1 ? true : false;
	}

	public void setRequest(boolean req) {
		this.req = req ? (char)1 : (char)0;
	}

	public char isSucceeded() {
		return succeded;
	}

	public void setSucceeded(char succeded) {
		this.succeded = succeded;
	}

	@Override
	public byte[] serialize() {
		byte[] array = new byte[headerSize];
		ByteBuffer buffer = ByteBuffer.wrap(array);
		buffer.put(id.getBytes());
		buffer.putChar(req);
		buffer.putChar(succeded);
		return array;
	}

	@Override
	public void deserialize(byte[] array) {
		ByteBuffer buffer = ByteBuffer.wrap(array);
		byte[] vxlanArray = new byte[3];
		buffer.get(vxlanArray);
		id.setBytes(vxlanArray);
		req = buffer.getChar();
		succeded = buffer.getChar();
	}

	@Override
	public short getSize() {
		return 0;
	}
}

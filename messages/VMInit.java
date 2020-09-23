package net.floodlightcontroller.vxlanmanager.messages;

import java.nio.ByteBuffer;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.MacAddress;

public class VMInit extends AgentDataPacket {
	private VxlanId id;
	private char req;
	private char succeeded;
	private IPv4Address ip;
	private MacAddress mac;
	private static final short headerSize = 16;

	public VMInit() {
		id = new VxlanId();
		req = (char) 0;
		succeeded = (char) 0;
		ip = IPv4Address.NONE;
		mac = MacAddress.NONE;
	}

	public VxlanId getVxlanId() {
		return id;
	}

	public void setVxlanId(VxlanId id) {
		this.id = id;
	}

	public boolean isRequest() {
		return req == (char) 0 ? false : true;
	}

	public void setRequest(boolean request) {
		this.req = request ? (char) 1 : (char) 0;
	}

	public boolean isSucceeded() {
		return succeeded == (char) 0 ? false : true;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded ? (char) 1 : (char) 0;
	}

	public IPv4Address getIP() {
		return ip;
	}

	public void setIP(IPv4Address ip) {
		this.ip = ip;
	}

	public MacAddress getMac() {
		return mac;
	}

	public void setMac(MacAddress mac) {
		this.mac = mac;
	}

	@Override
	public byte[] serialize() {
		byte[] array = new byte[headerSize];
		ByteBuffer buffer = ByteBuffer.wrap(array);
		buffer.put(id.getBytes());
		buffer.putChar(req);
		buffer.put(ip.getBytes());
		buffer.put(mac.getBytes());
		buffer.putChar(succeeded);
		return array;
	}

	@Override
	public void deserialize(byte[] array) {
		ByteBuffer buffer = ByteBuffer.wrap(array);
		byte[] ipArray = new byte[4];
		byte[] macArray = new byte[6];
		byte[] vxlanArray = new byte[3];
		buffer.get(vxlanArray);
		id.setBytes(vxlanArray);
		req = buffer.getChar();
		buffer.get(ipArray);
		ip = IPv4Address.of(ipArray);
		buffer.get(macArray);
		mac = MacAddress.of(macArray);
		succeeded = buffer.getChar();
	}

	@Override
	public short getSize() {
		if (payload == null)
			return headerSize;
		return (short) (headerSize + payload.getSize());
	}
}

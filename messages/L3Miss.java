package net.floodlightcontroller.vxlanmanager.messages;

import java.nio.ByteBuffer;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.MacAddress;

public class L3Miss extends AgentDataPacket {

	private IPv4Address src_ip;
	private VxlanId src_vm_vxlan;
	private MacAddress src_vm_mac;
	private IPv4Address dest_ip;
	private boolean request;
	private MacAddress replyMac;
	private static final short headerSize = 24;

	L3Miss() {
		src_ip = IPv4Address.NONE;
		src_vm_vxlan = new VxlanId();
		src_vm_mac = MacAddress.of(0);
		dest_ip = IPv4Address.of(0);
		request = false;
		replyMac = MacAddress.of(0);
	}

	void setSourceVMMac(MacAddress mac) {
		this.src_vm_mac = mac;
	}

	MacAddress getSourceVMMac() {
		return src_vm_mac;
	}

	void setSourceVMVxlanId(VxlanId id) {
		this.src_vm_vxlan = id;
	}

	VxlanId getSourceVMVxlanId() {
		return src_vm_vxlan;
	}

	void setDestinationIP(IPv4Address ip) {
		this.dest_ip = ip;
	}

	IPv4Address getDestinationIP() {
		return dest_ip;
	}

	boolean isRequest() {
		return request;
	}

	void setRequest(boolean req) {
		this.request = req;
	}

	void setReplyMac(MacAddress mac) {
		this.replyMac = mac;
	}

	MacAddress getReplyMac() {
		return replyMac;
	}

	void setSourceIP(IPv4Address ip) {
		this.src_ip = ip;
	}

	IPv4Address getSourceIP() {
		return src_ip;
	}

	@Override
	public byte[] serialize() {
		byte[] array = new byte[headerSize];
		ByteBuffer buffer = ByteBuffer.wrap(array);
		buffer.put(src_ip.getBytes());
		buffer.put(dest_ip.getBytes());
		buffer.put(src_vm_vxlan.getBytes());
		buffer.putChar(request ? (char) 1 : (char) 0);
		buffer.put(src_vm_mac.getBytes());
		if (!request) {
			buffer.put(replyMac.getBytes());
		}
		return array;
	}

	@Override
	public void deserialize(byte[] array) {
		byte[] ipArray = new byte[4];
		byte[] macArray = new byte[6];
		byte[] vxlanArray = new byte[3];
		ByteBuffer buffer = ByteBuffer.wrap(array);
		buffer.get(ipArray);
		src_ip = IPv4Address.of(ipArray);
		buffer.get(ipArray);
		dest_ip = IPv4Address.of(ipArray);
		buffer.get(vxlanArray);
		src_vm_vxlan.setBytes(vxlanArray);
		char req = buffer.getChar();
		if (req == 0) {
			request = false;
		} else {
			request = true;
		}
		buffer.get(macArray);
		src_vm_mac = MacAddress.of(macArray);
		buffer.get(macArray);
		replyMac = MacAddress.of(macArray);
	}

	@Override
	public short getSize() {
		if (payload == null)
			return headerSize;
		return (short) (headerSize + payload.getSize());
	}
}

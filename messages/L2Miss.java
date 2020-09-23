package net.floodlightcontroller.vxlanmanager.messages;

import java.nio.ByteBuffer;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.MacAddress;

public class L2Miss extends AgentDataPacket{
	private IPv4Address src_ip;
	private VxlanId src_vm_vxlan;
	private MacAddress src_vm_mac;
	private MacAddress dest_mac;
	private boolean request;
	private IPv4Address reply_ip;
	private static final short headerSize = 24;

	public L2Miss(){
		src_ip = IPv4Address.NONE;
		src_vm_vxlan = new VxlanId();
		src_vm_mac = MacAddress.of(0);
		dest_mac = MacAddress.of(0);
		request = false;
		reply_ip = IPv4Address.of(0);
	}

	public void setSourceVMMac(MacAddress mac){
		this.src_vm_mac = mac;
	}

	public MacAddress getSourceVMMac(){
		return src_vm_mac;
	}

	public void setSourceVMVxlanId(VxlanId id){
		this.src_vm_vxlan = id;
	}

	public VxlanId getSourceVMVxlanId(){
		return src_vm_vxlan;
	}

	public void setDestinationMac(MacAddress mac){
		this.dest_mac = mac;
	}

	public MacAddress getDestinationMac(){
		return dest_mac;
	}

	public boolean isRequest(){
		return request;
	}

	public void setRequest(boolean req){
		this.request = req;
	}

	public void setReplyIP(IPv4Address ip){
		this.reply_ip = ip;
	}

	public IPv4Address getReplyIP(){
		return reply_ip;
	}

	public void setSourceIP(IPv4Address ip){
		this.src_ip = ip;
	}

	public IPv4Address getSourceIP(){
		return src_ip;
	}
	@Override
	public byte[] serialize() {
		byte[] ret = new byte[headerSize];
		ByteBuffer buffer = ByteBuffer.wrap(ret);
		buffer.put(src_ip.getBytes());
		buffer.put(src_vm_vxlan.getBytes());
		buffer.putChar(request ? (char)1 : (char)0);
		buffer.put(src_vm_mac.getBytes());
		buffer.put(dest_mac.getBytes());
		if(!request){
			buffer.put(reply_ip.getBytes());
		}
		return ret;
	}

	@Override
	public void deserialize(byte[] array) {
		ByteBuffer buffer = ByteBuffer.wrap(array);
		byte[] ipArray = new byte[4];
		buffer.get(ipArray);
		src_ip = IPv4Address.of(ipArray);
		byte[] vxlanArray = new byte[3];
		buffer.get(vxlanArray);
		src_vm_vxlan.setBytes(vxlanArray);
		char req;
		req = buffer.getChar();
		if(req == 0){
			request = false;
		}else{
			request = true;
		}
		byte[] macArray = new byte[6];
		buffer.get(macArray);
		src_vm_mac = MacAddress.of(macArray);
		buffer.get(ipArray);
		reply_ip = IPv4Address.of(ipArray);
	}

	@Override
	public short getSize() {
		if(payload == null) return headerSize;
		return (short)(headerSize + payload.getSize());
	}
}

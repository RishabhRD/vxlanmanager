package net.floodlightcontroller.vxlanmanager.messages;

public class VxlanId{
	private static char numBytes = (char)3;
	private byte[] arr;

	public VxlanId(){
		arr = new byte[numBytes];
	}

	public byte[] getBytes(){
		return arr;
	}

	public void setBytes(byte[] arr){
		for(int i = 0; i < arr.length; i++){
			this.arr[i] = arr[i];
		}
	}
}

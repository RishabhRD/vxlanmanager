package net.floodlightcontroller.vxlanmanager.messages;
public enum MessageType{
	DATA((char)1),
	CONTROL((char)2),
	EROOR((char)3);

	private final char asChar;

	MessageType(char asChar){
		this.asChar = asChar;
	}

	public char asChar(){
		return asChar;
	}
}

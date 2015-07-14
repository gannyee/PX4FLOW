package com.mavlink;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class MavlinkParser {
	private static final int STATE_DFLT = 0;
	private static final int STATE_STX = 1; 
	private static final int STATE_LEN = 2; 
	private static final int STATE_SEQ = 3; 
	private static final int STATE_SYS = 4; 
	private static final int STATE_COMP = 5; 
	private static final int STATE_MSG = 6; 
	private static final int STATE_CKA = 7; 
	private static final int STATE_CKB = 8; 
	private static final byte STX_BYTE =  (byte)0XFE;
	private static final int TARGET_ID = 100;
	private int state;
	private int massagerLength;
	private int massagerID;
	private String massager;
	private String targetMassager;
	private int targetID;
	public MavlinkParser(){
		this.state = STATE_DFLT;
		this.massagerLength = 0;
		this.massagerID = 0;
		this.massager = "";
		this.targetMassager="";
		this.targetID = TARGET_ID;
	}
	
	public void process(byte[] buffer){
		for(int i = 0;i < buffer.length;i ++ ){
			if(buffer[i] == STX_BYTE){
				this.state = STATE_STX;
			}
			else if(this.state == STATE_STX){
				this.massagerLength = buffer[i];
				this.state = STATE_LEN;
			}else if(this.state == STATE_LEN){
				
				this.state = STATE_SEQ;
			}else if(this.state == STATE_SEQ){
				
				this.state = STATE_SYS;
			}else if(this.state == STATE_SYS){
				this.state = STATE_COMP;
			}else if(this.state == STATE_COMP){
				this.massagerID = buffer[i];
				this.massager = "";
				this.state = STATE_MSG;
			}else if(this.state == STATE_MSG){
				this.massager = this.massager + buffer[i];
				if( massager.length() == this.massagerLength ){
					this.state = STATE_CKA;
				}
			}else if(this.state == STATE_CKA){
				this.state = STATE_CKB;
			}else if(this.state == STATE_CKB){
				if(this.massagerID == this.targetID){
					//System.out.println("flag3: " + massager);
					this.targetMassager = this.massager;
					//System.out.println("flag5: " + targetMassager);
					this.massager = "";
					this.state = STATE_DFLT;
				}
			}
		}
	}
	public String getTargetMassager() {
		return targetMassager;
	}
	
	
}

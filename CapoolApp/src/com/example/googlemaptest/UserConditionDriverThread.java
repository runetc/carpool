package com.example.googlemaptest;

import java.io.IOException;

import android.util.Log;

public class UserConditionDriverThread extends ServerConnector {
	protected boolean checkJoin = false;
	protected String[] msg;
	protected String[] inputMsg = new String[500];
	protected int arraySize;
	
	protected void job() {
		try {
			output.writeUTF(Snumber);
			output.writeInt(processNum);
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		/*
		 * try { inputMsg[0] = input.readUTF(); inputMsg[1] = input.readUTF();
		 * inputMsg[2] = input.readUTF(); inputMsg[3] = input.readUTF();
		 * inputMsg[4] = input.readUTF(); inputMsg[5] = input.readUTF();
		 * inputMsg[6] = input.readUTF(); inputMsg[7] = input.readUTF(); } catch
		 * (IOException e1) {
		 * 
		 * e1.printStackTrace(); }
		 */
		String iodskfaopd = "";
		try {
			iodskfaopd = input.readUTF();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(iodskfaopd.equals("NEXT")){
			try {
				arraySize = input.readInt();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			arraySize = (arraySize * 2) + 2;
			for (int i = 0; i < arraySize; i++) {
				try {
					inputMsg[i] = input.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else
		{
			inputMsg[0] = "EOT";
		}
		
		try {
			checkJoin = input.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void inputText(String[] s) {
		msg = s;
	}

	public boolean getCheckJoinComplete() {
		return checkJoin;
	}

	public String[] getInputMsg() {
		return inputMsg;
	}
}

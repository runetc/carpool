package com.example.googlemaptest;

import java.io.IOException;

public class Status_MenuThread extends ServerConnector {
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
		

		
		/*try {
			inputMsg[0] = input.readUTF();
			inputMsg[1] = input.readUTF();
			inputMsg[2] = input.readUTF();
			inputMsg[3] = input.readUTF();
			inputMsg[4] = input.readUTF();
			inputMsg[5] = input.readUTF();
			inputMsg[6] = input.readUTF();
			inputMsg[7] = input.readUTF();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		*/
		
		try {
			inputMsg[0] = input.readUTF();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int i = 0;
		while(!inputMsg[i].equals("EOT")) {
			try {
				i++;
				inputMsg[i] = input.readUTF();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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
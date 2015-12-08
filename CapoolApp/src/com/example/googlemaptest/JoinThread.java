package com.example.googlemaptest;

import java.io.IOException;

public class JoinThread extends ServerConnector {
	private boolean checkJoin = false;
	private String[] msg;

	protected void job() {
		try {
			output.writeUTF(Snumber);
			output.writeInt(processNum);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < msg.length; i++) {
			try {
				output.writeUTF(msg[i]);
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
}

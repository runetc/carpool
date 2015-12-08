package com.example.googlemaptest;

import java.io.IOException;

import android.util.Log;

public class UserConditionMenuBoarderThread extends ServerConnector {
	protected boolean checkJoin = false;
	protected String[] inputMsg = new String[500];
	protected int arraySize;
	protected boolean accept = false;
	protected String tempString = "";

	protected void job() {

		try {
			output.writeUTF(Snumber);
			output.writeInt(processNum);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			tempString = input.readUTF();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (tempString.equals("OK")) {
			Log.w("dd", tempString);
			try {
				accept = input.readBoolean();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < 9; i++) {
				try {
					inputMsg[i] = input.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				arraySize = input.readInt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			arraySize = (arraySize * 2) + 1;

			for (int i = 9; i < arraySize + 9; i++) {
				try {
					inputMsg[i] = input.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			inputMsg[0] = "EOT";
		}

		try {
			checkJoin = input.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean getCheckJoinComplete() {
		return checkJoin;
	}

	public boolean getAccept() {
		return accept;
	}

	public String[] getInputMsg() {
		return inputMsg;
	}
}
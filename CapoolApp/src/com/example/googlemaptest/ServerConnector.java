package com.example.googlemaptest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class ServerConnector {
	protected Socket socket;
	protected DataInputStream input;
	protected DataOutputStream output;
	protected boolean workComplete = false;
	protected String ip = "192.168.0.33"; // IP
	protected int port = 6666; // PORT¹øÈ£
	protected int processNum = 0;
	protected String Snumber;

	protected Thread t = new Thread() {
		public void run() {
			try {
				setSocket();

				job();

				workComplete = true;
				output.writeUTF("EOT");

				output.close();
				input.close();
				socket.close();

				processNum = 0;
			} catch (Exception e) {
			}
		}
	};

	public void start() {
		workComplete = false;

		t.start();
	}

	public boolean getWorkComplete() {
		return workComplete;
	};

	public String getSnumber() {
		return Snumber;
	};

	public void setSnumber(String s) {
		Snumber = s;
	};

	public void inputProcessNum(int i) {
		processNum = i;
	}

	protected abstract void job();

	public void setSocket() throws IOException {
		try {
			socket = new Socket(ip, port);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}

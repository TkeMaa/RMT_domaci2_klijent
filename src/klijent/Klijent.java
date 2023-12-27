package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Klijent implements Runnable {
	
	static Socket soketZaKomunikaciju = null;
	
	static BufferedReader serverInput = null;
	static PrintStream serverOutput = null;
		
	@Override
	public void run() {
				
		try (BufferedReader inputTastatura = new BufferedReader(new InputStreamReader(System.in))) {
			
			String input;
			
			while (true) {
				
				input = inputTastatura.readLine();
				serverOutput.println(input);
				
				if (input.equals("6")) {
					return;
				}
					
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		
		try {
			
			soketZaKomunikaciju = new Socket("localhost", 5000);
			
			serverInput = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			serverOutput = new PrintStream(soketZaKomunikaciju.getOutputStream());
			
			new Thread(new Klijent()).start();
			
			String input = null;
			
			while (true) {
				
				input = serverInput.readLine();
				
				if (input.equals("***izlaz***")) {					
					soketZaKomunikaciju.close();
					return;
				}
				
				System.out.println(input);
				
			}
			
		} catch (SocketException e) {
			System.out.println("Error: server is down!");
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}	
}

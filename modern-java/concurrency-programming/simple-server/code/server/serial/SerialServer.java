///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package server.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import command.CommandExecutor;
import command.CommandFactory;
import command.CommandContext;
import server.Server;

/**
 * SerialServer
 */
public class SerialServer implements Server {
	private final ServerSocket serverSocket;
	private final AtomicBoolean running;
	private final CommandExecutor commandExecutor;

	public SerialServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.running = new AtomicBoolean(true);
		this.commandExecutor = new CommandExecutor(new CommandFactory());
	}

	@Override
	public void run() {
		System.out.println("Serial Server 启动成功，端口: %d".formatted(serverSocket.getLocalPort()));
		while (running.get()) {
			try {
				Socket clientSocket = serverSocket.accept();
				System.out.println("新连接: " + clientSocket);
				handleClient(clientSocket);
			} catch (IOException e) {
				if (running.get()) {
					System.out.println("接受连接错误" + e.getMessage());
				}
			}

		}
	}

	private void handleClient(Socket socket) {
		try (
				socket;
				var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				var out = new PrintWriter(socket.getOutputStream(), true);) {
			String cmdStr = in.readLine();
			String[] cmdData = cmdStr.split(";");

			CommandContext context = new CommandContext(out, cmdData);
			commandExecutor.execute(context);

		} catch (IOException e) {
			System.err.println("Error handling client: " + e.getMessage());
		}
	}

	@Override
	public void stop() {
		try {
			if (!this.serverSocket.isClosed()) {
				this.serverSocket.close();
				running.compareAndSet(true, false);
				System.out.println("关闭服务器Success");
			}
		} catch (IOException e) {
			System.err.println("关闭服务器出现错误: " + e.getMessage());
		}

	}
}
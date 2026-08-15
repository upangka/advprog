///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package server.parallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import command.CommandExecutor;
import command.CommandFactory;
import command.CommandContext;
import server.Server;

/**
 * SerialServer
 */
public class ConcurrencyServer implements Server {
	private final ServerSocket serverSocket;
	private final AtomicBoolean running;
	private final CommandExecutor commandExecutor;
	private final ThreadPoolExecutor executor;

	/**
	 * 内部类
	 */

	private class RequestTask implements Runnable{
		private final Socket socket;

		private RequestTask(Socket socket){
			this.socket = socket;
		}


		@Override
		public void run() {
			System.out.println("【%s】新连接: %s".formatted(Thread.currentThread().getName(),socket));
			ConcurrencyServer.this.handleClient(socket);
		}
	}


	public ConcurrencyServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.running = new AtomicBoolean(true);
		this.commandExecutor = new CommandExecutor(new CommandFactory());
		int coreThreads = Runtime.getRuntime().availableProcessors();
		this.executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(coreThreads);
	}

	@Override
	public void run() {
		System.out.println("Concurrency Server 启动成功，端口: %d".formatted(serverSocket.getLocalPort()));
		while (running.get()) {
			try {
				Socket clientSocket = serverSocket.accept();
				this.executor.submit(new RequestTask(clientSocket));
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

	/**
	 * serverSocket.close() 只关闭监听端口，不再接受新连接
	 * 已经 accept() 返回的 Socket 对象完全不受影响
	 * 已建立的连接可以继续通信
	 * 所以下面需要先关闭ServerSocket，再关闭线程池，不能搞反
	 */
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

		 // 关闭线程池（拒绝新任务，等待已有任务完成）
		 this.executor.shutdown();
		 try {
			if(this.executor.awaitTermination(30, TimeUnit.SECONDS)){
				this.executor.shutdownNow();
			}
			System.out.println("关闭线程池Success");
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			this.executor.shutdownNow();
		}

	}
}
package org.com.yilian.oMClient.instructions;

import java.io.IOException;
import java.net.Socket;

public interface Instruction {
    /**
     * 执行指令
     * @param socket
     * @return 执行结果 0失败，1成功
     */
    public int executeInstruction(Socket socket);
}

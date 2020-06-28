package org.com.yilian.oMClient.instructions.impl;

import org.com.yilian.oMClient.instructions.Instruction;

import java.net.Socket;

public class ShutDownAgentInstructionImpl implements Instruction {
    public int executeInstruction(Socket socket) {
         return 0;
    }
}

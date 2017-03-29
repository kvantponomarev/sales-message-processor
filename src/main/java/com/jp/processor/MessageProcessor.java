package com.jp.processor;

import java.io.IOException;

/**
 * Process all incoming sales notifications
 */
public interface MessageProcessor {
    /**
     * Starts accepting sales notifications
     * @throws IOException
     */
    void run() throws IOException;
}

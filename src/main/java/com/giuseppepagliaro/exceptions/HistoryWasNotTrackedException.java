package com.giuseppepagliaro.exceptions;

/**
 * thrown when attempting to get history details from a solver initialized with 
 * the saveHistory option to false
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class HistoryWasNotTrackedException extends RuntimeException {
    public HistoryWasNotTrackedException() {
        super("Attempting to get history details from a solver initialized with the saveHistory option to false.");
    }
}

package com.example.newtoolsmon.textscanner;

public interface ScannerListener {
    void onDetected(String detections);
    void onStateChanged(String state, int i);
}

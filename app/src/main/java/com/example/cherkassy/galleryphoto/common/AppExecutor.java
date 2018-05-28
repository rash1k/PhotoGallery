package com.example.cherkassy.galleryphoto.common;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutor INSTANCE;
    private final Executor mDiskIO;
    private final Executor mNetworkIO;

    private AppExecutor(Executor diskIO, Executor networkIO) {
        mDiskIO = diskIO;
        mNetworkIO = networkIO;
    }

    public static AppExecutor getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new AppExecutor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3));
            }
        }
        return INSTANCE;
    }

public Executor getDiskIO() {
        return mDiskIO;
        }

public Executor getNetworkIO() {
        return mNetworkIO;
        }
        }

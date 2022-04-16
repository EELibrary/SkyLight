package org.novau2333.ssapi;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private static ConcurrentHashMap<String, ThreadPoolExecutor> executors = new ConcurrentHashMap<>();

    public static Map<String, ThreadPoolExecutor> getExecutors() {
        return executors;
    }

    public static void init() {
        executors.put("entities", World.regularExecutor);
        executors.put("tileBlocks", World.tileExecutor1);
        executors.put("weatherEffects", World.weather_effectsExecutor);
    }

    public static void executeOnMainThread(Runnable task) {
        MinecraftServer.executeFailedQueue.add(() -> {
            try {
                task.run();
            } catch (Exception e) {
                MinecraftServer.LOGGER.error("Error in executeOnMainThread", e);
                e.printStackTrace();
            }
        });
    }

    public static void stopExecutors() {
        MinecraftServer.LOGGER.warn("Method stopExecutors() called!Server's entities,tileblocks,functions will stop updating!");
        MinecraftServer.LOGGER.warn("Stopping all async executors and clearing the queue!");
        World.regularExecutor.shutdown();
        World.tileExecutor1.shutdown();
        World.weather_effectsExecutor.shutdown();
        MinecraftServer.executeFailedQueue.clear();
        MinecraftServer.LOGGER.warn("All async executors and queue cleared!");
    }

    public static String getStatus() {
        return "\n#########Server Status:#########\n" +
                "Entities: " + World.regularExecutor.getTaskCount() + "/" + World.regularExecutor.getPoolSize() + "\n" +
                "TileBlocks: " + World.tileExecutor1.getTaskCount() + "/" + World.tileExecutor1.getPoolSize() + "\n" +
                "WeatherEffects: " + World.weather_effectsExecutor.getTaskCount() + "/" + World.weather_effectsExecutor.getPoolSize() + "\n" +
                "Server TPS cycle:" + MinecraftServer.getServerInst().tps1.getAverage() + "\n" +
                "Server Concurrent tick:" + MinecraftServer.currentTick + "\n" +
                "Server MSPT full cycle:" + MinecraftServer.MSPT + "\n" +
                "Server MSPT in ticking:" + MinecraftServer.MSPT + "\n" +
                "ThreadPoolExecutor Status:\n" +
                "   Entities: " + World.regularExecutor.toString() + "\n" +
                "   TileBlocks: " + World.tileExecutor1.toString() + "\n" +
                "   WeatherEffects: " + World.weather_effectsExecutor.toString() + "\n" +
                "###############################";
    }
}

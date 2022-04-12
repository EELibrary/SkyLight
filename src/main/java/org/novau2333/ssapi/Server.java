package org.novau2333.ssapi;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    public static void init() {
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

    public static void setEntitiesPerThread(int i) {
        if (i > 0) {
            World.entitiesPerThread.set(i);
        }
    }

    public static String getStatus() {
        return "\n#########Server Status:#########\n" +
                "TileBlocks: " + World.tileExecutor1.getTaskCount() + "/" + World.tileExecutor1.getPoolSize() + "\n" +
                "WeatherEffects: " + World.weather_effectsExecutor.getTaskCount() + "/" + World.weather_effectsExecutor.getPoolSize() + "\n" +
                "Server TPS cycle:" + MinecraftServer.getServerInst().tps1.getAverage() + "\n" +
                "Server Concurrent tick:" + MinecraftServer.currentTick + "\n" +
                "Server MSPT full cycle:" + MinecraftServer.MSPT + "\n" +
                "Server MSPT in ticking:" + MinecraftServer.MSPT + "\n" +
                "ThreadPoolExecutor Status:\n" +
                "   TileBlocks: " + World.tileExecutor1.toString() + "\n" +
                "   WeatherEffects: " + World.weather_effectsExecutor.toString() + "\n" +
                "###############################";
    }
}

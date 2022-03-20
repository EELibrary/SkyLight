package jlnpeapi.api;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;

public class API {
    private static MinecraftServer server = MinecraftServer.getServerInst();
    public static void executeOnMainThread(Runnable target){
        server.LOGGER.debug("Executed on main thread");
        server.executeFailedQueue.add(target);
    }
    public static PlayerList getPlayerList(){
        return server.getPlayerList();
    }
}

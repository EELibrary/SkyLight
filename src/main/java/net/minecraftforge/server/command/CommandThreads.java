package net.minecraftforge.server.command;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandException;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
public class CommandThreads extends CommandBase{
    @Override
    public String getName()
    {
        return "threads";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        ThreadGroup currentGroup =
                Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        sender.sendMessage(TextComponentHelper.createComponentTranslation(sender,"commands.forge.threads.summary.list",String.valueOf(lstThreads.length)));
        for (int i = 0; i < noThreads; i++)
            sender.sendMessage(TextComponentHelper.createComponentTranslation(sender,"commands.forge.threads.summary.threads", lstThreads[i].getName()));

    }

}

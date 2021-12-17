package xyz.raitaki.vantachatgames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

public final class main extends JavaPlugin {


    private static main instance;
    public static GameManager gameManager;
    public static main getInstance() {return instance;}

    private void registerCommands(String[] cmds, CommandExecutor cmdExecutor)
    {
        for (String cmd : cmds)
        {
            getCommand(cmd).setExecutor(cmdExecutor);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().info(ChatColor.AQUA + "VantaChatGames plugini açıldı.");
        try {
            saveconfigs();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        registerCommands(new String[] { "cgbildirim", "cgkelimeekle", "cgreload"}, new commands() );
        gameManager = new GameManager();
        gameManager.setTimer(10);
        Bukkit.getPluginManager().registerEvents(gameManager, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    private void saveconfigs() throws URISyntaxException {
        File f = new File(getDataFolder() + "/");
        if(!f.exists())
            f.mkdir();
        FileResourcesUtil app = new FileResourcesUtil();
        String ignoreds = "ignoredplayers.json";
        String words = "words.json";

        File folder = this.getDataFolder().getAbsoluteFile();
        if(!new File(folder+"/words.json").exists()){
            InputStream is = app.getFileFromResourceAsStream(words);
            app.saveFile(words,is);
        }
        if(!new File(folder+"/ignoredplayers.json").exists()){
            InputStream is = app.getFileFromResourceAsStream(ignoreds);
            app.saveFile(ignoreds,is);
        }
    }
}

package xyz.raitaki.vantachatgames;

import de.leonhard.storage.Json;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameManager implements Listener {

    private Json words           = new Json("words", main.getInstance().getDataFolder().getAbsolutePath());
    private Json ignoredplayers  = new Json("ignoredplayers", main.getInstance().getDataFolder().getAbsolutePath());
    private boolean game         = true;
    private boolean found        = false;
    private int timer            = 0;
    private static String text   = "";
    private String truetext      = "";
    private String color1        = "#39FF0D";
    private String color_borders = "#839192";
    private String color_winner  = "#0DDEFF";
    private String border_text   = "§m--------------------------------";

    public void setTimer(Integer time){
        this.timer = time;
        start();
    }

    private void start(){
        new BukkitRunnable(){
            public void run() {
                text = getRandomizedWord();
                game = true;
                checkfind();
                sendStartMessage();
            }
        }.runTaskTimer(main.getInstance(), 20L *timer, 20L *timer);
    }

    @EventHandler
    public void chatevent(AsyncPlayerChatEvent event){
        if(game && (!this.found) && event.getMessage().equalsIgnoreCase(truetext)){
            sendWinMessage(event.getPlayer());
            found = true;
            event.setCancelled(true);
        }
    }

    private String getRandomizedWord(){
        String randomizedword = "";
        List<String> wordslist = this.words.getStringList("words");
        String sellectedword = wordslist.get(new Random().nextInt(wordslist.size()));
        truetext = sellectedword;
        randomizedword = methods.shuffleletters(new Random(), sellectedword);
        return randomizedword;
    }

    public void addWord(String word){
        List<String> list = words.getStringList("words");
        list.add(word.toLowerCase());
        words.set("words", list);
        updateConfigs();
    }

    public void removeWord(String word){
        List<String> list = words.getStringList("words");
        list.remove(word.toLowerCase());
        words.set("words", list);
        updateConfigs();
    }

    public boolean isWordinList(String word){
        return words.getStringList("words").contains(word.toLowerCase());
    }

    public void addPlayer(Player p){
        List<String> list = ignoredplayers.getStringList("ignoredplayers");
        list.add(p.getName());
        ignoredplayers.set("ignoredplayers", list);
        updateConfigs();
    }

    public void removePlayer(Player p){
        List<String> list = ignoredplayers.getStringList("ignoredplayers");
        list.remove(p.getName());
        ignoredplayers.set("ignoredplayers", list);
        updateConfigs();
    }

    public boolean isPlayeronList(Player p){
        return ignoredplayers.getStringList("ignoredplayers").contains(p.getName());
    }

    private void checkfind(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(game && !found){
                    sendLoseMessage();
                }
                text = "";
                game  = false;
                found = false;
                truetext = "";
            }
        }.runTaskLater(main.getInstance(), 20L *(timer/2));
    }

    private void sendStartMessage(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!isPlayeronList(p)) {
                p.sendMessage(ChatColor.of(color_borders) + border_text);
                p.sendMessage(ChatColor.of(color1)        + "Kelime oyunu başladı!");
                p.sendMessage(ChatColor.of(color_winner)  + text);
                p.sendMessage(ChatColor.of(color1)        + "Kelimesini bil ve ödülü kap!");
                p.sendMessage(ChatColor.of(color_borders) + border_text);
            }
        }
        Bukkit.getLogger().info(ChatColor.of(color_borders) + border_text);
        Bukkit.getLogger().info(ChatColor.of(color1)        +"Kelime oyunu başladı!");
        Bukkit.getLogger().info(ChatColor.of(color_winner)  + text);
        Bukkit.getLogger().info(ChatColor.of(color1)        +"Kelimesini bil ve ödülü kap!");
        Bukkit.getLogger().info(ChatColor.of(color_borders) + border_text);
    }

    private void sendWinMessage(Player winner){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!isPlayeronList(p)) {
                p.sendMessage(ChatColor.of(color_borders) + border_text);
                p.sendMessage(ChatColor.of(color1)        + "Kelime oyunu bitti!");
                p.sendMessage(ChatColor.of(color_winner)  + winner.getName());
                p.sendMessage(ChatColor.of(color1)        + "Kelime oyununu kazandı!");
                p.sendMessage(ChatColor.of(color_borders) + border_text);
            }
        }
        Bukkit.getLogger().info(ChatColor.of(color_borders) + border_text);
        Bukkit.getLogger().info(ChatColor.of(color1)        + "Kelime oyunu bitti!");
        Bukkit.getLogger().info(ChatColor.of(color_winner)  + winner.getName());
        Bukkit.getLogger().info(ChatColor.of(color1)        + "Kelime oyununu kazandı!");
        Bukkit.getLogger().info(ChatColor.of(color_borders) + border_text);
    }

    private void sendLoseMessage(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!isPlayeronList(p)) {
                p.sendMessage(ChatColor.of(color_borders) + border_text);
                p.sendMessage(ChatColor.of(color1)        + "Kelime oyunu bitti!");
                p.sendMessage(ChatColor.of(color_winner)  + truetext);
                p.sendMessage(ChatColor.of(color1)        + "Kelimesini kimse bilemedi!");
                p.sendMessage(ChatColor.of(color_borders) + border_text);
            }
        }
        Bukkit.getLogger().info(ChatColor.of(color_borders) + border_text);
        Bukkit.getLogger().info(ChatColor.of(color1)        + "Kelime oyunu bitti!");
        Bukkit.getLogger().info(ChatColor.of(color_winner)  + truetext);
        Bukkit.getLogger().info(ChatColor.of(color1)        + "Kelimesini kimse bilemedi!");
        Bukkit.getLogger().info(ChatColor.of(color_borders) + border_text);
    }

    public void updateConfigs(){
        words          = new Json("words", main.getInstance().getDataFolder().getAbsolutePath());
        ignoredplayers = new Json("ignoredplayers", main.getInstance().getDataFolder().getAbsolutePath());
    }

    public Json getIgnoredPlayers(){
        return ignoredplayers;
    }
}

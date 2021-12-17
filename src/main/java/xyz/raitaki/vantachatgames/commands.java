package xyz.raitaki.vantachatgames;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("cgbildirim")) {
                GameManager gameManager = main.gameManager;
                if (!gameManager.isPlayeronList(p)) {
                    gameManager.addPlayer(p);
                    p.sendMessage(ChatColor.of("#839192") + "Artık chat oyunundan bildirim almayacaksın.");
                } else {
                    gameManager.removePlayer(p);
                    p.sendMessage(ChatColor.of("#839192") + "Artık chat oyunundan bildirim alacaksın.");
                }
                gameManager.updateConfigs();
            }
            if (label.equalsIgnoreCase("cgreload")) {
                if(p.hasPermission("chatgame.reload")){
                    GameManager gameManager = main.gameManager;
                    gameManager.updateConfigs();
                }
            }
            if(label.equalsIgnoreCase("cgkelimeekle") && p.hasPermission("chatgame.addword")){
                if(args.length == 1){
                    GameManager gameManager = main.gameManager;
                    if(gameManager.isWordinList(args[0])){
                        gameManager.removeWord(args[0]);
                        p.sendMessage(ChatColor.of("#0DDEFF") + args[0] + ChatColor.of("#839192") + " kelimesini listeden kaldırdın.");
                    }else {
                        gameManager.addWord(args[0]);
                        p.sendMessage(ChatColor.of("#0DDEFF") + args[0] + ChatColor.of("#839192") + " kelimesini listeye ekledin.");
                    }
                }else{
                    p.sendMessage(ChatColor.of("#0DDEFF") + "Doğru kullanım: /cgkelimeekle <kelime>");
                }
            }
        }
        return false;
    }
}

package me.mattyhd0.chatcolor.placeholderapi;

import me.mattyhd0.chatcolor.CPlayer;
import me.mattyhd0.chatcolor.pattern.api.BasePattern;
import org.bukkit.entity.Player;
import me.mattyhd0.chatcolor.ChatColorPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.plugin.Plugin;

public class ChatColorPlaceholders extends PlaceholderExpansion
{
    private Plugin plugin;
    
    public ChatColorPlaceholders() {
        this.plugin = ChatColorPlugin.getInstance();
    }
    
    public boolean canRegister() {
        return true;
    }
    
    public String getAuthor() {
        return "MattyHD0";
    }
    
    public String getIdentifier() {
        return "chatcolor";
    }
    
    public String getRequiredPlugin() {
        return "ChatColor";
    }
    
    public String getVersion() {
        return "1.0";
    }
    
    public String onPlaceholderRequest(Player player, String identifier) {

        CPlayer cPlayer = ChatColorPlugin.getInstance().getDataMap().get(player.getUniqueId());
        
        switch (identifier){
            case "last_message": {
                return cPlayer == null ? "" : cPlayer.getLastMessages();
            }
            case "pattern_name": {
                if(cPlayer == null) {
                    return "";
                }else{
                    return cPlayer.getPattern() == null ? "" : cPlayer.getPattern().getName(false);
                }
            }
            case "pattern_name_formatted": {
                if(cPlayer == null) {
                    return "";
                }else{
                    return cPlayer.getPattern() == null ? "" : cPlayer.getPattern().getName(true);
                }
            }
            case "color": {
                // Retorna o primeiro código de cor HEX do padrão
                if(cPlayer == null || cPlayer.getPattern() == null) {
                    return "";
                }
                BasePattern pattern = cPlayer.getPattern();
                if(pattern.getColors() == null || pattern.getColors().isEmpty()) {
                    return "";
                }
                return pattern.getColors().get(0).getName(); // Retorna código HEX como #e74c3c
            }
            case "colors": {
                // Retorna todos os códigos de cor separados por vírgula
                if(cPlayer == null || cPlayer.getPattern() == null) {
                    return "";
                }
                BasePattern pattern = cPlayer.getPattern();
                if(pattern.getColors() == null || pattern.getColors().isEmpty()) {
                    return "";
                }
                StringBuilder colors = new StringBuilder();
                for(int i = 0; i < pattern.getColors().size(); i++) {
                    if(i > 0) colors.append(",");
                    colors.append(pattern.getColors().get(i).getName());
                }
                return colors.toString();
            }
            default: {
                // Suporta %chatcolor_color_0%, %chatcolor_color_1%, etc para cores específicas
                if(identifier.startsWith("color_")) {
                    if(cPlayer == null || cPlayer.getPattern() == null) {
                        return "";
                    }
                    try {
                        int index = Integer.parseInt(identifier.substring(6));
                        BasePattern pattern = cPlayer.getPattern();
                        if(pattern.getColors() == null || index >= pattern.getColors().size()) {
                            return "";
                        }
                        return pattern.getColors().get(index).getName();
                    } catch (NumberFormatException e) {
                        return "";
                    }
                }
                return "";
            }
        }

    }

}

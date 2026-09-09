package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(value = "chatclear", alias = "clearchat")
public class ClearChatCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.chatclear")
    public void onChatClear(CommandSender sender) {
        Component clearMessage = Component.text("\n".repeat(100));
        Bukkit.getServer().filterAudience(audience -> {
            if (audience instanceof Player player) {
                return !player.hasPermission("allaychat.bypass.clearchat");
            }

            return false;
        }).sendMessage(clearMessage);

        ChatUtils.sendMessage(sender, ChatUtils.format(
                plugin.getMessagesConfig().getString("messages.chat-cleared",
                        "Could not find messages.chat-cleared in your messages config."),
                Placeholder.unparsed("player", sender.getName())
        ));

        ChatUtils.sendMessage(plugin.getServer(), ChatUtils.format(
                plugin.getMessagesConfig().getString("messages.chat-cleared-broadcast",
                        "Could not find messages.chat-cleared-broadcast in your messages config."),
                Placeholder.unparsed("player", sender.getName())
        ));
    }

}

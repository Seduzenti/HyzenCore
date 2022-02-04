package redehyzen.escolhendo.utils.cmd.utils;

import com.google.common.collect.ImmutableList;
import java.util.List;

import escolhendo.apexstore.services.player.Profile;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import redehyzen.escolhendo.utils.cmd.Commands;

public class TeleportCommand extends Commands {

    public TeleportCommand() {
        super("tp", "teleportar", "teleport");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("hyzenutils.cmd.teleportar")) {
            player.sendMessage("§cVocê não possui permissão para executar este comando.");
            return;
        }
        Profile profile = Profile.getProfile(player.getName());
    }
        public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        } else if (args.length >= 1 && args.length <= 4) {
            Player player;
            if (args.length != 1 && args.length != 3) {
                player = Bukkit.getPlayerExact(args[0]);
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cDigite o nome de um jogador!");
                    return true;
                }

                player = (Player)sender;
            }

            if (player == null) {
                sender.sendMessage("§cJogador não encontrado: " + args[0]);
                return true;
            } else {
                if (args.length < 3) {
                    Player target = Bukkit.getPlayerExact(args[args.length - 1]);
                    if (target == null) {
                        sender.sendMessage("Não foi possível localizar o jogador " + args[args.length - 1] + ". Portanto, sem teleporte.");
                        return true;
                    }

                    player.teleport(target, TeleportCause.COMMAND);
                    Command.broadcastCommandMessage(sender, "§eFoi teleportado " + player.getDisplayName() + " §epara " + target.getDisplayName());
                } else if (player.getWorld() != null) {
                    Location playerLocation = player.getLocation();
                    double x = this.getCoordinate(sender, playerLocation.getX(), args[args.length - 3]);
                    double y = this.getCoordinate(sender, playerLocation.getY(), args[args.length - 2], 0, 0);
                    double z = this.getCoordinate(sender, playerLocation.getZ(), args[args.length - 1]);
                    if (x == -3.0000001E7D || y == -3.0000001E7D || z == -3.0000001E7D) {
                        sender.sendMessage("Por favor, digite as coordenadas corretas!");
                        return true;
                    }

                    playerLocation.setX(x);
                    playerLocation.setY(y);
                    playerLocation.setZ(z);
                    player.teleport(playerLocation, TeleportCause.COMMAND);
                    Command.broadcastCommandMessage(sender, String.format("§eTeleportado %s para %.2f, %.2f, %.2f", player.getDisplayName(), x, y, z));
                }

                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Utilize: " + this.usageMessage);
            return false;
        }
    }

    private double getCoordinate(CommandSender sender, double current, String input) {
        return this.getCoordinate(sender, current, input, -30000000, 30000000);
    }

    private double getCoordinate(CommandSender sender, double current, String input, int min, int max) {
        boolean relative = input.startsWith("~");
        double result = relative ? current : 0.0D;
        if (!relative || input.length() > 1) {
            boolean exact = input.contains(".");
            if (relative) {
                input = input.substring(1);
            }

            double testResult = getDouble(sender, input);
            if (testResult == -3.0000001E7D) {
                return -3.0000001E7D;
            }

            result += testResult;
            if (!exact && !relative) {
                result += 0.5D;
            }
        }

        if (min != 0 || max != 0) {
            if (result < (double)min) {
                result = -3.0000001E7D;
            }

            if (result > (double)max) {
                result = -3.0000001E7D;
            }
        }

        return result;
    }

    private double getDouble(CommandSender sender, String input) {
        return 0;
    }

    public List tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List)(args.length != 1 && args.length != 2 ? ImmutableList.of() : super.tabComplete(sender, alias, args));
    }
}
